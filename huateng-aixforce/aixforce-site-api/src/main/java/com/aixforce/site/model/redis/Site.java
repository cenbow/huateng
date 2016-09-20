package com.aixforce.site.model.redis;

import com.aixforce.common.model.Indexable;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.util.Date;

/*
* Author: dimzfw@gmail.com, jlchen
* Date: 2012-11-16
*/
public class Site implements Indexable {
    private static final long serialVersionUID = -7166930192352652007L;

    public static enum Status {
        DELETED(-1, "删除"), NORMAL(1, "正常"), DESIGN(0, "设计");
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
                case -1:
                    return DELETED;
                case 1:
                    return NORMAL;
                case 0:
                    return DESIGN;
            }
            return null;
        }
    }

    public static enum Type {
        OFFICIAL(111, "官方"), NORMAL(1, "一般"),SHOP(2,"店铺");
        private final Integer value;
        private final String display;

        private Type(Integer value, String display) {
            this.value = value;
            this.display = display;

        }

        public Integer toNumber() {
            return value;
        }

        public String toString() {
            return display;
        }

        public static Type fromNumber(Integer value) {
            switch (value) {
                case 111:
                    return OFFICIAL;
                case 1:
                    return NORMAL;
                case 2:
                    return SHOP;
            }
            return null;
        }
    }

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Length(max = 20,message = "站点名称不能超过20个字符")
    private String name;

    @Getter
    @Setter
    private String image;

    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    @Length(max=50,message = "外部域名不能超过50个字符")
    private String domain;    //外部域名

    @Getter
    @Setter
    @Length(max=100,message = "二级域名不能超过100个字符")
    private String subDomain; //内部域名，二级域名
    @Getter
    @Setter
    private String hrefBase;

    @Getter
    @Setter
    private String rootPath;

    @Getter
    @Setter
    @Min(0)
    private Integer price; //价格,以分为单位

    @Getter
    @Setter
    private Integer status;

    @Getter
    @Setter
    @Min(0)
    private Integer siteCategory;

    @Getter
    @Setter
    @Min(0)
    private Integer type;

    @Getter
    @Setter
    @Min(0)
    private Long defaultSiteInstanceId;

    @Getter
    @Setter
    @Min(0)
    private Long releaseSiteInstanceId;

    @Getter
    @Setter
    @Min(0)
    private Long forkFrom;

    @Getter
    @Setter
    @Min(0)
    private Long forkRoot;

    @Getter
    @Setter
    private Integer forkable;  //-1:不可fork,1:可以fork

    @Getter
    @Setter
    @Min(0)
    private Integer color;

    @Getter
    @Setter
    @Min(0)
    private Integer stars;  //单个站点的收藏

    @Getter
    @Setter
    @Min(0)
    private Integer forks;  //都和fork源相等


    @Getter
    @Setter
    @Length(max=100,message = "描述信息必须控制在100个字符内")
    private String description;

    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Site)) {
            return false;
        }
        Site that = (Site)o;
        return Objects.equal(this.name, that.name)&&Objects.equal(this.userId,that.userId)
                &&Objects.equal(this.domain,that.domain)&&Objects.equal(this.subDomain,that.subDomain)
                &&Objects.equal(this.defaultSiteInstanceId,that.defaultSiteInstanceId)
                &&Objects.equal(this.siteCategory,that.siteCategory)&&Objects.equal(this.type,that.type);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name,userId,domain,subDomain,defaultSiteInstanceId,siteCategory,type);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id).add("name",name).add("userId",userId)
                .add("domain",domain).add("subDomain",subDomain).add("defaultSiteInstanceId",defaultSiteInstanceId)
                .add("siteCategory",siteCategory).add("type",type).add("forkFrom",forkFrom)
                .add("stars",stars).add("forks",forks).omitNullValues().toString();
    }
}
