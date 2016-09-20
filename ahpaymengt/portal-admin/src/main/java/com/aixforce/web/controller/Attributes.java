/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.web.controller;

import com.aixforce.exception.JsonResponseException;
import com.aixforce.item.model.AttributeKey;
import com.aixforce.item.model.AttributeValue;
import com.aixforce.item.service.AttributeService;
import com.aixforce.web.misc.MessageSources;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-03
 */
@Controller
@RequestMapping("/api")
public class Attributes {
    private final static Logger log = LoggerFactory.getLogger(Attributes.class);
    @Autowired
    private AttributeService attributeService;
    @Autowired
    private MessageSources messageSources;

    @RequestMapping(value = "/categories/{categoryId}/keys", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AttributeKey> loadKeys(@PathVariable("categoryId") Long categoryId) {
        try {
            return attributeService.findCategoryAttributeKeysBy(categoryId);
        } catch (Exception e) {
            log.error("failed to query attributeKeys for {},cause:{}", categoryId, e);
            throw new JsonResponseException(500, messageSources.get("attribute.category.key.query.fail"));
        }
    }

    @RequestMapping(value = "/categories/{categoryId}/keys", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AttributeKey> adjustCategoryAttributeKeyRank(@PathVariable("categoryId") Long categoryId,
                                                             @RequestParam("from") Integer from, @RequestParam("to") Integer to) {
        try {
            attributeService.adjustCategoryAttributeKeyRank(categoryId, from, to);
            return attributeService.findCategoryAttributeKeysBy(categoryId);
        } catch (Exception e) {
            log.error("failed to adjust category(id={}) attribute key rank from {} to {},cause:{}",
                    categoryId, from, to, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500, messageSources.get("attribute.category.key.adjust.success"));
        }
    }

    @RequestMapping(value = "/categories/{categoryId}/keys/{attributeKeyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteCategoryAttributeKey(@PathVariable("categoryId") Long categoryId, @PathVariable("attributeKeyId") Long attributeKeyId) {
        try {
            attributeService.deleteCategoryAttributeKey(categoryId, attributeKeyId);
            return messageSources.get("attribute.category.key.delete.success");
        } catch (Exception e) {
            log.error("failed to delete categoryAttributeKey(categoryId={},attributeKeyId={}),cause:{}",
                    categoryId, attributeKeyId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500, messageSources.get("attribute.category.key.delete.fail"));
        }
    }

    @RequestMapping(value = "/categories/{categoryId}/keys/{attributeKeyId}/values", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AttributeValue> loadValues(@PathVariable("categoryId") Long categoryId, @PathVariable("attributeKeyId") Long attributeKeyId) {
        try {
            return attributeService.findCategoryAttributeValuesBy(categoryId, attributeKeyId);
        } catch (Exception e) {
            log.error("failed to query attributeValues for categoryId={} && attributeKeyId={},cause:{}",
                    categoryId, attributeKeyId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500, messageSources.get("attribute.category.value.query.fail"));
        }
    }


    @RequestMapping(value = "/categories/{categoryId}/values", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AttributeValue> adjustCategoryAttributeValueRank(
            @PathVariable("categoryId") Long categoryId, @RequestParam("attributeKeyId") Long attributeKeyId,
            @RequestParam("from") Integer from, @RequestParam("to") Integer to) {
        try {
            attributeService.adjustCategoryAttributeValueRank(categoryId, attributeKeyId, from, to);
            return attributeService.findCategoryAttributeValuesBy(categoryId, attributeKeyId);
        } catch (Exception e) {
            log.error("failed to adjust category(id={}) attribute(key={}) value rank from {} to {},cause:{}",
                    categoryId, attributeKeyId, from, to, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500, messageSources.get("attribute.category.value.create.fail"));
        }
    }

    @RequestMapping(value = "/categories/{categoryId}/keys/{attributeKeyId}/values/{attributeValueId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteCategoryAttributeValue(@PathVariable("categoryId") Long categoryId,
                                               @PathVariable("attributeKeyId") Long attributeKeyId, @PathVariable("attributeValueId") Long attributeValueId) {
        try {
            attributeService.deleteCategoryAttributeValue(categoryId, attributeKeyId, attributeValueId);
            return messageSources.get("attribute.category.value.delete.success");
        } catch (Exception e) {
            log.error("failed to delete categoryAttributeValue(categoryId={},attributeKeyId={},attributeValueId={}),cause:{}",
                    categoryId, attributeKeyId, attributeValueId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500, messageSources.get("attribute.category.value.delete.fail"));
        }
    }


    @RequestMapping(value = "/categories/{categoryId}/keys", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AttributeKey> addCategoryAttributeKey(@PathVariable("categoryId") Long categoryId, @RequestParam("name") String attributeKeyName,
                                                      @RequestParam(value = "valueType", defaultValue = "1") Integer valueType) {
        try {
            return attributeService.addCategoryAttributeKey(categoryId, attributeKeyName,
                    AttributeKey.ValueType.fromNumber(valueType));
        } catch (Exception e) {
            log.error("failed to add categoryAttributeKeys for categoryId={} && attributeKeyName={},cause:{}",
                    categoryId, attributeKeyName, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500, messageSources.get("attribute.category.key.add.fail"));
        }
    }

    @RequestMapping(value = "/categories/{categoryId}/keys/{attributeKeyId}/values", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AttributeValue> addCategoryAttributeValues(@PathVariable("categoryId") Long categoryId, @PathVariable("attributeKeyId") Long attributeKeyId,
                                                           @RequestParam("data") String attributeValue) {
        try {
            return attributeService.addCategoryAttributeValue(categoryId, attributeKeyId, attributeValue);
        } catch (Exception e) {
            log.error("failed to add categoryAttributeValues for categoryId={} && attributeKeyId={},cause:{}",
                    categoryId, attributeKeyId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500, messageSources.get("attribute.category.value.add.fail"));
        }
    }

   /* @RequestMapping(value = "/spus/{spuId}/keys", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AttributeKey> addKeyForSpu(@PathVariable("spuId") Long spuId, @RequestParam("keyId") Long attributeKeyId,
                                        @RequestParam(value = "type", defaultValue = "0") Integer type) {
        try {
            return attributeService.addKeyForSpu(spuId, attributeKeyId, AttributeKeyType.fromNumber(type));
        } catch (Exception e) {
            log.error("failed to add skuKey for spu={} && skuKeyId={},cause:{}",
                    new Object[]{spuId, attributeKeyId, Throwables.getStackTraceAsString(Throwables.getRootCause(e))});
            throw new JsonResponseException(500, messageSources.get("attribute.spu.key.add.fail"));
        }
    }

    @RequestMapping(value = "/spus/{spuId}/keys/{skuKeyId}/values", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AttributeValue> addAttributeValueForSkuKey(@PathVariable("spuId")Long spuId,
                         @PathVariable("skuKeyId")Long attributeKeyId,@RequestParam("skuValueId")Long attributeValueId){
        try{
            return attributeService.addSkuValueOfSpu(spuId,attributeKeyId,attributeValueId);
        }catch (Exception e) {
            log.error("failed to add sku value for spuId={} && skuKeyId={},cause:{}",
                    new Object[]{spuId, attributeKeyId, Throwables.getStackTraceAsString(Throwables.getRootCause(e))});
            throw new JsonResponseException(500, messageSources.get("attribute.spu.value.add.fail"));
        }
    }*/

}
