package cn.com.huateng.mall.model;

import com.aixforce.item.model.Sku;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-22
 */
public class ConfirmItem implements Serializable {
    private static final long serialVersionUID = -4700129419535579713L;

    @Getter
    @Setter
    private Sku sku;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String image;

    @Getter
    @Setter
    private Integer count;

    @Override
    public String toString() {
        return "ConfirmItem{" +
                "sku=" + sku +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", count=" + count +
                '}';
    }
}
