package cn.com.huateng.web.controller.api;


import cn.com.huateng.CommonUser;
import cn.com.huateng.account.service.RealNameService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.util.Base64DealImageUtil;
import cn.com.huateng.web.util.HttpUtil;
import cn.com.huateng.web.util.ImageUtil;
import com.aixforce.common.utils.JsonMapper;
import com.aixforce.user.base.UserUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;
import com.huateng.p3.account.persistence.models.TRealnameApply;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Author: lzw
 * Date: 14-11-26
 * Time: 下午2:58
 */
@Controller
@RequestMapping("/api/security")
public class RealName {
    public final static Logger log = LoggerFactory.getLogger(RealName.class);

    private final static Set<String> allowed_types = ImmutableSet.of("jpeg", "jpg");

    @Autowired
    private RealNameService realNameService;

    private String imageUploadUrl;

    private String baseImageUrl;

    private int imgSizeMax;

    private final static JsonMapper jsonMapper = JsonMapper.JSON_NON_DEFAULT_MAPPER;

    private final static JavaType mapType = jsonMapper.createCollectionType(HashMap.class, String.class, String.class);

    @Autowired
    public RealName(@Value("#{app.imageServer}") String imageServer, @Value("#{app.realNameImageBaseUrl}") String baseImageUrl,
                    @Value("#{app.imgSizeMax}") int imgSizeMax) {
        this.imageUploadUrl = imageServer + "/submit";
        this.baseImageUrl = baseImageUrl;
        this.imgSizeMax = imgSizeMax;
    }

    /**
     * 安全中心-实名认证
     *
     * @param realName   真实姓名
     * @param nation     国籍
     * @param profession 职业
     * @param idType     证件类型
     * @param valid      证件有效期
     * @param address    联系地址
     * @param idNo       证件号码
     * @param imgPath    图片路径
     */
    @RequestMapping(value = "/realName", method = RequestMethod.POST)
    public String realName(@RequestParam("realName") String realName,
                           @RequestParam("nation") String nation,
                           @RequestParam("profession") String profession,
                           @RequestParam("idType") String idType,
                           @RequestParam("valid") String valid,
                           @RequestParam("address") String address,
                           @RequestParam("idNo") String idNo,
                           @RequestParam("imgPath") String imgPath,
                           HttpServletRequest request,
                           Map<String, Object> context) {
        CommonUser user = UserUtil.getCurrentUser();

        //请求实名认证前再次调用查询接口，再判断用户是否能实名认证，防止用户直接通过页面地址申请实名认证
        Response<List<TRealnameApply>> queryresult = realNameService.queryRealnameAuthStatus(user);
        if(!queryresult.isSuccess()){
            if ("700201".equals(queryresult.getErrorCode())) {
                //您申请失败次数大于2次，不能再申请
                context.put("errorMsg", "您申请实名认证的失败次数大于2次，不能再申请。");
                return "/layout/sutongpay/safeCenter/realName/fail";
            } else if ("700202".equals(queryresult.getErrorCode())) {
                //您的申请已经提交，轻耐心等待
                context.put("errorMsg", "您的申请已经提交，请耐心等待。");
                return "/layout/sutongpay/safeCenter/realName/fail";
            } else if ("700203".equals(queryresult.getErrorCode())) {
                //已经是高级实名认证
                context.put("errorMsg", "您已经是高级实名认证用户。");
                return "/layout/sutongpay/safeCenter/realName/fail";
            } else if ("800001".equals(queryresult.getErrorCode())) {
                return "layout/sutongpaypay/login";
            } else {
                log.error("queryRealnameAuthStatus fail productNo {},cause:{}",user.getUnifyId(), BussErrorCode.explain(queryresult.getErrorCode()));
                context.put("errorMsg",BussErrorCode.explain(queryresult.getErrorCode()));
                return "/layout/sutongpay/safeCenter/realName/fail";
            }
        }
        imgPath = imgPath == null ? "" : imgPath;
        if ("".equals(imgPath)) {
            context.put("errorMsg", "身份证图片获得失败。");
            return "/layout/sutongpay/safeCenter/realName/fail";
        }
        String fileName = imgPath.substring((imgPath.lastIndexOf('/') + 1));
        String photo = "";
        try{
            URL url;
            InputStream inputStream=null;
            try {
                url = new URL(imgPath);
                inputStream =url.openConnection().getInputStream();
                photo = Base64DealImageUtil.readImage(inputStream);
            } finally {
                if(inputStream!=null){
                    inputStream.close();
                }
            }
        }catch (Exception e){
            log.error("get image fail ,productNo:{},imagepath:{},cause:{}", user.getUnifyId(),imgPath, e);
            context.put("errorMsg", "身份证图片获得失败。");
            return "/layout/sutongpay/safeCenter/realName/fail";
        }
        String loginIp = HttpUtil.getIpAddr(request);
        Response<String> result;
        try {
            result = realNameService.identifyRealnameApply(user, realName, idType, idNo, photo, fileName, loginIp, nation, profession, valid, address);
        } catch (Exception e) {
            log.error("appaly realname fail productNo:{},cause:{}", user.getUnifyId(), e.getMessage());
            context.put("errorMsg", "服务器连接异常,请稍后再试。");
            return "/layout/sutongpay/safeCenter/realName/fail";
        }
        if (result.isSuccess()) {
            return "/layout/sutongpay/safeCenter/realName/ok";
        }
        context.put("errorMsg", BussErrorCode.explain(result.getErrorCode()));
        return "/layout/sutongpay/safeCenter/realName/fail";
    }
    /**
     * 实名认证上传图片
     *
     */
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadImage(@RequestParam("img") MultipartFile img) {
        String ext = Files.getFileExtension(img.getOriginalFilename()).toLowerCase();
        CommonUser user = UserUtil.getCurrentUser();
        if (user == null){
            return "error|logout|";
        }
        if (allowed_types.contains(ext)) {
            if (img.getSize() > imgSizeMax) {
                return "error|outsize|";
            }
            try {
                InputStream inputStream = img.getInputStream();
                //上传后图片时会自动压缩，现把压缩方法注释若后有需要再打开
                //图片大于200KB（200*1024=204800），需要压缩
                if (img.getSize()>204800){
                    ImageUtil imageUtil = new ImageUtil();
                    //imageUtil.compressPic(imageDir, imageDir, fileName, fileName, 600, 400, true);
                    inputStream=imageUtil.compressPic1(img,400,300);
                }
                //添加水印
                inputStream = ImageUtil.pressText1(inputStream, "速通专用", "宋体", Font.PLAIN, 30, Color.red, 0.5f, -45);
                //upload to image server
                HttpRequest request = HttpRequest.post(imageUploadUrl);
                try {
                    request.part("file", inputStream);
                    if (request.ok()) {
                        String response = request.body(Charsets.UTF_8.name());
                        Map<String, String> json = jsonMapper.fromJson(response, mapType);
                        String fileId = json.get("fid").replace(',', '/');
                        log.info("用户："+user.getUnifyId()+"_返回图片路径："+baseImageUrl+"/"+fileId+"."+ext);
                        return "success|"+baseImageUrl + "/" + fileId + "." + ext+"|";
                    }
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                return "error|uploaderror|";
            } catch (Exception e) {
                log.error("failed to process upload image {}", img.getOriginalFilename());
                log.error("cause: {}", e);
                return  "error|uploaderror";
            }
        }
        return "error|errorimg|";
    }

    /**
     * 查询实名认证结果,判断该用户是否可以实名认证
     */
    @RequestMapping(value = "/result")
    public String checkRealName(Map<String, Object> context) {
        CommonUser user = UserUtil.getCurrentUser();
        Response<List<TRealnameApply>> result = realNameService.queryRealnameAuthStatus(user);
        String isNext = "";
        if (!result.isSuccess()) {
            if ("700201".equals(result.getErrorCode())) {
                //您申请失败次数大于2次，不能再申请
                isNext = "A";
            } else if ("700202".equals(result.getErrorCode())) {
                //您的申请已经提交，轻耐心等待
                isNext = "B";
            } else if ("700203".equals(result.getErrorCode())) {
                //已经是高级实名认证
                isNext = "C";
            } else if ("800001".equals(result.getErrorCode())) {
                return "layout/sutongpay/login";
            } else {
                log.error("queryRealnameAuthStatus fail productNo {},cause:{}",user.getUnifyId(), BussErrorCode.explain(result.getErrorCode()));
                context.put("errorMsg",BussErrorCode.explain(result.getErrorCode()));
                return "/layout/sutongpay/safeCenter/realName/result/fail";
            }
        } else {
            //第一次申请实名认证
            if (result.getResult().size() == 0) {
                return "/layout/sutongpay/safeCenter/realName/upload";
            } else if (result.getResult().size() == 1) {
                isNext = "Y";
            }
        }
        List<TRealnameApply> tRealnameApplyList = result.getResult();
        for (TRealnameApply tRealnameApply : tRealnameApplyList) {
            String idNo = tRealnameApply.getIdNo();
            tRealnameApply.setIdNo(idNo);
        }
        context.put("isNext", isNext);
        context.put("realNameApplyList", tRealnameApplyList);
        return "/layout/sutongpay/safeCenter/realName/result";
    }
}
