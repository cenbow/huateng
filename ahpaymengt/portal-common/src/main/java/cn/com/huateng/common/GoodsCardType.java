package cn.com.huateng.common;

/**
 * 商品 类别，目前适用 速通充值卡 标签  月票
 * 可以理解为 商品编号，目前我们就3种商品
 * 以后扩展，再改进此类
 */
public enum GoodsCardType {


    ReChargeCard("1001", "充值卡"),
    MonthTicket("1002", "月票"),
    LABEL("1003", "标签");

    String cardType;
    String desc;

    public String getCardType() {
        return cardType;
    }

    public String getDesc() {
        return desc;
    }

    GoodsCardType(String cardType, String desc) {
        this.cardType = cardType;
        this.desc = desc;
    }
}
