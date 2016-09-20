/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.category.service;

import com.aixforce.category.dao.RedisCategoryDao;
import com.aixforce.category.dto.RichCategory;
import com.aixforce.category.model.Category;
import com.aixforce.category.model.CategoryTree;
import com.aixforce.common.utils.BeanMapper;
import com.aixforce.exception.ServiceException;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.*;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-28
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    private final static Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private static final int MAX_ALLOWED_LEVEL = 4;

    @Autowired
    private CategoryHierarchy categoryHierarchy;

    @Autowired
    private RedisCategoryDao categoryDao;

    @Override
    public void create(Category category) {
        checkArgument(category.getId() == null, "not a new category");
        //checkArgument(category.getRank() != null, "should specify a rank");
        checkArgument(category.getParentId() != null,
                "parent id should not be null,if it's a root,please set parentId = 0");
        CategoryTree parent = categoryHierarchy.getSubTreeById(category.getParentId());
        checkState(parent != null, "can't find parent category with id=[%s] ", category.getParentId());
        assert parent != null;
        if (parent.getLevel() >= MAX_ALLOWED_LEVEL) {
            throw new ServiceException("exceed max allowed level");
        }
        try {

            for (CategoryTree node : parent.getChildren()) {
                if(Objects.equal(node.getCategory().getName(), category.getName())){
                    log.error("duplicated category name {} under the same paren",category.getName());
                    throw new IllegalArgumentException("duplicated category name under the same parent");
                }
            }
            category.setName(category.getName().trim());
            categoryDao.create(category);
            log.debug("succeed to create new category {}", category);

            //update main tree and then remove parent from cache

            parent.addChild(new CategoryTree(category, parent.getLevel()));
            categoryHierarchy.invalidateCategory(category.getParentId());
            log.debug("succeed to update category tree");
        } catch (Exception e) {
            log.error("failed to create category {}, cause: {}", category, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Category category) {
        checkArgument(category.getId() != null, "id can not be null when update category");
        try {
            categoryDao.update(category);

            //update main tree and refresh cache
            Category updated = categoryDao.findById(category.getId());
            CategoryTree subTree = categoryHierarchy.getSubTreeById(category.getId());
            assert subTree != null;
            subTree.setCategory(updated);
            categoryHierarchy.invalidateCategory(category.getId());

            //notify search engine
            //searchExecutor.submit(updated, SearchExecutor.OP_TYPE.INDEX);
        } catch (Exception e) {
            log.error("failed to update category to {}, cause: {}", category, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
        log.debug("succeed to update category to {}", category);
    }

    @Override
    @Nullable
    public Category findById(Long id) {
        checkNotNull(id, "id should be specified");
        return categoryHierarchy.findById(id);
    }

    /**
     * only leaf category and no SPUs under that leaf node can be deleted;
     *
     * @param id category id
     */
    @Override
    public void delete(Long id) {
        checkNotNull(id, "id should be specified");
        CategoryTree subTree = categoryHierarchy.getSubTreeById(id);
        checkState(subTree != null, "can not find category with id=[%s]", id);

        //check if it is a leaf node
        if (!subTree.getChildren().isEmpty()) {
            throw new ServiceException("only leaf node can be deleted");
        }
        try {
            //check if there is any SPUs under the leaf category
            /*List<Spu> spus = spuDao.findByCategoryId(id);
            if (!spus.isEmpty()) {
                throw new ServiceException("please delete the SPUs under this category");
            }*/

            categoryDao.delete(id);

            //refresh main tree and cache
            Long parentId = subTree.getCategory().getParentId();
            CategoryTree parentTree = categoryHierarchy.getSubTreeById(parentId);
            assert parentTree != null;
            parentTree.removeChild(subTree);
            categoryHierarchy.invalidateCategory(id);
            categoryHierarchy.invalidateCategory(parentId);

            //notify search engine
           // searchExecutor.submit(subTree.getCategory(), SearchExecutor.OP_TYPE.DELETE);
        } catch (Exception e) {
            log.error("failed to delete category with id={}, cause: {}", id, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
        log.debug("succeed to delete category with id {}", id);
    }

    /**
     * find children of the specified category id
     *
     * @param id category id
     * @return children,
     */
    @Override
    @Nonnull
    public List<RichCategory> childrenOf(Long id) {
        CategoryTree subTree = categoryHierarchy.getSubTreeById(id);
        if (subTree == null) {
            log.warn("can not find category with id={}", id);
            throw new IllegalStateException("can not find category with id=" + id);
        }
        return Lists.transform(subTree.getChildren(), new Function<CategoryTree, RichCategory>() {
            @Override
            public RichCategory apply(CategoryTree categoryTree) {
                RichCategory richCategory = BeanMapper.map(categoryTree.getCategory(), RichCategory.class);
                richCategory.setLevel(categoryTree.getLevel());
                richCategory.setHasChild(!categoryTree.getChildren().isEmpty());
                return richCategory;
            }
        });
    }

    /**
     * find ancestor ids of the specified category id ,include itself
     *
     * @param id category id
     * @return ancestor ids
     */
    @Nonnull
    @Override
    public Set<Long> ancestorsOf(Long id) {

        List<Long> ancestors = Lists.newArrayListWithCapacity(MAX_ALLOWED_LEVEL);
        Long current = id;
        while (current > 1) {
            Category category = categoryHierarchy.findById(current);
            if (category == null) {
                log.warn("can not find category with id = {}", current);
                throw new IllegalStateException("can not find category with id=" + current);
            }
            ancestors.add(current);
            current = category.getParentId();
        }
        return ImmutableSet.copyOf(Lists.reverse(ancestors));
    }
}