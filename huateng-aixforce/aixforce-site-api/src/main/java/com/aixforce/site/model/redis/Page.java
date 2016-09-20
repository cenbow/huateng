package com.aixforce.site.model.redis;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
*Desc:页面，这里的页面不包括头尾，头尾是公用部分，由SiteInstance关联
* Author: dimzfw@gmail.com, jlchen
* Date: 2012-11-15
*/
public class Page implements Serializable {
    private static final long serialVersionUID = -8050328865364181624L;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String path;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Long siteInstanceId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String keyword;

    @Getter
    @Setter
    private String onNav;

    @Getter
    @Setter
    private Integer rank;

    @Override
    public boolean equals(Object o) {
        if(o==null||!(o instanceof Page)){
            return false;
        }
        Page that = (Page)o;
        return Objects.equal(this.path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id).add("siteInstanceId",siteInstanceId).add("path",path).add("title",title)
                .add("description",description).add("keyword",keyword).add("name",name)
                .add("rank",rank).omitNullValues().toString();
    }
}

