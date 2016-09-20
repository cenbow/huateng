package com.aixforce.search;

import com.aixforce.common.model.Indexable;
import com.aixforce.common.model.Paging;
import com.aixforce.common.utils.JsonMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-06-03
 */
@Component
public class ESClient {
    private final static Logger log = LoggerFactory.getLogger(ESClient.class);

    private static final int DEFAULT_PAGE_SIZE = 20;

    private static final Joiner joiner = Joiner.on("");

    private static final TimeValue DEFAULT_TIME_OUT = TimeValue.timeValueSeconds(10);

    private final TransportClient client;

    private final String indexName;


    @Autowired
    public ESClient(@Value("#{app.searchHost}") final String host, @Value("#{app.searchPort}") final Integer port,
                         @Value("#{app.searchClusterName}") final String clusterName,@Value("#{app.indexName}")String indexName) {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", true).build();
        Iterable<String> hosts = Splitter.on(',').trimResults().omitEmptyStrings().split(host);
        if (Iterables.isEmpty(hosts)) {
            throw new IllegalArgumentException("no search hosts specified");
        }
        client = new TransportClient(settings);
        for (String h : hosts) {
             client.addTransportAddress(new InetSocketTransportAddress(h, port));
        }
        this.indexName = indexName;
    }

    public TransportClient getClient() {
        return client;
    }

    /**
     * single index
     *
     * @param indexType index name to be indexed
     * @param indexable object to be indexed
     * @param <T>       type of object
     */
    public <T extends Indexable> void index(String indexType, @Nonnull T indexable) {
        if (indexable.getId() == null) {
            return;
        }
        client.prepareIndex(indexName, indexType, String.valueOf(indexable.getId())).setTimeout(DEFAULT_TIME_OUT)
                .setSource(JsonMapper.nonEmptyMapper().toJson(indexable)).execute().actionGet();
        log.debug("index {} has been executed", indexable);
    }

    /**
     * single delete
     *
     * @param clazz class of object
     * @param id    id of object to delete
     * @param <T>   type of object
     */
    public <T extends Indexable> void delete(Class<T> clazz, @Nonnull Long id) {
        String indexType = clazz.getSimpleName().toLowerCase();
        delete(indexType, id);
    }

    /**
     * single delete
     *
     * @param indexType indexType
     * @param id        id of object to delete
     */
    public void delete(String indexType, @Nonnull Long id) {
        client.prepareDelete(indexName, indexType, String.valueOf(id)).execute().actionGet();
    }

    /**
     * bulk index object list
     *
     * @param indexables list of objects to be indexed
     * @param <T>        type of object
     */
    public <T extends Indexable> void index(@Nonnull Iterable<T> indexables) {
        if (Iterables.isEmpty(indexables)) {
            log.info("empty list,no need to index");
            return;
        }
        T first = Iterables.getFirst(indexables, null);
        String indexType = first.getClass().getSimpleName().toLowerCase();
        index(indexType, indexables);
    }

    /**
     * bulk index object list
     *
     * @param indexables list of objects to be indexed
     * @param <T>        type of object
     */
    public <T extends Indexable> void index(String indexType, @Nonnull Iterable<T> indexables) {
        if (Iterables.isEmpty(indexables)) {
            log.info("empty list,no need to index");
            return;
        }
        BulkRequestBuilder bulkRequestBuilder = buildBatchRequestForIndex(indexType, indexables);
        BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    log.error("index {} failed, cause: {}", bulkItemResponse.getId(), bulkItemResponse.getFailureMessage());
                }
            }
        } else {
            log.info("bulk index {} objects took {} milliseconds", Iterables.size(indexables), bulkResponse.getTookInMillis());
        }
    }

    /**
     * batch delete list of objects
     *
     * @param clazz class of objects to be deleted
     * @param ids   id list of objects to be deleted
     * @param <T>   type of object
     */
    public <T extends Indexable> void delete(Class<T> clazz, @Nonnull Iterable<Long> ids) {
        if (Iterables.isEmpty(ids)) {
            log.info("empty list,no need to delete");
            return;
        }
        String indexType = clazz.getSimpleName().toLowerCase();
        delete(indexType, ids);
    }

    /**
     * batch delete list of objects
     *
     * @param indexType index type
     * @param ids       id list of objects to be deleted
     */
    public void delete(String indexType, @Nonnull Iterable<Long> ids) {
        if (Iterables.isEmpty(ids)) {
            log.info("empty list,no need to delete");
            return;
        }
        BulkRequestBuilder bulkRequestBuilder = buildBatchRequestForDelete(indexType, ids);
        BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    log.error("delete {} failed,cause:{}", bulkItemResponse.getId(), bulkItemResponse.getFailureMessage());
                }
            }
        } else {
            log.info("bulk delete {} objects took {} milliseconds", Iterables.size(ids), bulkResponse.getTookInMillis());
        }
    }

    private <T extends Indexable> BulkRequestBuilder buildBatchRequestForIndex(String indexType, Iterable<T> indexables) {
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        for (T indexable : indexables) {
            if (indexable.getId() == null) {//从redis获取数据时,可能会出现这样的空数据,因此需要过滤
                continue;
            }
            bulkRequestBuilder.add(
                    client.prepareIndex(indexName, indexType, String.valueOf(indexable.getId()))
                            .setTimeout(DEFAULT_TIME_OUT).setSource(JsonMapper.nonEmptyMapper().toJson(indexable)));
        }
        return bulkRequestBuilder;
    }

    private BulkRequestBuilder buildBatchRequestForDelete(String indexType, Iterable<Long> ids) {
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        for (Long id : ids) {
            bulkRequestBuilder.add(
                    client.prepareDelete(indexName, indexType, String.valueOf(id)));
        }
        return bulkRequestBuilder;
    }

    public SearchRequestBuilder searchRequestBuilder(){
        return new SearchRequestBuilder(client).setIndices(indexName).setTimeout(DEFAULT_TIME_OUT);
    }

    public CountRequestBuilder countRequestBuilder(){
        return new CountRequestBuilder(client).setIndices(indexName);
    }

    public <T extends Indexable> Paging<T> search(Class<T> clazz, SearchRequestBuilder searchRequestBuilder){
        String indexType = clazz.getSimpleName().toLowerCase();
        searchRequestBuilder.setTypes(indexType);
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        SearchHits hits = response.getHits();
        List<T> data = deserialize(clazz, hits);
        return new Paging<T>(hits.getTotalHits(), data);
    }

    public <T extends Indexable> RawSearchResult<T> facetSearch(Class<T> clazz, SearchRequestBuilder searchRequestBuilder) {
        String indexType = clazz.getSimpleName().toLowerCase();
        searchRequestBuilder.setTypes(indexType);
        SearchResponse response = searchRequestBuilder.setTimeout(DEFAULT_TIME_OUT).execute().actionGet();
        SearchHits hits = response.getHits();
        List<T> data = deserialize(clazz, hits);
        return new RawSearchResult<T>(hits.getTotalHits(), response.getFacets(), data);
    }

    public <T extends Indexable> long count(Class<T> clazz, QueryBuilder q){
        return count(clazz.getSimpleName().toLowerCase(),q);
    }

    public long count(String indexType, QueryBuilder q){
        CountResponse response = client.prepareCount(indexName).setTypes(indexType).setQuery(q)
                .execute().actionGet();
        return Objects.firstNonNull(response.getCount(), 0L);
    }

    private <T extends Indexable> List<T> deserialize(Class<T> clazz, SearchHits hits) {
        List<T> data = Lists.newArrayListWithExpectedSize(DEFAULT_PAGE_SIZE);
        for (SearchHit hit : hits) {
            T entity = JsonMapper.nonEmptyMapper().fromJson(hit.getSourceAsString(), clazz);
            Map<String,HighlightField> highlightFields = hit.getHighlightFields();
            if (!highlightFields.isEmpty()) {
                HighlightField highlightField  = Iterables.get(highlightFields.values(), 0);
                String fieldName = highlightField.name();
                String value = joiner.join(highlightField.getFragments());
                setFieldValue(entity,fieldName,value);
            }
            data.add(entity);
        }
        return data;
    }


    private void setFieldValue(Object entity, String fieldName, Object fieldValue) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);

            field.setAccessible(true);
            field.set(entity, fieldValue);

        } catch (Exception e) {
            log.error("failed to set field value with reflection,class={},fieldName={},fieldValue={},cause:{}",
                    entity.getClass(),fieldName,fieldValue);
        }
    }

}
