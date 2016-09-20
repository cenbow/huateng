package cn.com.huateng.mall.service;

import com.aixforce.annotations.ParamInfo;
import com.aixforce.common.model.Paging;
import com.aixforce.item.dto.FacetSearchResult;
import com.aixforce.item.model.Item;
import com.aixforce.item.model.ItemDetail;
import com.aixforce.item.service.ItemService;
import com.aixforce.item.service.SearchService;
import com.aixforce.search.Pair;
import com.aixforce.search.SearchFacet;
import com.aixforce.user.base.BaseUser;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * This proxy created for fix some serialize problems
 * <p/>
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-22
 */
@Service("searchServiceProxy")
public class SearchServiceProxyImpl implements SearchService {
    private static final Logger log = LoggerFactory.getLogger(SearchServiceProxyImpl.class);

    @Autowired
    private SearchService searchService;

    @Autowired
    private ItemService itemService;

    @Override
    public FacetSearchResult facetSearchItem(@ParamInfo("pageNo") int pageNo, @ParamInfo("size") int size, @ParamInfo("params") Map<String, String> params) {
        if (log.isDebugEnabled()) {
            log.debug("got params {}", params);
        }
        FacetSearchResult originResult = searchService.facetSearchItem(pageNo, size, params);
        List<Item> itemList = originResult.getItems();
        List<Item> newItemList = Lists.newArrayList();
        for (Item item : itemList) {
            ItemDetail itemDetail = itemService.findDetailBy(item.getId());
            item.setItemDetail(itemDetail);
            newItemList.add(item);
        }
        originResult.setItems(newItemList);
        originResult.setBreadCrumbs(new ArrayList<Pair>(originResult.getBreadCrumbs()));
        for (FacetSearchResult.PropertyNavigator prop : originResult.getProperties()) {
            prop.setValues(new HashSet<SearchFacet>(prop.getValues()));
        }
        return originResult;
    }

    @Override
    public Paging<Item> searchInShop(@ParamInfo("baseUser") BaseUser baseUser, @ParamInfo("pageNo") int pageNo,
                                     @ParamInfo("size") int size, @ParamInfo("params") Map<String, String> params) {
        if (log.isDebugEnabled()) {
            log.debug("got params {}", params);
        }
        params = Objects.firstNonNull(params, Maps.<String, String>newHashMap());
        // 默认查未上架的
        if (!params.containsKey("status")) {
            params.put("status", "0");
        }
        return searchService.searchInShop(baseUser, pageNo, size, params);
    }

    @Override
    public Paging<Item> searchOnShelfItemsInShop(@ParamInfo("seller") BaseUser seller, @ParamInfo("pageNo") int pageNo,
                                                 @ParamInfo("size") int size, @ParamInfo("params") Map<String, String> params) {
        if (log.isDebugEnabled()) {
            log.debug("got params {}", params);
        }
        return searchService.searchOnShelfItemsInShop(seller, pageNo, size, params);
    }
}
