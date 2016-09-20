package com.aixforce.site.model.redis;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
* Author: jlchen
* Date: 2012-11-28
*/
public class SiteCategory implements Serializable {
    private static final long serialVersionUID = 4018128346268919431L;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer count;

    @Getter
    @Setter
    private String belong;

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null|| !(o instanceof SiteCategory)){
            return false;
        }
        SiteCategory that = (SiteCategory)o;
        return Objects.equal(this.name,that.name);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id).add("name",name).add("belong",belong)
                .add("count",count).omitNullValues().toString();
    }
}
