/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/30/12 11:48 AM
 */
public class StorageChain {
    private static Logger logger = LoggerFactory.getLogger(StorageChain.class);

    private StorageChain pre;
    private StorageChain next;
    private Storage current;
    /**
     * storable object's expired second time.
     * -1 means never expired
     */
    private long expiredSeconds = -1;

    protected Storable get(String key) throws Throwable {

        Storable storable = this.current.get(key);

        if (storable == null) {
            if (next != null) {
                logger.debug("Object [{}] not found in {},call next!", key, this.current.getClass().getSimpleName());
                storable = next.get(key);
            } else {
                return null;
            }
            if (storable != null) {
                this.current.put(storable);
            }
        } else {
            logger.debug("Object [{}] found in {}!", key, current.getClass().getSimpleName());

        }
        return storable;
    }

    protected boolean putForward(Storable storable) {

        if (this.pre != null) {
            this.pre.putForward(storable);
        } else {
            logger.debug("Object [{} with key {}] add to storage [{}]", storable.target, storable.key(), this.current.getClass().getSimpleName());
        }
        return this.current.put(storable);
    }

    protected void remove(String key) {

        if (this.pre != null) {
            this.pre.remove(key);
        } else {
            logger.debug("Object key {} remove from  storage [{}]", key, this.current.getClass().getSimpleName());
        }
        this.current.remove(key);
    }


    protected void setPre(StorageChain pre) {
        this.pre = pre;
    }


    protected void setNext(StorageChain next) {
        this.next = next;
    }


    protected Storage getCurrent() {
        return current;
    }

    protected void setCurrent(Storage current) {
        this.current = current;
    }

    protected long getExpiredSeconds() {
        return expiredSeconds;
    }

    protected void setExpiredSeconds(long expiredSeconds) {
        this.expiredSeconds = expiredSeconds;
    }
}