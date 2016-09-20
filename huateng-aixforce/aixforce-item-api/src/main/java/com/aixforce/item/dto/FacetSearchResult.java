/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.dto;

import com.aixforce.item.model.Item;
import com.aixforce.search.Pair;
import com.aixforce.search.SearchFacet;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-10-04
 */
public class FacetSearchResult implements Serializable{
    private static final long serialVersionUID = -4823400869781857784L;

    private Long total;

    private List<Item> items;

    private List<PropertyNavigator> properties;

    private List<SearchFacet> categories;

    private List<Pair> breadCrumbs;

    private List<Pair> chosenProperties;

    public List<PropertyNavigator> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyNavigator> properties) {
        this.properties = properties;
    }

    public List<SearchFacet> getCategories() {
        return categories;
    }

    public void setCategories(List<SearchFacet> categories) {
        this.categories = categories;
    }

    public List<Pair> getBreadCrumbs() {
        return breadCrumbs;
    }

    public void setBreadCrumbs(List<Pair> breadCrumbs) {
        this.breadCrumbs = breadCrumbs;
    }

    public List<Pair> getChosenProperties() {
        return chosenProperties;
    }

    public void setChosenProperties(List<Pair> chosenProperties) {
        this.chosenProperties = chosenProperties;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class PropertyNavigator implements Serializable{
        private static final long serialVersionUID = -7690146242950003488L;
        private String key;
        private Set<SearchFacet> values;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Set<SearchFacet> getValues() {
            return values;
        }

        public void setValues(Set<SearchFacet> values) {
            this.values = values;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null||!(obj instanceof PropertyNavigator)){
                return false;
            }
            PropertyNavigator that = (PropertyNavigator)obj;
            return Objects.equal(key,that.getKey());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this).add("key",key).add("values",values).toString();
        }
    }

}
