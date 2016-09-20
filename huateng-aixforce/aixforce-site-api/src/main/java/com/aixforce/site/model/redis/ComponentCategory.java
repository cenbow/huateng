/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.model.redis;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class ComponentCategory implements Serializable {

    private static final long serialVersionUID = -94031290224430163L;
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long parentId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String icon;

    @Getter
    @Setter
    private Integer rank;

    @Override
    public boolean equals(Object o) {
        if(o == null ||!(o instanceof ComponentCategory)){
            return false;
        }
        ComponentCategory that = (ComponentCategory) o;
        return Objects.equal(this.parentId,that.parentId)&&Objects.equal(this.name,that.name)
                &&Objects.equal(this.rank,that.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(parentId,name,rank);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id).add("name",name).add("icon",icon).add("rank",rank).toString();
    }
}
