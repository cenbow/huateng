/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.search;

import org.elasticsearch.search.facet.Facets;

import java.io.Serializable;
import java.util.List;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-10-05
 */
public class RawSearchResult<T>  implements Serializable {
    private static final long serialVersionUID = -2874083820658846561L;

    private final Long total;

    private final Facets facets;

    private final List<T> data;


    public RawSearchResult(Long total, Facets facets, List<T> data) {
        this.total = total;
        this.facets = facets;
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public List<T> getData() {
        return data;
    }

    public Facets getFacets() {
        return facets;
    }
}
