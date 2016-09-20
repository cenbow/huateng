/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.service;

import com.aixforce.exception.ServiceException;
import com.aixforce.item.dao.redis.RedisAttributeIndexDao;
import com.aixforce.item.dao.redis.RedisAttributeKeyDao;
import com.aixforce.item.dao.redis.RedisAttributeValueDao;
import com.aixforce.item.dao.redis.RedisSpuAttributeDao;
import com.aixforce.item.dto.AttributeDto;
import com.aixforce.item.dto.RichAttribute;
import com.aixforce.item.model.AttributeKey;
import com.aixforce.item.model.AttributeValue;
import com.aixforce.item.model.Spu;
import com.aixforce.item.model.SpuAttribute;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Iterables.limit;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-30
 */
@Service("attributeService")
public class AttributeServiceImpl implements AttributeService {
    private final static Logger log = LoggerFactory.getLogger(AttributeServiceImpl.class);
    public static final Predicate<AttributeDto> NON_ENUMERALE_PREDICATE = new Predicate<AttributeDto>() {
        @Override
        public boolean apply(AttributeDto attributeDto) {
            return Objects.equal(attributeDto.getType(), AttributeKey.ValueType.NOT_ENUM.toNumber());
        }
    };

    @Autowired
    private RedisAttributeKeyDao attributeKeyDao;

    @Autowired
    private RedisAttributeValueDao attributeValueDao;

    @Autowired
    private RedisSpuAttributeDao spuAttributeDao;

    @Autowired
    private RedisAttributeIndexDao attributeIndexDao;

    @Autowired
    private SpuService spuService;

    @Autowired
    private Forrest forrest;


    private static final Predicate<AttributeDto> SKU_PREDICATE = new Predicate<AttributeDto>() {
        @Override
        public boolean apply(AttributeDto attributeDto) {
            return attributeDto.getIsSku();
        }
    };

    /**
     * ********************** Category related service start *************************************************
     */

    @Override
    public List<AttributeKey> addCategoryAttributeKey(Long categoryId, String keyName,
                                                      AttributeKey.ValueType valueType) {
        try {

            AttributeKey attributeKey = attributeKeyDao.findByName(keyName.trim());
            if (attributeKey == null) { //if attributeKey not exists, create it first
                attributeKey = new AttributeKey();
                attributeKey.setName(keyName.trim());
                attributeKey.setValueType(valueType.toNumber());
                attributeKeyDao.create(attributeKey);
                log.info("created {} succeed.", attributeKey);
            }
            attributeIndexDao.addCategoryAttributeKey(categoryId, attributeKey.getId());
            //invalid cache
            forrest.invalidateCategoryKeyCache(categoryId);
            return findCategoryAttributeKeysBy(categoryId);
        } catch (Exception e) {
            log.error("failed to add categoryAttributeKeys for categoryId={} && attributeKeyName={},cause:{}",
                    categoryId, keyName, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }

    }

    @Override
    public List<AttributeValue> addCategoryAttributeValue(Long categoryId, Long keyId, String value) {
        try {

            AttributeValue attributeValue = attributeValueDao.findByValue(value.trim());
            if (attributeValue == null) {  //if attribute value not exists, create it first

                attributeValue =attributeValueDao.create(value.trim());
                log.info("created {} succeed.", attributeValue);
            }

            attributeIndexDao.addCategoryAttributeValue(categoryId, keyId, attributeValue.getId());

            //invalid value cache
            forrest.invalidateCategoryValueCache(categoryId, keyId);

            return findCategoryAttributeValuesBy(categoryId, keyId);
        } catch (Exception e) {
            log.error("failed to add categoryAttributeValue for categoryId={},attributeKeyId={},cause:{}",
                    categoryId, keyId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    /*@Override
    public List<AttributeKey> addKeyForSpu(Long spuId, Long attributeKeyId, AttributeKeyType attributeKeyType) {

        //todo
        if(AttributeKeyType.SPU == attributeKeyType){
            attributeIndexDao.addSpuKeyOfSpu(spuId, attributeKeyId);
        }else{
            attributeIndexDao.addSkuKeyOfSpu(spuId, attributeKeyId);
            forrest.invalidateSkuKeyCache(spuId);
        }
        //todo: return all attribute key
        return null;
    }

    @Override
    public List<AttributeValue> addSkuValueOfSpu(Long spuId, Long attributeKeyId, Long attributeValueId) {
        attributeIndexDao.addSkuValueOfSpu(spuId, attributeKeyId,attributeValueId);
        forrest.invalidateSkuValueCache(spuId,attributeKeyId);
        return forrest.getAttributeValuesBySpuAndKey(spuId,attributeKeyId);
    }*/

    //todo: do we need the next two methods?
    @Override
    public void updateAttributeKey(AttributeKey attributeKey) {
        checkArgument(attributeKey.getId() != null, "id can not be null");
        try {
            attributeKeyDao.update(attributeKey);
            log.debug("succeed to update attributeKey to {}", attributeKey);
        } catch (Exception e) {
            log.error("failed to update attributeKey to {},cause:{}", attributeKey, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateAttributeValue(AttributeValue attributeValue) {
        checkArgument(attributeValue.getId() != null, "id can not be null");
        try {
            attributeValueDao.update(attributeValue);
            log.debug("succeed to update attributeValue to {}", attributeValue);
        } catch (Exception e) {
            log.error("failed to update attributeValue to {},cause:{}", attributeValue, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    /**
     * currently we should only change rank of category attribute key
     *
     * @param categoryId category id
     * @param from       source position ,0-based
     * @param to         target position ,0-based
     */
    @Override
    public void adjustCategoryAttributeKeyRank(Long categoryId, Integer from, Integer to) {

    }

    /**
     * currently we should only change rank of category attribute value
     *
     * @param categoryId     category id
     * @param attributeKeyId attribute key id
     * @param from           source position ,0-based
     * @param to             target position ,0-based
     */
    @Override
    public void adjustCategoryAttributeValueRank(Long categoryId, Long attributeKeyId, Integer from, Integer to) {

    }


    /**
     * delete a Category-AttributeKey relation ,also delete the correspond attributeValue
     *
     * @param categoryId     category id
     * @param attributeKeyId attributeKey id
     */
    @Override
    public void deleteCategoryAttributeKey(Long categoryId, Long attributeKeyId) {
        checkArgument(categoryId != null, "categoryId can't be null");
        checkArgument(attributeKeyId != null, "attributeKeyId can't be null");
        try {
            attributeIndexDao.removeCategoryAttributeKey(categoryId, attributeKeyId);
            forrest.invalidateCategoryValueCache(categoryId, attributeKeyId);
            forrest.invalidateCategoryKeyCache(categoryId);
            log.debug("succeed to delete categoryAttributeKey where categoryId={} and attributeKeyId={}",
                    categoryId, attributeKeyId);
        } catch (Exception e) {
            log.error("failed to delete category-attributeKey relation where categoryId={} and attributeKeyId={},cause:{}",
                    categoryId, attributeKeyId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    /**
     * delete a Category-AttributeValue relation
     *
     * @param categoryId       category id
     * @param attributeKeyId   attribute key id
     * @param attributeValueId attributeValue id
     */
    @Override
    public void deleteCategoryAttributeValue(Long categoryId, Long attributeKeyId, Long attributeValueId) {
        checkArgument(categoryId != null, "categoryId can not be null");
        checkArgument(attributeKeyId != null, "attributeKeyId can not be null");
        checkArgument(attributeValueId != null, "attributeValueId can not be null");
        try {
            attributeIndexDao.removeCategoryAttributeValue(categoryId, attributeKeyId, attributeValueId);
            forrest.invalidateCategoryValueCache(categoryId, attributeKeyId);

        } catch (Exception e) {
            log.error("failed to delete CategoryAttributeValue where categoryId={} and attributeKeyId={}" +
                    "and attributeValueId={}, cause:{}",
                    categoryId, attributeKeyId, attributeValueId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    /**
     * find attribute Keys by category id , the results is sorted by categoryAttributeKey.getRank()
     *
     * @param categoryId category id
     * @return attribute keys sorted by rank
     */
    @Override
    @Nonnull
    public List<AttributeKey> findCategoryAttributeKeysBy(Long categoryId) {
        checkArgument(categoryId != null, "categoryId should be specified");
        return forrest.getAttributeKeys(categoryId);
    }


    /**
     * find attribute values by category id and attribute key id, the results is sorted by categoryAttributeKey.getRank()
     *
     * @param categoryId     category id
     * @param attributeKeyId attribute key id
     * @return attribute values sorted by rank
     */
    @Override
    public List<AttributeValue> findCategoryAttributeValuesBy(Long categoryId, Long attributeKeyId) {
        checkArgument(categoryId != null, "categoryId can't be null");
        checkArgument(attributeKeyId != null, "attributeKeyId can't be null");
        return forrest.getAttributeValues(categoryId, attributeKeyId);
    }


    /**
     * find attribute key values by category id, attribute keys and values are all sorted by rank
     *
     * @param categoryId category id
     * @param keyCount   expected key count returned
     * @param valueCount expected value count returned
     * @return attribute values sorted by category attribute rank
     */
    @Override
    public Multimap<AttributeKey, AttributeValue> findCategoryAttributesBy(Long categoryId, Integer keyCount, Integer valueCount) {
        List<AttributeKey> attributeKeys = findCategoryAttributeKeysBy(categoryId);
        ImmutableSetMultimap.Builder<AttributeKey, AttributeValue> builder =
                new ImmutableSetMultimap.Builder<AttributeKey, AttributeValue>();
        Iterable<AttributeKey> keys = limit(attributeKeys, keyCount);
        for (AttributeKey attributeKey : keys) {
            builder = builder.putAll(attributeKey, limit(findCategoryAttributeValuesBy(categoryId, attributeKey.getId()), valueCount));
        }
        return builder.build();
    }


    /******************************** Category related service end *******************************************/


    /**
     * ************************ Spu related service start ***********************************
     */
    /*@Override
    public void createSpuAttribute(SpuAttribute spuAttribute) {
        checkArgument(spuAttribute.getId() == null, "not a new spuAttribute");
        checkArgument(spuAttribute.getSpuId() != null, "spu id can not be null");
        checkArgument(spuAttribute.getAttributeKeyId() != null, "attributeKeyId can not be null");
        checkArgument(spuAttribute.getAttributeValueId() != null, "attributeValueId  can not be null");
        try {
            //todo(jlchen) handle rank
            spuAttributeDao.create(spuAttribute);
            log.debug("succeed to create spuAttribute : {}", spuAttribute);

            //invalid cache
            forrest.invalidateSpuAttributeCache(spuAttribute.getSpuId());
        } catch (Exception e) {
            log.error("failed to create spuAttribute : {}, cause: {}", spuAttribute, e);
            throw new ServiceException(e);
        }
    }*/

    @Override
    public void adjustSpuAttributeRank(Long spuId, Integer from, Integer to) {

    }

    @Override
    public void deleteSpuAttribute(Long id) {
        checkArgument(id != null, "id should be specified");
        try {
            SpuAttribute spuAttribute = spuAttributeDao.findById(id);
            if (spuAttribute == null) {
                log.warn("can not find spuAttribute with id {}", id);
                forrest.invalidateSpuAttributeCache(id);
                return;
            }
            spuAttributeDao.delete(id);

            Long spuId = spuAttribute.getSpuId();
            log.debug("succeed to delete {}", spuAttribute);
            forrest.invalidateSpuAttributeCache(spuId);
        } catch (Exception e) {
            log.error("failed to delete spuAttribute whose id={}, cause:{}", id, e);
            throw new ServiceException(e);
        }
    }


    @Override
    @Nonnull
    public List<RichAttribute> findSpuAttributesBy(Long spuId) {
        return forrest.getRichAttributes(spuId);
    }

    @Override
    public List<AttributeKey> findSkuKeysBy(Long spuId) {
        return forrest.getSkuKeysForSpu(spuId);
    }

    @Override
    public Map<AttributeKey,List<AttributeValue>> findSkuAttributesBy(Long spuId){
        Spu spu = spuService.findById(spuId);
        if(spu == null){
            log.error("can not find spu where id = {}",spuId);
            throw new ServiceException("spu not found");
        }
        List<AttributeKey> skuKeys = findSkuKeysBy(spuId);
        Map<AttributeKey,List<AttributeValue>> result = Maps.newHashMapWithExpectedSize(skuKeys.size());
        for (AttributeKey skuKey : skuKeys) {
            Long skuKeyId = skuKey.getId();
            List<AttributeValue> attributeValues = forrest.getAttributeValues(spu.getCategoryId(), skuKeyId);
            result.put(skuKey,attributeValues);
        }
        return result;
    }

    @Override
    public void addForSpu(Long spuId, List<AttributeDto> attributes) {
        //1.save sku key first
        Iterable<AttributeDto> skuKeys = Iterables.filter(attributes, SKU_PREDICATE);

        //sku key number can not greater than 3
        if(Iterables.size(skuKeys)>3){
            throw new ServiceException("sku.key.overflow");
        }

        attributeIndexDao.addSkuKeysForSpu(spuId,skuKeys);

        //remove sku keys from attributes to be handled
         Iterables.removeIf(attributes, SKU_PREDICATE);

        //2.create spuAttributeList
        List<SpuAttribute> spuAttributes = Lists.newArrayListWithCapacity(attributes.size());

        // 2.1 save not-enumerate-attributes
        Iterable<AttributeDto> nonEnumerable = Iterables.filter(attributes, NON_ENUMERALE_PREDICATE);

        for (AttributeDto attributeDto : nonEnumerable) {
            String value = attributeDto.getValue();
            AttributeValue attributeValue = attributeValueDao.findByValue(value);
            if(attributeValue == null){
                attributeValue = attributeValueDao.create(value);
            }
            SpuAttribute spuAttribute = new SpuAttribute();
            spuAttribute.setAttributeKeyId(attributeDto.getAttributeKeyId());
            spuAttribute.setAttributeValueId(attributeValue.getId());
            spuAttribute.setSpuId(spuId);
            spuAttributes.add(spuAttribute);
        }

        Iterables.removeIf(attributes,NON_ENUMERALE_PREDICATE);

        //2.2 for enumerable attributes
        for (AttributeDto attribute : attributes) {
            SpuAttribute spuAttribute = new SpuAttribute();
            spuAttribute.setAttributeKeyId(attribute.getAttributeKeyId());
            spuAttribute.setAttributeValueId(Long.parseLong(attribute.getValue()));
            spuAttribute.setSpuId(spuId);
            spuAttributes.add(spuAttribute);
        }

        //3. save spu Attributes
        spuAttributeDao.create(spuAttributes);

        //4. invalidate cache
        forrest.invalidateSkuKeyCache(spuId);
        forrest.invalidateSpuAttributeCache(spuId);
        forrest.invalidateSkuKeyCache(spuId);
    }
    /****************************** Spu related service end ***********************************************/

}
