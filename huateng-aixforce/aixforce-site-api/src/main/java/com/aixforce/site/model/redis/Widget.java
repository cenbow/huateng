package com.aixforce.site.model.redis;

import com.aixforce.common.utils.Node;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
* Author: dimzfw, jlchen
* Date: 2012-11-15
*/
public class Widget implements Node, Serializable {
    private static final long serialVersionUID = 6222424880839098697L;

    public enum BelongType {
        HEADER(1, "头部"), FOOTER(2, "尾部"), PAGE(3, "页面"), SITE(4, "站点"), ITEM(5,"商品");
        private final Integer value;
        private final String display;

        private BelongType(Integer value, String display) {
            this.value = value;
            this.display = display;

        }

        public Integer toNumber() {
            return value;
        }

        public String toString() {
            return display;
        }

        public static BelongType fromNumber(Integer value) {
            switch (value) {
                case 1:
                    return HEADER;
                case 2:
                    return FOOTER;
                case 3:
                    return PAGE;
                case 4:
                    return SITE;
                case 5:
                    return ITEM;
            }
            return null;
        }
    }

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Long compId;
    @Getter
    @Setter
    private String jsonData;    //配置数据
    @Getter
    @Setter
    private Long parentId;      //绑定parent组件的ID

    @Getter
    @Setter
    private Long belongId;      //所属的ID ，由type决定是SiteInstance的头尾或Page
    @Getter
    @Setter
    private Integer belongType; //包括：siteInstance,page等
    @Getter
    @Setter
    private String style;       //top;left;width;height;opacity;border;z-index;background-color,image,position,repeat,box-radius,border-shadow

    @Getter
    @Setter
    private String mode;       //css class
    @Getter
    @Setter
    private String behavior;// behavior for widget

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Widget)) {
            return false;
        }
        Widget that = (Widget) o;
        return Objects.equal(this.compId, that.compId) && Objects.equal(this.parentId, that.parentId)
                && Objects.equal(this.belongType, that.belongType) && Objects.equal(this.belongId, that.belongId)
                && Objects.equal(this.style, that.style) && Objects.equal(this.behavior, that.behavior)
                && Objects.equal(this.jsonData, that.jsonData);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(compId, parentId, belongType, belongId, style, behavior, jsonData);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("compId", compId).add("parentId", parentId)
                .add("belongType", belongType).add("belongId", belongId).add("style", style).add("behavior", behavior)
                .add("jsonData", jsonData).omitNullValues().toString();
    }
}
