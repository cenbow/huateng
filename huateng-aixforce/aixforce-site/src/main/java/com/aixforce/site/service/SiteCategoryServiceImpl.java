/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.exception.ServiceException;
import com.aixforce.site.dao.redis.RedisSiteCategoryDao;
import com.aixforce.site.model.redis.SiteCategory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/*
* Author: jlchen
* Date: 2012-11-28
*/
@Service
public class SiteCategoryServiceImpl implements SiteCategoryService{
    private final static Logger log = LoggerFactory.getLogger(SiteCategoryServiceImpl.class);
//    public static final int KEY = 1;

    @Autowired
    private RedisSiteCategoryDao siteCategoryDao;


    @Override
    public Map<String, List<SiteCategory>> group(Iterable<SiteCategory> siteCategories) {
        Map<String,List<SiteCategory>> result = Maps.newHashMap();
        for (SiteCategory siteCategory : siteCategories) {
            List<SiteCategory> entry = result.get(siteCategory.getBelong());
            if(entry == null){
                entry = Lists.newArrayList();
                result.put(siteCategory.getBelong(),entry);
            }
            entry.add(siteCategory);
        }
        return result;
    }

    @Override
    public SiteCategory findById(Long id){
        checkArgument(id!=null,"id can not be null");
        try{
            return siteCategoryDao.findById(id);
        }catch (Exception e){
            log.error("failed to find siteCategory where id ={},cause:{}",id,e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(SiteCategory siteCategory){
        checkArgument(siteCategory.getId()!=null,"id can not be null");
        try{
            siteCategoryDao.update(siteCategory);
            //cache.invalidate(KEY);
        }catch (Exception e){
            log.error("failed to update {},cause:{}",siteCategory,e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Long id){
        checkArgument(id!=null,"id can not be null");
        try{
            siteCategoryDao.delete(id);
            //cache.invalidate(KEY);
        }catch (Exception e){
            log.error("failed to delete siteCategory where id={}, cause:{}",id,e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Long create(SiteCategory siteCategory){
        checkArgument(siteCategory!=null,"param can not be null");
        try{
            siteCategoryDao.create(siteCategory);
            //cache.invalidate(KEY);
            return siteCategory.getId();
        }catch (Exception e){
            log.error("failed to create {},cause:{}",siteCategory,e);
            throw new ServiceException(e);
        }
    }


    @Override
    public List<SiteCategory> all(){
        try{
            return siteCategoryDao.loadAll();
        }catch (Exception e){
            log.error("failed to load all siteCategories,cause:{}",e);
            throw new ServiceException(e);
        }
    }
}
