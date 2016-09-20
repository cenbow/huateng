/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.service;

import com.aixforce.category.model.Category;
import com.aixforce.category.model.CategoryTree;
import com.aixforce.category.service.CategoryHierarchy;
import com.aixforce.common.model.Paging;
import com.aixforce.item.dto.FacetSearchResult;
import com.aixforce.item.model.AttributeKey;
import com.aixforce.item.model.AttributeValue;
import com.aixforce.item.model.Item;
import com.aixforce.item.service.search.ItemSearchHelper;
import com.aixforce.search.ESClient;
import com.aixforce.search.Pair;
import com.aixforce.search.RawSearchResult;
import com.aixforce.search.SearchFacet;
import com.aixforce.user.base.BaseUser;
import com.google.common.base.*;
import com.google.common.collect.*;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.facet.FacetBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-10
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService {
    private final static Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

    private final static Splitter splitter = Splitter.on('_').trimResults().omitEmptyStrings();
    public static final String CAT_FACETS = "cat_facets";
    public static final String ATTR_FACETS = "attr_facets";
    public static final ImmutableList<Pair> rootCategory = ImmutableList.of(new Pair("所有分类", 0L));


    @Autowired
    private ESClient esClient;

    @Autowired
    private Forrest forrest;

    @Autowired(required = false)
    private CategoryHierarchy categoryHierarchy;


    /**
     * 店铺内搜索,用于管理店铺内商品
     * @param baseUser 系统注入的用户
     * @param pageNo 起始页码
     * @param size   返回条数
     * @param params 搜索参数
     * @return 搜索结果
     */
    @Override
    public Paging<Item> searchInShop(BaseUser baseUser, int pageNo, int size, Map<String, String> params) {
        if (params == null) {
            params = Maps.newHashMap();
        }
        params.put("userId", baseUser.getId().toString());
        return doSearch(pageNo, size, params);

    }

    private Paging<Item> doSearch(int pageNo, int size, Map<String, String> params) {
        pageNo = pageNo <= 0 ? 1 : pageNo;
        size = size <= 0 ? 20 : size;

        SearchRequestBuilder requestBuilder = esClient.searchRequestBuilder();
        QueryBuilder queryBuilder = ItemSearchHelper.composeQuery(params);
        requestBuilder.setQuery(queryBuilder);
        String sort = params.get("sort");
        ItemSearchHelper.composeSort(requestBuilder,sort);

        requestBuilder.setFrom((pageNo - 1) * size).setSize(size);
        return esClient.search(Item.class,requestBuilder);
    }

    /**
     * 店铺内搜索,用于搜索店铺内已上架的商品
     * @param seller 店铺的卖家,系统注入
     * @param pageNo 起始页码
     * @param size   返回条数
     * @param params 搜索参数
     * @return 搜索结果
     */
    @Override
    public Paging<Item> searchOnShelfItemsInShop(BaseUser seller, int pageNo, int size, Map<String, String> params){
        if (params == null) {
            params = Maps.newHashMap();
        }
        params.put("userId", seller.getId().toString());
        params.put("status",String.valueOf(Item.Status.ON_SHELF.toNumber()));
        pageNo = pageNo <= 0 ? 1 : pageNo;
        size = size <= 0 ? 20 : size;

        SearchRequestBuilder requestBuilder = esClient.searchRequestBuilder();
        QueryBuilder queryBuilder = ItemSearchHelper.composeQuery(params);
        requestBuilder.setQuery(queryBuilder);

        String sort = params.get("sort");
        ItemSearchHelper.composeSort(requestBuilder,sort);
        requestBuilder.setFrom((pageNo - 1) * size).setSize(size);
        return esClient.search(Item.class,requestBuilder);
    }

    /**
     * 全网搜索
     * @param pageNo 起始页码
     * @param size   返回条数
     * @param params 搜索参数
     * @return 搜索结果
     */
    @Override
    public FacetSearchResult facetSearchItem(int pageNo, int size, Map<String, String> params) {
        pageNo = pageNo <= 0 ? 1 : pageNo;
        size = size <= 0 ? 20 : size;
        if (params == null) {
            params = Maps.newHashMap();
        }
        params.put("status",String.valueOf(Item.Status.ON_SHELF.toNumber())); //主搜只搜上架商品

        final Long categoryId = !Strings.isNullOrEmpty(params.get("cid")) ? Long.valueOf(params.get("cid")) : null;
        boolean categoryIdPresent = categoryId != null && !Objects.equal(categoryId, 0L);
        String pvids = params.get("pvids");//attribute value ids
        Set<Long> attributeIds = null;
        if (!Strings.isNullOrEmpty(pvids)) {
            Iterable<String> parts = splitter.split(pvids);
            attributeIds = Sets.newLinkedHashSetWithExpectedSize(Iterables.size(parts));
            for (String part : parts) {
                attributeIds.add(Long.valueOf(part));
            }
        }

        SearchRequestBuilder requestBuilder = esClient.searchRequestBuilder();

        QueryBuilder queryBuilder = ItemSearchHelper.composeQuery(params);
        requestBuilder.setQuery(queryBuilder);

        String sort = params.get("sort");
        ItemSearchHelper.composeSort(requestBuilder,sort);
        requestBuilder.setFrom((pageNo - 1) * size).setSize(size);

        FacetBuilder catFacetBuilder = FacetBuilders.termsFacet(CAT_FACETS).field("categoryIds").size(20);
        FacetBuilder attrFacetBuilder = FacetBuilders.termsFacet(ATTR_FACETS).field("attributeIds").size(100);

        requestBuilder.addFacet(catFacetBuilder).addFacet(attrFacetBuilder);

        RawSearchResult<Item> rawResult = esClient.facetSearch(Item.class,requestBuilder);

        FacetSearchResult result = from(rawResult);
        //refine category navigator if necessary
        refineCategoryNavigator(categoryId, categoryIdPresent, result);

        //refine bread crumbs
        refineBreadCrumbs(categoryId, categoryIdPresent, result);

        //refine property navigator
        refineAttributeNavigator(attributeIds, result);
        return result;
    }


    //if a category has been selected as a query filter, then it should not appears in category navigator
    private void refineCategoryNavigator(final Long categoryId, boolean categoryIdPresent, FacetSearchResult result) {
        if (categoryIdPresent) {
            if (isLeaf(categoryId)) {//if is leaf category,no need to show category navigator any more
                result.setCategories(Collections.<SearchFacet>emptyList());
            }
            Iterables.removeIf(result.getCategories(), new Predicate<SearchFacet>() { //remove selected category
                @Override
                public boolean apply(SearchFacet input) {
                    return Objects.equal(input.getId(), categoryId);
                }
            });
        }
    }

    //if user has selected a category or only one category matches the query ,then use that category as a bread crumbs,
    // else only virtual root shows on bread crumbs
    private void refineBreadCrumbs(Long categoryId, boolean categoryIdPresent, FacetSearchResult result) {
        //if user specified a category or only one category found,then add breadCrumbs
        if (categoryIdPresent || result.getCategories().size() == 1) {
            Long targetId = categoryId != null ? categoryId : result.getCategories().get(0).getId();
            List<Category> ancestors = categoryHierarchy.ancestorsOf(targetId);
            result.setBreadCrumbs(Lists.transform(ancestors, new Function<Category, Pair>() {
                @Override
                public Pair apply(Category category) {
                    return new Pair(category.getName(), category.getId());
                }
            }));
            //if the leaf category used as bread crumbs, then it should not appears in category navigator
            if (!categoryIdPresent) {
                result.setCategories(Collections.<SearchFacet>emptyList());
            }
        } else { //only add virtual root category
            result.setBreadCrumbs(rootCategory);
        }
    }


    //if a attribute has been selected as a query filter, then it should not appear in attribute navigator.
    //NOTE: we need to return user chosen properties
    private void refineAttributeNavigator(final Set<Long> attributeIds, FacetSearchResult result) {
        if (attributeIds != null) {
            final List<Pair> chosenProperties = Lists.newArrayListWithCapacity(attributeIds.size());
            Iterables.removeIf(result.getProperties(), new Predicate<FacetSearchResult.PropertyNavigator>() {
                @Override
                public boolean apply(FacetSearchResult.PropertyNavigator input) {
                    for (SearchFacet searchFacet : input.getValues()) {
                        if (attributeIds.contains(searchFacet.getId())) {
                            chosenProperties.add(new Pair(input.getKey() + ":" + searchFacet.getName(), searchFacet.getId()));
                            return true;
                        }
                    }
                    return false;
                }
            });
            result.setChosenProperties(chosenProperties);
        }
        result.setProperties(ImmutableList.copyOf(Iterables.limit(result.getProperties(), 5)));//return atMost 5 attribute group
    }



    private boolean isLeaf(Long categoryId) {
        CategoryTree subTree = categoryHierarchy.getSubTreeById(categoryId);
        return subTree != null && subTree.getChildren().isEmpty();
    }

    //accommodate raw search result to com.aixforce.web required format
    private FacetSearchResult from(RawSearchResult<Item> rawResult) {
        FacetSearchResult result = new FacetSearchResult();
        Facets facets = rawResult.getFacets();

        //handle category facets
        TermsFacet catFacet = facets.facet(CAT_FACETS);
        List<SearchFacet> leafCategoryFacet = processCategoryFacets(catFacet);

        //handle property facets
        TermsFacet attrFacet = facets.facet(ATTR_FACETS);
        List<FacetSearchResult.PropertyNavigator> propertyNavigators = processPropertyFacets(leafCategoryFacet, attrFacet);

        result.setTotal(rawResult.getTotal());
        result.setItems(rawResult.getData());
        result.setCategories(leafCategoryFacet);
        result.setProperties(propertyNavigators);
        return result;
    }

    //find leaf node and fill the category name
    private List<SearchFacet> processCategoryFacets(TermsFacet catFacet) {
        List<SearchFacet> leafCategoryFacet = Lists.newArrayList();
        for (TermsFacet.Entry entry : catFacet) {

            Long categoryId = Long.valueOf(entry.getTerm().string());
            CategoryTree subTree = categoryHierarchy.getSubTreeById(categoryId);
            if (subTree != null) {
                if (subTree.getChildren().isEmpty()) {
                    SearchFacet searchFacet = new SearchFacet(Long.valueOf(entry.getTerm().string()),
                            (long) entry.getCount());
                    searchFacet.setName(subTree.getCategory().getName());
                    leafCategoryFacet.add(searchFacet);
                } else {
                    log.debug("skip non-leaf category(id={},name={}) ", categoryId, subTree.getCategory().getName());
                }
            } else {
                log.error("failed to find category(id={})", categoryId);
            }
        }
        return leafCategoryFacet;
    }

    //find out attribute key and fill attrFacet name
    private List<FacetSearchResult.PropertyNavigator> processPropertyFacets(List<SearchFacet> leafCategoryFacet,
                                                                            TermsFacet propFacets) {
        Multimap<String, SearchFacet> allAttributes = LinkedHashMultimap.create();
        Map<Long, TermsFacet.Entry> byAttributeId = Maps.uniqueIndex(propFacets, new Function<TermsFacet.Entry, Long>() {
            @Override
            public Long apply(TermsFacet.Entry entry) {
                return Long.valueOf(entry.getTerm().string());
            }
        });
        for (SearchFacet categoryFacet : leafCategoryFacet) {
            Long categoryId = categoryFacet.getId();
            List<AttributeKey> attributeKeys = forrest.getAttributeKeys(categoryId);
            for (AttributeKey attributeKey : attributeKeys) {
                Long attributeKeyId = attributeKey.getId();
                List<AttributeValue> attributeValues = forrest.getAttributeValues(categoryId, attributeKeyId);
                for (AttributeValue attributeValue : attributeValues) {
                    Long attributeValueId = attributeValue.getId();
                    if (byAttributeId.containsKey(attributeValueId)) {
                        TermsFacet.Entry entry = byAttributeId.get(attributeValueId);
                        SearchFacet searchFacet = new SearchFacet(attributeValueId,
                                (long) entry.getCount());
                        searchFacet.setName(attributeValue.getValue());//fill name
                        allAttributes.put(attributeKey.getName(), searchFacet);
                    }
                }
            }
        }
        List<FacetSearchResult.PropertyNavigator> propertyNavigators = Lists.newArrayListWithCapacity(allAttributes.size());
        for (String key : allAttributes.keySet()) {
            FacetSearchResult.PropertyNavigator navigator = new FacetSearchResult.PropertyNavigator();
            navigator.setKey(key);
            navigator.setValues((Set<SearchFacet>) allAttributes.get(key));
            propertyNavigators.add(navigator);
        }
        return propertyNavigators;
    }
}
