/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.category.model;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-28
 */
public class CategoryTree implements Serializable{
    private static final long serialVersionUID = 825724355084076723L;
    private Category category;
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock read = readWriteLock.readLock();
    private final Lock write = readWriteLock.writeLock();

    private List<CategoryTree> children = Lists.newArrayList();
    private final int level;

    public CategoryTree(Category category, int level) {
        this.category = category;
        this.level = level;
    }

    public Category getCategory() {
        read.lock();
        try {
            return category;
        } finally {
            read.unlock();
        }
    }

    public void setCategory(Category category) {
        write.lock();
        try {
            this.category = category;
        } finally {
            write.unlock();
        }
    }

    public int getLevel() {
        return level;
    }

    public boolean addChild(CategoryTree categoryTree) {
        write.lock();
        try {
            children.remove(categoryTree);
            return children.add(categoryTree);
        } finally {
            write.unlock();
        }
    }

    public void addAll(Collection<CategoryTree> categories) {
        write.lock();
        try {
            children.removeAll(categories);
            children.addAll(categories);
        } finally {
            write.unlock();
        }
    }

    public void removeChild(CategoryTree categoryTree) {
        write.lock();
        try {
            children.remove(categoryTree);
        } finally {
            write.unlock();
        }
    }

    @Nonnull
    public synchronized List<CategoryTree> getChildren() {
        read.lock();
        try {
            return children;
        } finally {
            read.unlock();
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("category", category)
                .add("level", level).add("children", children).toString();
    }

    @Override
    public int hashCode() {
        return category.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CategoryTree)) {
            return false;
        }
        CategoryTree that = (CategoryTree) obj;
        return Objects.equal(category, that.category);
    }
}
