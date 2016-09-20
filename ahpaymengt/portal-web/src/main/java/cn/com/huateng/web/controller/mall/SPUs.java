package cn.com.huateng.web.controller.mall;

import com.aixforce.exception.JsonResponseException;
import com.aixforce.item.dto.RichAttribute;
import com.aixforce.item.model.AttributeKey;
import com.aixforce.item.model.AttributeValue;
import com.aixforce.item.model.Spu;
import com.aixforce.item.service.AttributeService;
import com.aixforce.item.service.SpuService;
import com.aixforce.web.misc.MessageSources;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
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
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-21
 */
@Controller
@RequestMapping("/api/mall")
public class SPUs {
    private final static Logger log = LoggerFactory.getLogger(SPUs.class);

    private final AttributeService attributeService;

    private final SpuService spuService;

    private final MessageSources messageSources;

    @Autowired
    public SPUs(AttributeService attributeService, SpuService spuService, MessageSources messageSources) {
        this.attributeService = attributeService;
        this.spuService = spuService;
        this.messageSources = messageSources;
    }

    @RequestMapping(value = "/spus/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> find(@PathVariable("id")Long spuId){
        Spu spu = spuService.findById(spuId);
        List<RichAttribute> spuAttributes = attributeService.findSpuAttributesBy(spuId);
        Map<AttributeKey,List<AttributeValue>> skuAttributeMap = attributeService.findSkuAttributesBy(spuId);
        List<AttributeKeyValues> skuKeyValues = from(skuAttributeMap);
        return ImmutableMap.of("spu", spu, "spuAttributes", spuAttributes, "skuAttributes", skuKeyValues);
    }



    @RequestMapping(value = "/category/{categoryId}/spus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Spu> listOf(@PathVariable("categoryId") Long categoryId) {
        try {
            return spuService.findByCategoryId(categoryId);
        } catch (Exception e) {
            log.error("failed to find spus under categoryId:{},cause:{}", categoryId, e);
            throw new JsonResponseException(500, messageSources.get("spu.query.fail"));
        }
    }

    public static class AttributeKeyValues {

        private AttributeKey attributeKey;


        private List<AttributeValue> attributeValues;

        public AttributeKey getAttributeKey() {
            return attributeKey;
        }

        public void setAttributeKey(AttributeKey attributeKey) {
            this.attributeKey = attributeKey;
        }

        public List<AttributeValue> getAttributeValues() {
            return attributeValues;
        }

        public void setAttributeValues(List<AttributeValue> attributeValues) {
            this.attributeValues = attributeValues;
        }
    }

    private List<AttributeKeyValues> from(Map<AttributeKey, List<AttributeValue>> skuAttributeMap) {
        List<AttributeKeyValues> result = Lists.newArrayListWithCapacity(skuAttributeMap.keySet().size());
        for (AttributeKey attributeKey : skuAttributeMap.keySet()) {
            AttributeKeyValues akv = new AttributeKeyValues();
            akv.setAttributeKey(attributeKey);
            akv.setAttributeValues(skuAttributeMap.get(attributeKey));
            result.add(akv);
        }
        return result;
    }

}
