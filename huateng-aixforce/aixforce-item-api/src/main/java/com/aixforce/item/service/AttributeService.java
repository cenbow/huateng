/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.service;

import com.aixforce.item.dto.AttributeDto;
import com.aixforce.item.dto.RichAttribute;
import com.aixforce.item.model.AttributeKey;
import com.aixforce.item.model.AttributeValue;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-30
 */
public interface AttributeService {
    void updateAttributeKey(AttributeKey attributeKey);

    void updateAttributeValue(AttributeValue attributeValue);


    /**************************** Category related service start *********************************/
    /**
     * currently we should only change rank of category attribute key
     * @param categoryId category id
     * @param  from source position
     * @param to target position
     */
    void adjustCategoryAttributeKeyRank(Long categoryId,Integer from,Integer to);

    /**
     * currently we should only change rank of category attribute value
     * @param categoryId  category id
     * @param attributeKeyId attribute key id
     * @param  from source position ,0-based
     * @param to target position,0-based
     */
    void adjustCategoryAttributeValueRank(Long categoryId,Long attributeKeyId,Integer from,Integer to);

    /**
     * delete a Category-AttributeKey relation,also delete the correspond attributeValue
     * @param categoryId   category id ,0-based
     * @param attributeKeyId  attributeKey id ,0-based
     */
    void deleteCategoryAttributeKey(Long categoryId,Long attributeKeyId);

    /**
     * delete a Category-AttributeValue relation
     * @param categoryId   category id
     * @param attributeKeyId attribute key id
     * @param attributeValueId  attributeValue id
     */
    void deleteCategoryAttributeValue(Long categoryId,Long attributeKeyId,Long attributeValueId);

    /**
     * find attribute Keys by category id , the results is sorted by categoryAttributeKey.getRank()
     *
     * @param categoryId category id
     * @return attribute keys sorted by rank
     */
    List<AttributeKey> findCategoryAttributeKeysBy(Long categoryId);

    /**
     * find attribute values by category id and attribute key id
     * @param categoryId  category id
     * @param attributeKeyId attribute key id
     * @return attribute values sorted by rank
     */
    List<AttributeValue> findCategoryAttributeValuesBy(Long categoryId, Long attributeKeyId);


    public List<AttributeKey> addCategoryAttributeKey( Long categoryId, String keyName, AttributeKey.ValueType valueType);

    public List<AttributeValue> addCategoryAttributeValue(Long categoryId,Long keyId,String value);

    /**
     * find attribute key values by category id ,attribute keys and values are all sorted by rank
     * @param categoryId  category id
     * @param keyCount expected key count returned
     * @param valueCount expected value count returned
     * @return attribute values sorted by category attribute rank
     */
    Multimap<AttributeKey,AttributeValue> findCategoryAttributesBy(Long categoryId, Integer keyCount, Integer valueCount);

    /**************************** Category related service end *********************************/

    /**************************** Spu related service start ************************************/


   // void createSkuAttribute(SkuAttribute skuAttribute);

    void adjustSpuAttributeRank(Long spuId, Integer from, Integer to);

    void deleteSpuAttribute(Long id);

    List<RichAttribute> findSpuAttributesBy(Long spuId);

    List<AttributeKey> findSkuKeysBy(Long spuId);

    void addForSpu(Long spuId, List<AttributeDto> attributes);

    Map<AttributeKey,List<AttributeValue>> findSkuAttributesBy(Long spuId);
}
