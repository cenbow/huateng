/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.service;

import com.aixforce.exception.ServiceException;
import com.aixforce.item.dao.redis.RedisSpuAttributeDao;
import com.aixforce.item.dao.redis.RedisSpuDao;
import com.aixforce.item.model.Spu;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-29
 */
@Service("spuService")
public class SpuServiceImpl implements SpuService {
    private final static Logger log = LoggerFactory.getLogger(SpuServiceImpl.class);

    @Autowired
    private RedisSpuDao spuDao;

    @Autowired
    private RedisSpuAttributeDao spuAttributeDao;

    @Autowired
    private Forrest forrest;

    @Override
    public void create(Spu spu) {
        checkArgument(spu.getId()==null,"id should be null when create new Spu");
        checkArgument(spu.getCategoryId()!=null,"category id should be specified when create new Spu");
        checkArgument(spu.getName()!=null,"name should be specified when create new Spu");
        try {
           // spu.setStatus(Spu.STATUS.ENABLED.toNumber());
            spuDao.create(spu);
            //invalidate cache
            forrest.invalidateSpusOfCategory(spu.getCategoryId());
        } catch (Exception e) {
            log.error("failed to create spu {},cause:{}",spu, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException("failed to create Spu",e);
        }
        log.debug("succeed to create spu {}",spu);
    }

    @Override
    public void update(Spu spu) {
        checkArgument(spu.getId()!=null,"id should be specified when update Spu");
        try {
            spuDao.update(spu);
            Spu updated = spuDao.findById(spu.getId());
            if(updated!=null){
                forrest.invalidateSpusOfCategory(spu.getCategoryId());
            }
        } catch (Exception e) {
            log.error("failed to update spu to {},cause:{}",spu, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException("failed to update Spu",e);
        }
        log.debug("succeed to update spu {}",spu);
    }

    @Override
    @Nullable
    public Spu findById(Long id) {
        checkArgument(id!=null,"id should be specified");
        Spu spu = spuDao.findById(id);
        if(spu == null){
            log.warn("can not find spu with id={}",id);
        }else{
            log.debug("succeed to find spu {}",spu);
        }
        return spu;
    }

    /**
     * when delete a spu, then its properties should also deleted
     * @param id spu id
     */
    @Override
    public void delete(Long id) {
        checkArgument(id!=null,"id should be specified");
        try{
            Spu spu = spuDao.findById(id);
            if(spu == null){
                log.warn("can not find spu where id={}",id);
                return;
            }
            spuDao.delete(id);
            spuAttributeDao.deleteBySpuId(id);
            log.debug("succeed to delete Spu with id = {}",id);

            //invalid cache
            forrest.invalidateSpuAttributeCache(id);
            forrest.invalidateSpusOfCategory(spu.getCategoryId());
            forrest.invalidateSkuKeyCache(id);
        }catch (Exception e){
            log.error("failed to delete Spu with id={}, cause: {}", id, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Spu> findByCategoryId(Long categoryId) {
        checkArgument(categoryId!=null,"categoryId can not be null");
        try{
            return forrest.getSpusOfCategory(categoryId);
        }catch (Exception e){
            log.error("failed to find Spus by categoryId: {}, cause: {}",categoryId,Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }
}
