/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.service;

import com.aixforce.item.dao.redis.RedisAttributeKeyDao;
import com.aixforce.item.dao.redis.RedisAttributeValueDao;
import com.aixforce.item.dao.redis.RedisSpuAttributeDao;
import com.aixforce.item.dao.redis.RedisSpuDao;
import com.aixforce.item.dto.RichAttribute;
import com.aixforce.item.model.AttributeKey;
import com.aixforce.item.model.AttributeValue;
import com.aixforce.item.model.Spu;
import com.aixforce.item.model.SpuAttribute;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.getLast;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-10-06
 */
@Component
public class Forrest {
    private final static Logger log = LoggerFactory.getLogger(Forrest.class);
    public static final Splitter SPLITTER = Splitter.on("_");


    @Autowired
    private  RedisSpuDao spuDao;

    @Autowired
    private  RedisAttributeKeyDao attributeKeyDao;

    @Autowired
    private  RedisAttributeValueDao attributeValueDao;

    @Autowired
    private  RedisSpuAttributeDao spuAttributeDao;




    /**
     * cache for category id to attribute keys
     */
    private LoadingCache<Long, List<AttributeKey>> categoryKeyCache;

    /**
     * cache for categoryId +"_"+ attributeKeyId  ==> list of attribute values
     */
    private LoadingCache<String, List<AttributeValue>> categoryValueCache;

    /**
     * spu id ==> list of spuAttribute
     */
    private LoadingCache<Long, List<RichAttribute>> spuAttributeCache;

    /**
     * spu id ==> list of spu attributeKeys
     */
    //private LoadingCache<Long, List<AttributeKey>> spuKeyCache;

    /**
     * spuId ==> list of sku attributeKeys
     */
    private LoadingCache<Long,List<AttributeKey>> skuKeyCache;


    /**
     * sku id ==> list of skuAttribute
     */
    //private LoadingCache<Long,List<RichAttribute>>  skuAttributeCache;

    /**
     * categoryId ==> list of spu
     */
    private LoadingCache<Long,List<Spu>> spusOfCategory;

    @PostConstruct
    public void init() {
        categoryKeyCache = CacheBuilder.newBuilder().build(
                new CacheLoader<Long, List<AttributeKey>>() {
                    @Override
                    public List<AttributeKey> load(Long key) throws Exception {
                        return attributeKeyDao.findByCategoryId(key);
                    }
                });
        categoryValueCache = CacheBuilder.newBuilder().build(
                new CacheLoader<String, List<AttributeValue>>() {
                    @Override
                    public List<AttributeValue> load(String key) throws Exception {
                        Iterable<String> parts = SPLITTER.split(key);
                        Long categoryId = Long.parseLong(getFirst(parts, "-1"));
                        Long attributeKeyId = Long.parseLong(getLast(parts, "-1"));
                        return attributeValueDao.findByCategoryIdAndKeyId(categoryId, attributeKeyId);
                    }
                }
        );
        spuAttributeCache = CacheBuilder.newBuilder().build(
                new CacheLoader<Long, List<RichAttribute>>() {
                    @Override
                    public List<RichAttribute> load(Long spuId) throws Exception {
                        return loadSpuAttributesBy(spuId);
                    }
                }
        );

        /*spuKeyCache = CacheBuilder.newBuilder().build(
                new CacheLoader<Long, List<AttributeKey>>() {
                    @Override
                    public List<AttributeKey> load(Long spuId) throws Exception {
                        return attributeKeyDao.findSpuKeysBySpuId(spuId);
                    }
                }
        );*/

        skuKeyCache = CacheBuilder.newBuilder().build(new CacheLoader<Long, List<AttributeKey>>() {
            @Override
            public List<AttributeKey> load(Long spuId) throws Exception {
                return attributeKeyDao.findSkuKeysBySpuId(spuId);
            }
        });



        /*skuAttributeCache = CacheBuilder.newBuilder().build(
                new CacheLoader<Long, List<RichAttribute>>() {
                    @Override
                    public List<RichAttribute> load(Long skuId) throws Exception {
                        return loadSkuAttributesBy(skuId);
                    }
                }
        );*/

        spusOfCategory = CacheBuilder.newBuilder().build(
            new CacheLoader<Long,List<Spu>>(){

                @Override
                public List<Spu> load(Long categoryId) throws Exception {
                    return spuDao.findByCategoryId(categoryId);
                }
            }
        );

    }

    /************************* category related cache methods *********************************/






    /************************* attribute related cache methods *********************************/

    public List<AttributeKey> getAttributeKeys(Long categoryId) {
        return categoryKeyCache.getUnchecked(categoryId);
    }

    public List<AttributeValue> getAttributeValues(Long categoryId,Long attributeKeyId) {
        return categoryValueCache.getUnchecked(makeValueCacheKey(categoryId,attributeKeyId));
    }

    public List<RichAttribute> getRichAttributes(Long spuId) {
        return spuAttributeCache.getUnchecked(spuId);
    }

    public List<AttributeKey> getSkuKeysForSpu(Long spuId){
        return skuKeyCache.getUnchecked(spuId);
    }

/*
    public List<AttributeKey> getAttributeKeysOf(Long spuId){
        return spuKeyCache.getUnchecked(spuId);
    }
*/



    public List<Spu> getSpusOfCategory(Long categoryId){
        return spusOfCategory.getUnchecked(categoryId);
    }

    public void invalidateCategoryKeyCache(Long categoryId) {
        categoryKeyCache.invalidate(categoryId);
    }

    public void invalidateSkuKeyCache(Long spuId){
        skuKeyCache.invalidate(spuId);
    }

    public void invalidateCategoryValueCache(Long categoryId, Long attributeKeyId) {
        categoryValueCache.invalidate(makeValueCacheKey(categoryId,attributeKeyId));
    }

    public void invalidateSpuAttributeCache(Long spuId) {
        spuAttributeCache.invalidate(spuId);
    }

    public void invalidateSpusOfCategory(Long categoryId){
        spusOfCategory.invalidate(categoryId);
    }

    private String makeValueCacheKey(Long belongId, Long attributeKeyId) {
        return belongId + "_" + attributeKeyId;
    }


    private List<RichAttribute> loadSpuAttributesBy(Long spuId) {
        List<SpuAttribute> spuAttributes = spuAttributeDao.findBySpuId(spuId);
        List<RichAttribute> richAttributes = Lists.newArrayListWithCapacity(spuAttributes.size());
        for (SpuAttribute spuAttribute : spuAttributes) {
            Long keyId = spuAttribute.getAttributeKeyId();
            Long valueId = spuAttribute.getAttributeValueId();
            String keyName = attributeKeyDao.findById(keyId).getName();
            String valueName = attributeValueDao.findById(valueId).getValue();
            RichAttribute richAttribute = new RichAttribute();
            richAttribute.setBelongId(spuId);
            richAttribute.setAttributeKeyId(keyId);
            richAttribute.setAttributeKey(keyName);
            richAttribute.setAttributeValueId(valueId);
            richAttribute.setAttributeValue(valueName);
            richAttributes.add(richAttribute);
        }
        return richAttributes;
    }

    /*private List<RichAttribute> loadSkuAttributesBy(Long skuId) {
        List<SkuAttribute> skuAttributes = skuAttributeDao.findBySkuId(skuId);
        List<RichAttribute> richAttributes = Lists.newArrayListWithCapacity(skuAttributes.size());
        for (SkuAttribute skuAttribute : skuAttributes) {
            Long keyId = skuAttribute.getAttributeKeyId();
            Long valueId = skuAttribute.getAttributeValueId();
            String keyName = attributeKeyDao.findById(keyId).getName();
            String valueName = attributeValueDao.findById(valueId).getValue();
            RichAttribute richAttribute = new RichAttribute();
            richAttribute.setBelongId(skuId);
            richAttribute.setAttributeKeyId(keyId);
            richAttribute.setAttributeKey(keyName);
            richAttribute.setAttributeValueId(valueId);
            richAttribute.setAttributeValue(valueName);
            richAttributes.add(richAttribute);
        }
        return richAttributes;
    }
*/

    public void invalidAll(){
        log.info("begin to invalidate all forrest caches");
        this.categoryKeyCache.invalidateAll();
        this.categoryValueCache.invalidateAll();
        this.spuAttributeCache.invalidateAll();
        this.spusOfCategory.invalidateAll();
        this.skuKeyCache.invalidateAll();
        log.info("succeed to invalidate all forrest caches");
    }
}
