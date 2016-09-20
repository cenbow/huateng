/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.category.service;

import com.aixforce.category.dto.RichCategory;
import com.aixforce.category.model.Category;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public interface CategoryService {

    void create(Category category);

    void update(Category category);

    @Nullable
    Category findById(Long id);

    /**
     * only leaf category and no SPUs under that leaf node can be deleted;
     * @param id category id
     */
    void delete(Long id);


    /**
     * find children of the specified category id
     * @param id  category id
     * @return children,
     */
    @Nonnull
    List<RichCategory> childrenOf(Long id);

    /**
     * find ancestor ids of the specified category id,include its self
     * @param id  category id
     * @return  ancestor ids
     */
    @Nonnull
    Set<Long> ancestorsOf(Long id);
}
