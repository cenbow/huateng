package com.aixforce.site.model.redis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/*
*Desc:组件原型，
* Author: dimzfw@gmail.com, jlchen
* Date: 2012-11-16
*/
public class Component implements Serializable {
    private static final long serialVersionUID = -8376120801944888172L;

    public enum CacheBy {
        None(0, "None"), Widget(1, "Widget"), Component(2, "Component");
        private final int value;
        private final String display;

        private CacheBy(int value, String display) {
            this.value = value;
            this.display = display;
        }

        public static CacheBy fromNumber(int value) {
            for (CacheBy type : CacheBy.values()) {
                if (type.value == value) {
                    return type;
                }
            }
            return null;
        }

        public int value() {
            return value;
        }

        @Override
        public String toString() {
            return display;
        }
    }

    public enum CodeType {
        HANDLEBARS(1, "Handlebars");
        private final Integer value;
        private final String display;

        private CodeType(Integer value, String display) {
            this.value = value;
            this.display = display;

        }

        public Integer toNumber() {
            return value;
        }

        public String toString() {
            return display;
        }

        public static CodeType fromNumber(int value) {
            for (CodeType type : CodeType.values()) {
                if (type.value == value) {
                    return type;
                }
            }
            return null;
        }
    }

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Long compCategoryId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String path;
    @Getter
    @Setter
    private String icon;
    @Getter
    @Setter
    private Integer rank;           //列表中的显示顺序
    @Getter
    @Setter
    private Integer cachedBy = 0;   //0：不缓存；1：WidgetId，2：ComponentId
    @Getter
    @Setter
    private Long cachedTime;        //模块的缓存时间 以分钟为单位，缓存处理逻辑再HandlebarEngine中
    @Getter
    @Setter
    private String api;
    @Getter
    @Setter
    private Date createdAt;
    @Getter
    @Setter
    private Date updatedAt;
    @JsonIgnore
    @Getter
    @Setter
    private Map<String, String> apis;

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Component)) {
            return false;
        }
        Component that = (Component) o;
        return Objects.equal(this.compCategoryId, that.compCategoryId)
                && Objects.equal(this.name, that.name) && Objects.equal(this.path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(compCategoryId, name, path);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("name", name).add("path", path)
                .add("icon", icon).add("icon", icon).add("rank", rank).add("service", api).omitNullValues().toString();
    }
}
