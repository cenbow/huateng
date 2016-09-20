package com.aixforce.category.service;

import com.aixforce.category.model.Category;
import com.aixforce.category.model.CategoryTree;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-07-12
 */
public interface CategoryHierarchy {
    CategoryTree rebuildTree();

    /*
        * @param id
        */
    void invalidateCategory(Long id);

    Category findById(Long id);

    /**
     * find subTree rooted at specified category id,include its self
     *
     * @param id category id
     * @return sub tree, maybe null
     */
    @Nullable
    CategoryTree getSubTreeById(Long id);

    /**
     * NOTE:the virtual root category included
     * find ancestor ids of the specified category id ,include itself
     *
     * @param id category id
     * @return ancestor ids
     */
    @Nonnull
    List<Category> ancestorsOf(Long id);
}
