package cn.com.huateng.mall.service;

import cn.com.huateng.util.StringUtil;
import com.aixforce.annotations.ParamInfo;
import com.aixforce.common.model.Paging;
import com.aixforce.common.utils.JsonMapper;
import com.aixforce.item.dto.RichAttribute;
import com.aixforce.item.dto.SkuGroup;
import com.aixforce.item.model.Item;
import com.aixforce.item.model.ItemDetail;
import com.aixforce.item.model.Sku;
import com.aixforce.item.service.ItemService;
import com.aixforce.user.base.BaseUser;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.*;

/**
 * This proxy created for fix some serialize problems
 * <p/>
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-22
 */
@Service("itemServiceProxy")
public class ItemServiceProxyImpl implements ItemService {
    private final static Logger log = LoggerFactory.getLogger(ItemServiceProxyImpl.class);

    @Autowired
    private ItemService itemService;

    @Override
    public Map<String, Object> detail(Long itemId) {
        return itemService.detail(itemId);
    }

    @Override
    public void create(Item item, ItemDetail itemDetail, List<Sku> skus) {
        itemDetail.setDetail(itemDetail.getStrDetail().getBytes());
        itemService.create(item, itemDetail, skus);
    }

    @Override
    public void update(Item item, ItemDetail itemDetail, List<Sku> skus) {
        itemDetail.setDetail(itemDetail.getStrDetail().getBytes());
        itemService.update(item, itemDetail, skus);
    }

    @Override
    public void decrementStock(Long skuId, Long itemId, Integer quantity) {
        itemService.decrementStock(skuId, itemId, quantity);
    }

    @Override
    public void incrementStock(Long skuId, Long itemId, Integer quantity) {
        itemService.incrementStock(skuId, itemId, quantity);
    }

    @Override
    public void bulkUpdateStatus(Long userId, Integer status, List<Long> ids) {
        itemService.bulkUpdateStatus(userId, status, ids);
    }

    @Override
    public Item findById(@ParamInfo("id") Long id) {
        return itemService.findById(id);
    }

    @Override
    public List<Item> findByIds(@ParamInfo("ids") List<Long> ids) {
        return itemService.findByIds(ids);
    }

    @Override
    public void delete(Long userId, Long id) {
        itemService.delete(userId, id);
    }

    @Override
    public ItemDetail findDetailBy(@ParamInfo("itemId") Long itemId) {
        return itemService.findDetailBy(itemId);
    }

    @Override
    public Map<String, Object> findWithDetailsById(@ParamInfo("itemId") Long itemId) {
        Map<String, Object> originResult = itemService.findWithDetailsById(itemId);

        @SuppressWarnings("unchecked")
        Map<String, Collection<Map<String, String>>> originSkuGroup = (Map<String, Collection<Map<String, String>>>) originResult.get("skuGroup");
        Map<String, String> targetSkuGroup = new HashMap<String, String>();

        for (String key : originSkuGroup.keySet()) {
            Collection<Map<String, String>> collection = originSkuGroup.get(key);
            List<Map.Entry<String, String>> allCollection = new ArrayList<Map.Entry<String, String>>();
            for (Map<String, String> strMap : collection) {
                List<Map.Entry<String, String>> newCollection = new ArrayList<Map.Entry<String, String>>(strMap.entrySet());
                for (Map.Entry<String, String> entry : newCollection) {
                    allCollection.add(entry);
                }
            }
            Collections.sort(allCollection, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    Integer s = Integer.parseInt(o1.getValue());
                    Integer a = Integer.parseInt(o2.getValue());
                    return s.intValue() - a.intValue();
                }
            });
            targetSkuGroup.put(key, JsonMapper.nonEmptyMapper().toJson(allCollection));
        }
        Map<String, Object> targetResult = new HashMap<String, Object>(originResult);
        targetResult.put("skuGroup", targetSkuGroup);

        return targetResult;
    }

    @Override
    public Sku findSkuById(@ParamInfo("skuId") Long skuId) {
        return itemService.findSkuById(skuId);
    }

    @Override
    public List<RichAttribute> attributesOf(@ParamInfo("itemId") Long itemId) {
        return itemService.attributesOf(itemId);
    }

    @Override
    public Paging<Item> sellerItems(@ParamInfo("baseUser") BaseUser baseUser, @ParamInfo("pageNo") Integer pageNo,
                                    @ParamInfo("size") Integer size, @ParamInfo("params") Map<String, String> params) {
        if (log.isDebugEnabled()) {
            log.info("got params {}", params);
        }
        params = Objects.firstNonNull(params, Maps.<String, String>newHashMap());
        // 默认查未上架的
        if (!params.containsKey("status") || Objects.equal(params.get("status"), "0")) {
            params.put("status", "-1,0");
        }
        return itemService.sellerItems(baseUser, pageNo, size, params);
    }

    @Override
    public Paging<Map<String, Object>> applicationItems(@ParamInfo("baseUser") BaseUser baseUser, @ParamInfo("pageNo") Integer pageNo, @ParamInfo("size") Integer size, @ParamInfo("params") Map<String, String> params) {
        if (log.isDebugEnabled()) {
            log.info("got params {}", params);
        }
        params = Objects.firstNonNull(params, Maps.<String, String>newHashMap());
        // 默认查上架的
        if (!params.containsKey("status") || Objects.equal(params.get("status"), "0")) {
            params.put("status", "1");
        }
        try {
            Paging<Item> itemPaging = itemService.sellerItems(baseUser, pageNo, size, params);
            List<Map<String, Object>> mapList = Lists.newArrayList();
            List<Item> itemList = itemPaging.getData();
            for (Item item : itemList) {
                ItemDetail itemDetail = findDetailBy(item.getId());
                Map<String, Object> stringObjectMap = Maps.newHashMap();
                stringObjectMap.put("item", item);
                stringObjectMap.put("detail", itemDetail);
                mapList.add(stringObjectMap);
            }
            return new Paging<Map<String, Object>>(itemPaging.getTotal(), mapList);
        } catch (Exception e) {
            log.error("failed to query applicationItems,", e);
            return new Paging<Map<String, Object>>(0L, Collections.<Map<String, Object>>emptyList());
        }
    }

    @Override
    public void fullDump() {
        itemService.fullDump();
    }

    @Override
    public void deltaDump(Integer interval) {
        itemService.deltaDump(interval);
    }
}
