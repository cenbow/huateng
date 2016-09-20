package com.aixforce.user.base;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-07-18
 */
public class BaseUser implements Serializable {
    @Getter
    @Setter
    @Min(0)
    protected Long id;  //用户id

    @Getter
    @Setter
    @Length(min=4,max=16,message = "用户名长度需在4到16个字符长度内")
    protected String name;

    @Getter
    @Min(0)
    protected Integer type; //角色
    public void setType(Integer type) {
        this.type = type;
        this.enumType = TYPE.fromNumber(type);
    }
    private TYPE enumType;

    @Getter
    @Setter
    @Min(0)
    protected Long parent; //parent id

    public static enum TYPE{
        ADMIN(0,"管理员"),
        BUYER(1,"买家"),
        SELLER(2,"卖家"),
        SITE_OWNER(3,"站点拥有者"),
        DESIGNER(4,"设计师"),
        SUB_ACCOUNT(5,"子账号"),
        WHOLESALER(6,"批发商"),
        OTHER(7,"其他");

        private final int value;

        private final String display;

        private TYPE(int number, String display) {
            this.value = number;
            this.display = display;
        }

        public static TYPE fromName(String name){
            for(TYPE type : TYPE.values()) {
                if(Objects.equal(type.name(), name.toUpperCase())){
                    return type;
                }
            }
            return null;
        }

        public static TYPE fromNumber(Integer number){
            if(number == null){
                return null;
            }
            for(TYPE type : TYPE.values()) {
                if(Objects.equal(type.value,number)){
                    return type;
                }
            }
            return null;
        }

        public int toNumber(){
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }


    public BaseUser() {
    }

    public BaseUser(Long id, String name, Integer type) {
        this.id = id;
        this.name = name;
        this.setType(type);
    }

    public BaseUser(Long id, String name, TYPE type) {
        this.id = id;
        this.name = name;
        this.setTypeEnum(type);
    }

    /***********************************************
     * all method after this comment are alias
     ***********************************************/

    /**
     * alias of {@link #setParent(Long)}
     */
    public void setParentId(Long parentId) {
        this.parent = parentId;
    }

    /**
     * alias of {@link #getParent()}
     */
    public Long getParentId() {
        return parent;
    }

    /**
     * alias of {@link #setName(String)}
     */
    public void setPrincipal(String principal) {
        this.name = principal;
    }

    /**
     * alias of {@link #getName()}
     */
    public String getPrincipal() {
        return name;
    }

    /***********************************************
     * all method before this comment are alias
     ***********************************************/

    public TYPE getTypeEnum() {
        return enumType;
    }

    public void setTypeEnum(TYPE type) {
        this.type = type.toNumber();
        this.enumType = type;
    }
}
