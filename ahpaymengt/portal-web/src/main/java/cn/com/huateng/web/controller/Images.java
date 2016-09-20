/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.controller;

import com.aixforce.common.model.Paging;
import com.aixforce.common.utils.JsonMapper;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.site.model.UserImage;
import com.aixforce.site.service.ImageService;
import com.aixforce.user.base.BaseUser;
import com.aixforce.user.base.UserUtil;
import com.aixforce.web.misc.MessageSources;
import com.fasterxml.jackson.databind.JavaType;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
public class Images {
    private final static Logger log = LoggerFactory.getLogger(Images.class);
    private final static Set<String> allowed_types = ImmutableSet.of("jpeg", "jpg", "png", "gif");

    private final static JsonMapper jsonMapper = JsonMapper.JSON_NON_DEFAULT_MAPPER;

    private final static JavaType mapType = jsonMapper.createCollectionType(HashMap.class,String.class,String.class);

    //private final HashFunction hf = Hashing.md5();

    private final String imageUploadUrl;

    private final String baseImageUrl;

    @Autowired
    private MessageSources messageSources;

    @Autowired
    private ImageService imageService;

    @Autowired
    public Images(@Value("#{app.imageServer}")String imageServer,@Value("#{app.imageBaseUrl}")String baseImageUrl) {
        //this.imageDir = imageDir;
        this.imageUploadUrl = imageServer+"/submit";
        this.baseImageUrl = baseImageUrl;
    }

    @RequestMapping(value = "/images/{imageId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void deleteImage(@PathVariable("imageId") Long imageId) {
        UserImage userImage = imageService.findUserImageById(imageId);
        if (userImage == null) return;
        if (!userImage.getUserId().equals(UserUtil.getCurrentUser().getId())) {
            throw new JsonResponseException(401, messageSources.get("image.delete.noauth"));
        }
        imageService.deleteUserImage(userImage);
        try {
            //FileUtils.deleteQuietly(new File(imageDir+userImage.getFileName()));
            HttpRequest request = HttpRequest.delete(imageUploadUrl+"/"+userImage.getFileName());
            if(request.ok()&&log.isDebugEnabled()){
                log.debug("succeed to delete image {}",userImage.getFileName());
            }else{
                log.error("failed to delete image {}",userImage.getFileName());
            }
        } catch (Exception e) {
            log.warn("error happened when deleteFile {} on share disk, error:{}", userImage, e);
        }
    }

    @RequestMapping(value = "/images/upload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String processUpload(@RequestParam("file") MultipartFile file) {
        String ext = Files.getFileExtension(file.getOriginalFilename()).toLowerCase();

        if (allowed_types.contains(ext)) {
            try {
                BaseUser user = UserUtil.getCurrentUser();
                checkNotNull(user, "user not login");
                Long userId = user.getId();

                //upload to image server
                HttpRequest request = HttpRequest.post(imageUploadUrl);
                InputStream inputStream = file.getInputStream();
                try {
                    request.part("file", inputStream);
                    if(request.ok()){
                        String response = request.body(Charsets.UTF_8.name());
                        Map<String,String> json = jsonMapper.fromJson(response,mapType );
                        String fileId = json.get("fid").replace(',','/');
                        UserImage userImage = new UserImage();
                        userImage.setUserId(userId);
                        userImage.setFileName(baseImageUrl+"/"+fileId+"."+ext);
                        userImage.setFileSize((int)file.getSize());
                        imageService.addUserImage(userImage);
                    }
                } finally {
                    if(inputStream!=null){
                        inputStream.close();
                    }
                }

                return "ok";

            } catch (Exception e) {
                Throwables.propagateIfInstanceOf(e,JsonResponseException.class);
                log.error("failed to process upload image {},cause:{}", file.getOriginalFilename(), e);
                throw new JsonResponseException(500, messageSources.get("image.upload.fail"));
            }
        }

        throw new JsonResponseException(400, messageSources.get("image.illegal.ext"));
    }

    @RequestMapping(value = "/images/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Paging<UploadedFile> imagesOf(@RequestParam(value = "p", defaultValue = "1") Integer pageNo,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {

        try {
            BaseUser user = UserUtil.getCurrentUser();
            checkNotNull(user, "user not login");

            Integer from = (pageNo - 1) * size;
            Paging<UserImage> result = imageService.findUserImagesByUserId(user.getId(), from, size);
            return new Paging<UploadedFile>(result.getTotal(),Lists.transform(result.getData(), new Function<UserImage, UploadedFile>() {
                @Override
                public UploadedFile apply(UserImage input) {
                    return new UploadedFile(input.getId(), null, input.getFileSize(), input.getFileName());
                }
            }));
        } catch (Exception e) {
            log.error("failed to find user images for pageNo={} and size={},cause:{}",
                    pageNo, size, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500, messageSources.get("image.query.fail"));
        }
    }

}
