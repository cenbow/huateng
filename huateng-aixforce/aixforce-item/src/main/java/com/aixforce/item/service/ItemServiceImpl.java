/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.service;

import com.aixforce.common.model.Paging;
import com.aixforce.common.utils.JsonMapper;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.exception.ServiceException;
import com.aixforce.item.dao.mysql.ItemDao;
import com.aixforce.item.dao.mysql.ItemDetailDao;
import com.aixforce.item.dao.mysql.SkuDao;
import com.aixforce.item.dto.RichAttribute;
import com.aixforce.item.dto.RichItem;
import com.aixforce.item.dto.SkuGroup;
import com.aixforce.item.model.Item;
import com.aixforce.item.model.ItemDetail;
import com.aixforce.item.model.Sku;
import com.aixforce.search.ESClient;
import com.aixforce.user.base.BaseUser;
import com.google.common.base.*;
import com.google.common.collect.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-03
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService {
    private final static Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private static final int PAGE_SIZE = 200;
    public static final RichItem DUMB = new RichItem();

    @Autowired
    private ESClient esClient;

    @Autowired
    private RichItems richItems;

    @Autowired
    private Validator validator;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemDetailDao itemDetailDao;

    @Autowired
    private SkuDao skuDao;

    @Autowired
    private Forrest forrest;


    @Override
    public List<Item> findByIds(List<Long> ids) {
        if (Iterables.isEmpty(ids)) {
            return Collections.emptyList();
        }
        try {
            return itemDao.findByIds(ids);
        } catch (Exception e) {
            log.error("failed to find items for ids {},cause:{}", ids, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, Object> detail(Long itemId) {
        Item item = findById(itemId);
        if (item == null) {
            return Collections.emptyMap();
        }
        ItemDetail itemDetail = itemDetailDao.findByItemId(itemId);
        if (itemDetail == null) {
            return Collections.emptyMap();
        }
        List<Sku> skus = skuDao.findByItemId(itemId);
        //List<Sku> skus = Collections.emptyList();
        return ImmutableMap.of("item", item, "itemDetail", itemDetail, "skus", skus);
    }

    /**
     * 卖家后台商品列表,用于管理店铺内商品
     *
     * @param baseUser 系统注入的用户
     * @param pageNo   起始页码
     * @param size     返回条数
     * @param params   搜索参数
     * @return 商品列表
     */
    @Override
    public Paging<Item> sellerItems(BaseUser baseUser, Integer pageNo, Integer size, Map<String, String> params) {
        try {
            Map<String, Object> builder = Maps.newHashMap();
            String keywords = params.get("q");
            if (!Strings.isNullOrEmpty(keywords)) {
                builder.put("name", keywords.trim());
            }

            String priceFrom = params.get("p_f");
            String priceTo = params.get("p_t");
            if (!Strings.isNullOrEmpty(priceFrom)) {
                builder.put("priceFrom", Integer.parseInt(priceFrom)*100);
            }
            if (!Strings.isNullOrEmpty(priceTo)) {
                builder.put("priceTo", Integer.parseInt(priceTo)*100);
            }
            String quantityFrom = params.get("q_f");
            String quantityTo = params.get("q_t");
            if (!Strings.isNullOrEmpty(quantityFrom)) {
                builder.put("quantityFrom", Integer.parseInt(quantityFrom));
            }
            if (!Strings.isNullOrEmpty(quantityTo)) {
                builder.put("quantityTo", Integer.parseInt(quantityTo));
            }

            String soldQuantityFrom = params.get("s_f");
            String soldQuantityTo = params.get("s_t");
            if (!Strings.isNullOrEmpty(soldQuantityFrom)) {
                builder.put("soldQuantityFrom", Integer.parseInt(soldQuantityFrom));
            }
            if (!Strings.isNullOrEmpty(soldQuantityTo)) {
                builder.put("soldQuantityTo", Integer.parseInt(soldQuantityTo));
            }
            String status = Objects.firstNonNull(params.get("status"), "0");
            if (!Strings.isNullOrEmpty(status)) {//分割状态成为list
                Iterable<String> all = Splitter.on(",").trimResults().omitEmptyStrings().split(status);
                List<Integer> concernedStatus = Lists.newArrayListWithCapacity(Iterables.size(all));
                for (String s : all) {
                    concernedStatus.add(Integer.parseInt(s));
                }
                builder.put("status", concernedStatus);
            }
            pageNo = Objects.firstNonNull(pageNo, 1);
            size = Objects.firstNonNull(size, 20);
            size = size > 0 ? size : 20;
            int offset = (pageNo - 1) * size;
            offset = offset > 0 ? offset : 0;
            return itemDao.sellerItems(baseUser.getId(), offset, size, builder);
        } catch (Exception e) {
            log.error("failed to query sellerItems,",e);
            return new Paging<Item>(0L,Collections.<Item>emptyList());
        }
    }

    /**
     * 全量dump搜索引擎
     */
    @Override
    public void fullDump() {
        log.info("[FULL_DUMP_ITEM] full item refresh start");
        Stopwatch stopwatch = Stopwatch.createStarted();
        Long lastId = itemDao.maxId() + 1;  //scan from maxId+1
        int returnSize = PAGE_SIZE;
        int handled = 0;
        while (returnSize == PAGE_SIZE) {
            List<Item> items = itemDao.forDump(lastId, PAGE_SIZE);
            final List<Long> invalidIds = Lists.newArrayList();
            if (!items.isEmpty()) {

                //handle all status except deleted items
                Iterable<RichItem> valid = filterValidItems(items, invalidIds);

                esClient.index("item", valid);
                esClient.delete("item", invalidIds);

                handled += items.size();
                lastId = items.get(items.size() - 1).getId();
                log.info("has indexed {} items,and last handled id is {}", handled, lastId);
                returnSize = items.size();
            } else {
                break;
            }
        }
        stopwatch.stop();
        log.info("[FULL_DUMP_ITEM] full item refresh end, took {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    /**
     * 增量dump搜索引擎
     *
     * @param interval 间隔时间,以分钟计算
     */
    @Override
    public void deltaDump(Integer interval) {
        log.info("[DELTA_DUMP_ITEM] item delta dump start");

        String compared = DATE_TIME_FORMAT.print(new DateTime().minusMinutes(interval));
        Stopwatch stopwatch = Stopwatch.createStarted();
        Long lastId = itemDao.maxId() + 1;  //scan from maxId+1
        int returnSize = PAGE_SIZE;
        int handled = 0;
        while (returnSize == PAGE_SIZE) {
            List<Item> items = itemDao.forDeltaDump(lastId, compared, PAGE_SIZE);
            final List<Long> invalidIds = Lists.newArrayList();
            if (!items.isEmpty()) {
                //handle all status except deleted items
                Iterable<RichItem> valid = filterValidItems(items, invalidIds);

                esClient.index("item", valid);
                esClient.delete("item", invalidIds);

                handled += items.size();
                lastId = items.get(items.size() - 1).getId();
                log.info("has indexed {} items,and last handled id is {}", handled, lastId);
                returnSize = items.size();
            } else {
                break;
            }
        }
        stopwatch.stop();
        log.info("[DELTA_DUMP_ITEM] item delta finished,cost {} millis,handled {} items",stopwatch.elapsed(TimeUnit.MILLISECONDS),handled);
    }

    private Iterable<RichItem> filterValidItems(List<Item> items, final List<Long> invalidIds) {
        return FluentIterable.from(items).filter(new Predicate<Item>() {
            @Override
            public boolean apply(Item input) {
                return Item.Status.DELETED != Item.Status.fromNumber(input.getStatus());
            }}).transform(new Function<Item, RichItem>() {
            @Override
            public RichItem apply(Item input) {
                try {
                    return richItems.make(input);
                } catch (Exception e) {
                    log.error("can not make rich item for item (id={})",input.getId());
                    invalidIds.add(input.getId());
                    return DUMB;
                }
            }
        }).filter(new Predicate<RichItem>() {
            @Override
            public boolean apply(RichItem input) {
                return input.getId()!=null;
            }
        });
    }

    @Override
    @Transactional
    public void create(Item item, ItemDetail itemDetail, List<Sku> skus) {
        checkArgument(item.getId() == null, "not a new item");
        //checkArgument(item.getSiteId() != null, "siteId can not be null");
        checkArgument(item.getUserId() != null, "userId can not be null");
        //checkArgument(itemDetail.getId() == null, "not a new itemDetail");
        checkArgument(!Strings.isNullOrEmpty(item.getName()), "item name can not be empty");
        try {
            Set<ConstraintViolation<Item>> violations = validator.validate(item);
            if (!violations.isEmpty()) {

                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<?> violation : violations) {
                    sb.append(violation.getMessage()).append("\n");
                }
                throw new IllegalArgumentException(sb.toString());

            }
            item.setName(item.getName().trim());
            itemDao.create(item);
            log.debug("succeed to create  {}", item);
            if (itemDetail != null) {
                itemDetail.setItemId(item.getId());
                itemDetailDao.create(itemDetail);
                log.debug("succeed to create {} and {}", item, itemDetail);
            }

            for (Sku sku : skus) {
                sku.setItemId(item.getId());
            }
            skuDao.create(skus);
            //notify search engine, for redisItemDao
//            Date now = new Date();
//            item.setCreatedAt(now);
//            item.setUpdatedAt(now);
//            searchExecutor.submit(indexName, richItems.make(item), SearchExecutor.OP_TYPE.INDEX);
        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, ServiceException.class);
            log.error("failed to create item: {} and itemDetail:{}, cause:{}",
                    item, itemDetail, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public void update(Item item, ItemDetail itemDetail, List<Sku> skus) {
        checkArgument(item.getId() != null, "item id can not be null when updated");
        try {
            itemDao.update(item);
            log.debug("succeed to update item to {}", item);

            itemDetail.setItemId(item.getId());
            itemDetailDao.update(itemDetail);

            for (Sku sku : skus) {
                sku.setItemId(item.getId());
            }
            skuDao.update(skus);
            //notify search engine
//            Item updated = findById(item.getId());
//            searchExecutor.submit(indexName, richItems.make(updated), SearchExecutor.OP_TYPE.INDEX);

        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, JsonResponseException.class);
            log.error("failed to update item to {},cause:{}", item, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    /**
     * 减少库存
     *
     * @param skuId    sku id
     * @param itemId   item id
     * @param quantity 数量
     */
    @Override
    @Transactional
    public void decrementStock(Long skuId, Long itemId, Integer quantity) {
        checkArgument(skuId != null, "skuId can not be null");
        checkArgument(quantity != null, "quantity can not be null");
        try {
            skuDao.changeStock(skuId, -quantity);
            itemDao.changeStock(itemId, -quantity);
        } catch (Exception e) {
            log.error("failed to decrement stock for sku(id={}),cause:{}", skuId, e);
            throw new ServiceException(e);
        }

    }

    /**
     * 增加库存
     *
     * @param skuId    sku id
     * @param itemId   item id
     * @param quantity sku id
     */
    @Override
    @Transactional
    public void incrementStock(Long skuId, Long itemId, Integer quantity) {
        checkArgument(skuId != null, "skuId can not be null");
        checkArgument(quantity != null, "quantity can not be null");
        try {
            skuDao.changeStock(skuId, quantity);
            itemDao.changeStock(itemId, quantity);
        } catch (Exception e) {
            log.error("failed to increment stock for sku(id={}),cause:{}", skuId, e);
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional
    public void bulkUpdateStatus(Long userId, Integer status, List<Long> ids) {
        checkArgument(userId != null, "userId can not be null");
        checkArgument(status != null, "status can not be null");
        if (ids.isEmpty()) {
            log.warn("ids is empty, nothing to be updated,return directly.");
            return;
        }
        try {
            itemDao.bulkUpdateStatus(userId, status, ids);
        } catch (Exception e) {
            log.error("failed to update status to {} for items {},cause:{}",
                    status, ids, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    @Override
    public Item findById(Long id) {
        checkArgument(id != null, "item id should be specified when find by id");
        try {
            return itemDao.findById(id);
        } catch (Exception e) {
            log.error("failed to find item whose id is {},cause:{}", id, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    @Override
    public ItemDetail findDetailBy(Long itemId) {
        checkArgument(itemId != null, "item id can not be null");
        try {
            return itemDetailDao.findByItemId(itemId);
        } catch (Exception e) {
            log.error("failed to find itemDetail for item(id={}),cause:{}", itemId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, Object> findWithDetailsById(Long itemId) {
        Item item = findById(itemId);
        if (item == null) {
            log.debug("can not find item whose id = {}", itemId);
            return null;
        }

        List<Sku> skus = skuDao.findByItemId(itemId);
        //RichItem richItem;
        ItemDetail itemDetail = findDetailBy(itemId);
        if (itemDetail == null) {
            log.debug("can not find itemDetail whose item_id={}", itemId);
            return ImmutableMap.of("item", item,
                    "attributes", forrest.getRichAttributes(item.getSpuId()),
                    "skuGroup", new SkuGroup(skus).getAttributes().asMap(),
                    "skus", JsonMapper.nonEmptyMapper().toJson(skus));
        }
        return ImmutableMap.of("item", item,
                "detail", itemDetail,
                "attributes", forrest.getRichAttributes(item.getSpuId()),
                "skuGroup", new SkuGroup(skus).getAttributes().asMap(),
                "skus", JsonMapper.nonEmptyMapper().toJson(skus));


    }

    @Override
    @Transactional
    public void delete(Long userId, Long id) {
        checkArgument(id != null, "item id should be specified when deleted");
        try {
            Item item = itemDao.findById(id);
            if (item == null) {
                log.warn("can not find item whose id={},return directory", id);
                return;
            }
            if (!Objects.equal(userId, item.getUserId())) {
                throw new ServiceException("item.not.owner");
            }
            item.setStatus(Item.Status.DELETED.toNumber());
            itemDao.update(item);
            itemDetailDao.delete(id);
            skuDao.deleteByItemId(id);
        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, ServiceException.class);
            log.error("failed to delete item whose id={},cause:{}", id, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    @Override
    public Sku findSkuById(Long skuId) {
        return skuDao.findById(skuId);
    }

    /**
     * 只需spu属性即可
     *
     * @param itemId 商品id
     * @return spu属性列表
     */
    @Override
    public List<RichAttribute> attributesOf(Long itemId) {
        checkArgument(itemId != null, "itemId can not be null");
        Item item = itemDao.findById(itemId);
        if (item == null) {
            log.error("can not find item where id={}", itemId);
            throw new ServiceException("can not find item");
        }
        Long spuId = item.getSpuId();
        return forrest.getRichAttributes(spuId);
    }
}
