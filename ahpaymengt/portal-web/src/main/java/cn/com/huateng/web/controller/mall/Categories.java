package cn.com.huateng.web.controller.mall;

import com.aixforce.category.dto.RichCategory;
import com.aixforce.category.service.CategoryService;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.item.model.Spu;
import com.aixforce.item.service.SpuService;
import com.aixforce.web.misc.MessageSources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-21
 */
@Controller
@RequestMapping("/api/mall/categories")
public class Categories {
    private final static Logger log = LoggerFactory.getLogger(Categories.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SpuService spuService;
    @Autowired
    private MessageSources messageSources;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<RichCategory> list() {
        try {
            return categoryService.childrenOf(0L);
        } catch (Exception e) {
            log.error("failed to load root categories,cause:{}",e);
            throw new JsonResponseException(500, messageSources.get("category.fail.load"));
        }
    }

    @RequestMapping(value = "/{id}/children", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<RichCategory> childrenOf(@PathVariable("id") Long categoryId) {
        try {
            return categoryService.childrenOf(categoryId);
        } catch (Exception e) {
            log.error("failed to load sub categories of {},cause:{}",categoryId,e);
            throw new JsonResponseException(500, messageSources.get("category.query.fail"));
        }
    }


    @RequestMapping(value="/{categoryId}/spus",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Spu> findByCategoryId(@PathVariable("categoryId")Long categoryId){
        try {
            return spuService.findByCategoryId(categoryId);
        } catch (Exception e) {
            log.error("failed to find Spus for categoryId {},cause:{}",categoryId,e);
            throw new JsonResponseException(500,messageSources.get("spu.query.fail"));
        }
    }
}
