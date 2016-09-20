/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.search.endpoint;

import com.aixforce.common.model.Indexable;
import com.aixforce.search.ESClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-21
 */
@Component
public class SearchExecutor {
    public enum OP_TYPE{
        INDEX,
        DELETE
    }

    private final static Logger log = LoggerFactory.getLogger(SearchExecutor.class);
    public static final int DEFAULT_QUEUE_SIZE = 10000;
    private final ExecutorService executorService;
    private final ESClient esClient;

    @Autowired
    SearchExecutor(ESClient esClient) {
        this.esClient = esClient;
        this.executorService = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(DEFAULT_QUEUE_SIZE), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                SearchTask task = (SearchTask) r;
                log.error("task {} is rejected", task.getId());
            }
        });
    }

    public <T extends Indexable> void submit(String indexType,T indexable,OP_TYPE opType) {
        log.debug("asynchronous submit {} to search engine ", indexType + ":[id=" + indexable.getId() + "]");
        this.executorService.submit(new SearchTask<T>(indexType,indexable, opType));
    }

    public <T extends Indexable> void submit(T indexable,OP_TYPE opType) {
        log.debug("asynchronous submit {} to search engine ", indexable.getClass().getSimpleName() + ":[id=" + indexable.getId() + "]");
        this.executorService.submit(new SearchTask<T>(indexable, opType));
    }

    private class SearchTask<T extends Indexable> implements Runnable {
        private final String indexType;
        private final T indexable;
        private final OP_TYPE opType;

        private SearchTask(T indexable,OP_TYPE op_type){
            this(indexable.getClass().getSimpleName().toLowerCase(),indexable,op_type);
        }

        private SearchTask(String indexType, T indexable, OP_TYPE opType) {
            this.indexType = indexType.toLowerCase();
            this.indexable = indexable;
            this.opType = opType;
        }


        public String getId() {
            return indexType + ": [id=" + indexable.getId() + "]";
        }

        @Override
        public void run() {
            if (opType == OP_TYPE.INDEX) {
                log.debug("submit {} to search engine for index", indexable);
                esClient.index(indexType, indexable);
            } else if (opType == OP_TYPE.DELETE) {
                esClient.delete(indexType, indexable.getId());
            }
        }
    }

    @PreDestroy
    public void destroy(){
        log.info("shutdown search executor.....");
        this.executorService.shutdown();
       // this.esClient.destroy();
    }
}
