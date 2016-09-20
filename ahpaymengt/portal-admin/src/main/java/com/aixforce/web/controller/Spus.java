/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.web.controller;

import com.aixforce.common.utils.JsonMapper;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.item.dto.AttributeDto;
import com.aixforce.item.dto.RichAttribute;
import com.aixforce.item.model.AttributeKey;
import com.aixforce.item.model.Spu;
import com.aixforce.item.service.AttributeService;
import com.aixforce.item.service.SpuService;
import com.aixforce.web.misc.MessageSources;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-10
 */
@Controller
@RequestMapping("/api")
public class Spus {
    private final static Logger log = LoggerFactory.getLogger(Spus.class);

    @Autowired
    private SpuService spuService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private MessageSources messageSources;

    @RequestMapping(value = "/spus/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RichSpu find(@PathVariable("id")Long spuId){
        Spu spu = spuService.findById(spuId);
        List<RichAttribute> spuAttributes = attributeService.findSpuAttributesBy(spuId);
        List<AttributeKey> skuAttributeKeys = attributeService.findSkuKeysBy(spuId);
        List<AttributeDto> attributeDtos = createDtoFrom(spuAttributes,skuAttributeKeys);
        RichSpu richSpu = new RichSpu();
        richSpu.setSpu(spu);
        richSpu.setAttributes(attributeDtos);
        return richSpu;
    }

    //no type needed
    private List<AttributeDto> createDtoFrom(List<RichAttribute> spuAttributes, List<AttributeKey> skuAttributeKeys) {
        List<AttributeDto> attributeDtos = Lists.newArrayListWithCapacity(spuAttributes.size()+skuAttributeKeys.size());
        for (RichAttribute spuAttribute : spuAttributes) {
            AttributeDto attributeDto = new AttributeDto(spuAttribute.getAttributeKeyId(),null,spuAttribute.getAttributeValue(),false);
            attributeDtos.add(attributeDto);
        }
        for (AttributeKey skuAttributeKey : skuAttributeKeys) {
            AttributeDto attributeDto = new AttributeDto(skuAttributeKey.getId(),null,null,true);
            attributeDtos.add(attributeDto);
        }
        return attributeDtos;
    }

    @RequestMapping(value = "/spus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String create(@RequestParam("data") String data) {
        try {
            RichSpu richSpu = JsonMapper.nonEmptyMapper().fromJson(data, RichSpu.class);
            Spu spu = richSpu.getSpu();
            spuService.create(spu);
            List<AttributeDto> attributes = richSpu.getAttributes();
            if(attributes!=null && !attributes.isEmpty()){
                attributeService.addForSpu(spu.getId(), attributes);
            }
            return "ok";
        } catch (Exception e) {
            log.error("failed to create spu (data={}),cause:{}", data, e);
            throw new JsonResponseException(500, messageSources.get("spu.create.fail"));
        }
    }

    @RequestMapping(value = "/spus", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String update(@RequestParam("data") String data) {
        try {
            RichSpu richSpu = JsonMapper.nonEmptyMapper().fromJson(data, RichSpu.class);
            Spu spu = richSpu.getSpu();
            if(spu.getId() == null){
                throw new JsonResponseException(500,"spu.id.not.specify") ;
            }
            spuService.update(spu);
            List<AttributeDto> attributes = richSpu.getAttributes();
            attributeService.addForSpu(spu.getId(), attributes);
            return "ok";
        } catch (JsonResponseException e) {
            log.error("failed to update spu (data={}),cause:{}", data, e);
            throw new JsonResponseException(500, messageSources.get("spu.update.fail"));
        }
    }



    @RequestMapping(value = "/spus/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String delete(@PathVariable("id") Long id) {
        try {
            spuService.delete(id);
            return messageSources.get("spu.delete.success");
        } catch (Exception e) {
            log.error("failed to delete spu (id={}),cause:{}", id, e);
            throw new JsonResponseException(500, messageSources.get("spu.delete.fail"));
        }
    }


    public static class RichSpu {
        @Getter
        @Setter
        private Spu spu;

        @Getter
        @Setter
        private List<AttributeDto> attributes;
    }


}
