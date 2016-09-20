package com.aixforce.category.service;

import com.aixforce.category.dao.RedisCategoryDao;
import com.aixforce.category.model.Category;
import com.aixforce.category.model.CategoryTree;
import com.aixforce.exception.ServiceException;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-07-12
 */
@Component
public class CategoryHierarchyImpl implements CategoryHierarchy {
    private final Logger log = LoggerFactory.getLogger(CategoryHierarchyImpl.class);

    @Autowired
    private RedisCategoryDao categoryDao;

    private volatile CategoryTree categoryTree;

    /**
     * cache for category id to subTree
     */
    private LoadingCache<Long, Optional<CategoryTree>> categoryCache;

    @PostConstruct
    public void init(){
        categoryTree = buildCategoryTree();
        this.categoryCache = CacheBuilder.newBuilder().build(
                new CacheLoader<Long, Optional<CategoryTree>>() {
                    @Override
                    public Optional<CategoryTree> load(Long key) throws Exception {
                        return Optional.fromNullable(treeRootedAt(categoryTree, key));
                    }
                });
    }

    @Override
    public CategoryTree rebuildTree(){
        this.categoryTree = buildCategoryTree();
        this.categoryCache.invalidateAll();
        return this.categoryTree;
    }

    /*
    * @param id
    */
    @Override
    public void invalidateCategory(Long id) {
        categoryCache.invalidate(id);
    }

    @Override
    public Category findById(Long id){
        CategoryTree subTree = getSubTreeById(id);
        if(subTree!=null){
            return subTree.getCategory();
        }
        return categoryDao.findById(id);
    }

    /**
     * find subTree rooted at specified category id,include its self
     *
     * @param id category id
     * @return sub tree, maybe null
     */
    @Override
    @Nullable
    public CategoryTree getSubTreeById(Long id) {
        Optional<CategoryTree> subTree = categoryCache.getUnchecked(id);
        if (subTree.isPresent()) {
            return subTree.get();
        } else {
            log.warn("can not find subTree for category id={}", id);
            return null;
        }
    }

    /**
     * NOTE:the virtual root category included
     * find ancestor ids of the specified category id ,include itself
     *
     * @param id category id
     * @return ancestor ids
     */
    @Override
    @Nonnull
    public List<Category> ancestorsOf(Long id) {
        List<Category> ancestors = Lists.newArrayListWithCapacity(4);
        Long current = id;
        while (current > 1) {
            Category category = findById(current);
            if (category == null) {
                log.warn("can not find category with id = {}", current);
                throw new IllegalStateException("can not find category with id=" + current);
            }
            ancestors.add(category);
            current = category.getParentId();
        }
        //add virtual root
        ancestors.add(findById(0L));
        return Lists.reverse(ancestors);
    }

    @Nonnull
    CategoryTree buildCategoryTree() {
        Category virtualParent = new Category();
        virtualParent.setId(0L);
        virtualParent.setName("所有分类");

        CategoryTree virtualRoot = new CategoryTree(virtualParent, 0);
        try {
            recursiveBuildCategoryTree(virtualRoot);
            return virtualRoot;
        } catch (Exception e) {
            log.error("failed to build category tree, cause:", e);
            throw new ServiceException(e);
        }
    }

    private void recursiveBuildCategoryTree(CategoryTree root) {
        Long id = root.getCategory().getId();
        List<Category> categories = categoryDao.findChildrenById(id);
        for (Category category : categories) {
            CategoryTree subTree = new CategoryTree(category, root.getLevel() + 1);
            root.addChild(subTree);
            recursiveBuildCategoryTree(subTree);
        }
    }


    @Nullable
    private CategoryTree treeRootedAt(CategoryTree root, Long id) {
        if (Objects.equal(root.getCategory().getId(), id)) {
            return root;
        }
        List<CategoryTree> children = root.getChildren();
        for (CategoryTree child : children) {
            CategoryTree subTree = treeRootedAt(child, id);
            if (subTree != null) {
                return subTree;
            }
        }
        return null;
    }

}
