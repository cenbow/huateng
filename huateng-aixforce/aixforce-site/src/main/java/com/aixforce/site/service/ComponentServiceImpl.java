/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.common.utils.JsonMapper;
import com.aixforce.site.dao.redis.RedisComponentCategoryDao;
import com.aixforce.site.dao.redis.RedisComponentDao;
import com.aixforce.site.model.redis.Component;
import com.aixforce.site.model.redis.ComponentCategory;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/20/12 10:19 AM
 */
@Service("componentService")
public class ComponentServiceImpl implements ComponentService {
    private static Logger logger = LoggerFactory.getLogger(ComponentServiceImpl.class);

    @Autowired
    private RedisComponentDao componentDao;

    @Autowired
    private RedisComponentCategoryDao componentCategoryDao;

    @Override
    public Component findById(Long compId) {
        checkNotNull(compId);
        Component component = componentDao.findById(compId);
        buildApis(component);
        return component;
    }


    @Override
    public List<Component> findByCategoryId(Long categoryId) {
        checkNotNull(categoryId);
        return componentDao.findByCategoryId(categoryId);
    }

    @Override
    public Component findByPath(String path) {
        checkNotNull(path);
        Component component = componentDao.findByPath(path);
        buildApis(component);
        return component;
    }

    @Override
    public void create(Component comp) {
        checkArgument(comp.getId() == null);
        componentDao.create(comp);
        logger.debug("component created - [id:{},name:{}]", comp.getId(), comp.getName());
    }

    @Override
    public void delete(Long compId) {
        checkNotNull(compId);
        componentDao.delete(compId);
    }

    @Override
    public void update(Component comp) {
        checkNotNull(comp.getId());
        componentDao.update(comp);
    }

    /**
     * 列出所有的组件
     *
     * @return 查询结果
     */
    @Override
    public List<Component> all() {
        //currently,only category 0 and 2 has children
        List<ComponentCategory> categories =componentCategoryDao.findByParentId(0L);
        List<ComponentCategory> all = Lists.newArrayList(categories);
        all.addAll(componentCategoryDao.findByParentId(2L));

        List<Component> components = Lists.newArrayList();
        for (ComponentCategory category : all) {
            components.addAll(componentDao.findByCategoryId(category.getId()));
        }
        return components;
    }

    private void buildApis(Component component) {
        //build service
        if (component != null && !Strings.isNullOrEmpty(component.getApi())) {
            Map<String, String> _APIs = JsonMapper.nonDefaultMapper().fromJson(component.getApi(),
                    JsonMapper.nonDefaultMapper().createCollectionType(Map.class, String.class, String.class));
            if (_APIs == null) {
                _APIs = Maps.newHashMap();
                _APIs.put("default", component.getApi());  //"default" defined in aixforce-web's RenderConstants
            }
            component.setApi(null);
            component.setApis(_APIs);
        }
    }

}
