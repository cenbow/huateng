package com.aixforce.site.model.redis;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/*
* Desc: 站点实例，一个站点可以拥有多个实例
* Author: Author: dimzfw@gmail.com, jlchen
* Date: 2012-11-15
*/
public class SiteInstance implements Serializable {
    private static final long serialVersionUID = 7735697080733511796L;

    public enum Status {
        DELETED(1, "删除"), NORMAL(2, "正常");
        private final Integer value;
        private final String display;

        private Status(Integer value, String display) {
            this.value = value;
            this.display = display;

        }

        public Integer toNumber() {
            return value;
        }

        public String toString() {
            return display;
        }

        public static Status fromNumber(Integer value) {
            switch (value) {
                case 1:
                    return DELETED;
                case 2:
                    return NORMAL;
            }
            return null;
        }
    }

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Long siteId;
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer status;
    @Getter
    @Setter
    private Long defaultPageId;
    @Getter
    @Setter
    private String style;

    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if(o == null ||!(o instanceof SiteInstance)){
            return false;
        }
        SiteInstance that = (SiteInstance)o;
        return Objects.equal(siteId,that.siteId)&&Objects.equal(this.name,that.name)
                &&Objects.equal(this.defaultPageId,that.defaultPageId)&&Objects.equal(this.style,that.style)
                &&Objects.equal(this.status,that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(siteId,name,defaultPageId,style,status);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id).add("siteId",siteId)
                .add("defaultPageId",defaultPageId).add("name",name).add("status",status).omitNullValues().toString();
    }
}
