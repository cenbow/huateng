package com.huateng.p3.account.common.enummodel;

import com.google.common.base.Objects;

import lombok.Getter;

public enum TxnInnerType {
	
	//管理类
	TXN_TYPE_100070("100070", "客户号锁定"),
	TXN_TYPE_100080("100080", "客户号解锁"),
	TXN_TYPE_101010("101010", "资金帐户开户"),
	TXN_TYPE_101131("101131", "开通天翼账户"),
	TXN_TYPE_101132("101132", "关闭天翼账户"),
	TXN_TYPE_101139("101139", "开通添益宝理财"),
	TXN_TYPE_101138("101138", "关闭添益宝理财"),
	TXN_TYPE_102000("102000", "脱机账户开户"),
	TXN_TYPE_101230("101230", "解除支付密码错误锁"),
    TXN_TYPE_101001("101001", "资金帐户激活默认开户"),
	TXN_TYPE_101020("101020", "资金帐户销户"),
	TXN_TYPE_101120("101120", "资金账户关闭"),
	TXN_TYPE_101170("101170", "资金帐户卡绑定"),
	TXN_TYPE_101180("101180", "资金帐户卡解绑"),
	TXN_TYPE_101190("101190", "资金帐户卡挂失"),
	TXN_TYPE_101200("101200", "资金帐户卡解挂"),
    TXN_TYPE_102011("102011", "脱机账户激活默认开户"),
	TXN_TYPE_102110("102110", "脱机账户关闭"),
	TXN_TYPE_102030("102030", "脱机账户销户"),
	TXN_TYPE_102080("102080", "脱机账户锁定"),
	TXN_TYPE_102090("102090", "脱机账户解锁"),
    TXN_TYPE_103001("103001", "代金账户激活默认开户"),
	TXN_TYPE_103110("103110", "代金券账户关闭"),
	TXN_TYPE_103070("103070", "代金账户锁定"),
	TXN_TYPE_103080("103070", "代金账户解锁"),
	TXN_TYPE_10A020("10A020", "百事购卡销户"),
    TXN_TYPE_104001("104001","积分账户开户"),
	TXN_TYPE_10B020("10B020", "天翼预付卡销户"),
	TXN_TYPE_103020("103020", "代金帐户销户"),
	TXN_TYPE_101140("101140", "资金帐户带余额销户(主动)"),
	TXN_TYPE_101150("101150", "资金帐户带余额销户(被动)"),
	TXN_TYPE_102130("102130", "脱机账户带余额销户(主动)"),
	TXN_TYPE_102140("102140", "脱机账户带余额销户(被动)"),
	TXN_TYPE_10A030("10A030", "百事购卡带余额销户"),
	TXN_TYPE_10B030("10B030", "天翼预付卡带余额销户"),
	TXN_TYPE_101050("101050", "资金帐户冻结"),
	TXN_TYPE_101060("101060", "资金帐户解冻"),
	TXN_TYPE_101030("101030", "资金帐户挂失"),
	TXN_TYPE_101040("101040", "资金帐户解挂"),
	TXN_TYPE_101070("101070", "资金帐户锁定"),
	TXN_TYPE_101080("101080", "资金帐户解锁"),
	TXN_TYPE_101090("101090", "修改支付密码"),
	TXN_TYPE_101110("101110", "重置支付密码"),
	TXN_TYPE_101210("101210", "支付密码鉴权"),
	TXN_TYPE_101111("101111", "重置登录密码"),
	TXN_TYPE_101112("101112", "修改登录密码"),
	TXN_TYPE_100100("100100", "客户帐户查询"),
	TXN_TYPE_100090("100090", "客户信息更新"),
	TXN_TYPE_10A160("10A160", "百事购卡延期"),
	TXN_TYPE_10B160("10B160", "天翼预付卡延期"),
	TXN_TYPE_10C010("10C010", "手机绑定"),
	TXN_TYPE_10C020("10C020", "手机绑定变更"),
	//交易类
	TXN_TYPE_111010("111010", "圈存(转出)"),
	TXN_TYPE_111012("111012", "圈存(转出)冲正"),
	TXN_TYPE_111020("111020", "圈提(转入)"),
	TXN_TYPE_111022("111022", "圈提(转入)冲正"),
	TXN_TYPE_112010("112010", "圈存(充值)"),
	TXN_TYPE_112012("112012", "圈存(充值)冲正"),
	TXN_TYPE_112020("112020", "圈提(转出)"),
	TXN_TYPE_112022("112022", "圈提(转出)冲正"),
    TXN_TYPE_141020("141020", "转出(转账)"),
    TXN_TYPE_141B20("141B20", "农村金融转出(转账)"),
    TXN_TYPE_131B10("131B10", "农村金融资金账户代付(消费)"),
    TXN_TYPE_131100("131100", "号码支付(消费)"),
    TXN_TYPE_131160("131160", "农村金融提现消费"),
    TXN_TYPE_131180("131180", "快捷支付(消费)"),
    TXN_TYPE_131190("131190", "信用卡还款消费"),
    TXN_TYPE_121010("121010", "资金账户网银充值"),
    TXN_TYPE_121050("121050", "资金账户客户端网银充值"),
    TXN_TYPE_121060("121060", "电影票返利充值"),
    TXN_TYPE_121061("121061", "彩票业务返利充值"),
    TXN_TYPE_121062("121062", "话费充值业务返利充值"),
    TXN_TYPE_121063("121063", "游戏快充业务返利充值"),
    TXN_TYPE_121064("121064", "火车票业务返利充值"),
    TXN_TYPE_121065("121065", "水电煤业务返利充值"),
    TXN_TYPE_121066("121066", "购汽车票业务返利充值"),
    TXN_TYPE_121067("121067", "缴有线电视费业务返利充值"),
    TXN_TYPE_121068("121068", "Wifi时长卡业务返利充值"),
    TXN_TYPE_121069("121069", "缴交通罚款返利充值"),
    TXN_TYPE_121071("121071", "开户返利充值"),
    TXN_TYPE_121072("121072", "翼支付首次消费返利充值"),
    TXN_TYPE_121073("121073", "翼支付账户充值返利充值"),
    TXN_TYPE_121074("121074", "鼎一保险活动返利充值"),
    TXN_TYPE_121075("121075", "3G流量卡的返利充值"),
    TXN_TYPE_121090("121090", "资金账户批量充值"),  
    TXN_TYPE_121011("121011", "可提现充值"),
    TXN_TYPE_121013("121013", "可提现批量充值"),
    TXN_TYPE_121020("121020", "资金账户省平台充值"),
    TXN_TYPE_121100("121100", "营销系统返利充值"),
    TXN_TYPE_121120("121120", "快捷可提现充值"),   
    TXN_TYPE_121160("121160", "当当网消费返利充值"),
    TXN_TYPE_121B10("121B10", "农村金融资金账户代扣(充值)"),
    TXN_TYPE_121200("121200", "预存款可提现批量充值"),
    TXN_TYPE_12M010("12M010", "小微商户充值"),
    TXN_TYPE_121130("121130", "天翼账户代扣充值"),
    TXN_TYPE_121190("121190", "添益宝利息充值"),
    TXN_TYPE_141010("141010", "转入(转账)"),
    TXN_TYPE_141B10("141B10", "农村金融转入(转账)"),
    TXN_TYPE_261010("261010", "退货"),	
    TXN_TYPE_261020("261020", "退货返还手续费"),	
    TXN_TYPE_270010("270010", "正调账"),	
    TXN_TYPE_270020("270020", "反调账"),	
    TXN_TYPE_280010("280010", "用户手续费"),	
    TXN_TYPE_280020("280020", "退还用户手续费"),
    TXN_TYPE_280030("280030", "退还用户手续费冲正"),
    TXN_TYPE_101231("101231", "黑名单查询"),
    TXN_TYPE_101B70("101B70","银行卡绑定"),
    TXN_TYPE_101B80("101B80","银行卡解绑"),
    CUSTOMER_REALNAME_REQUEST_TXN_TYPE("10S001","实名认证请求交易类型"),
    CUSTOMER_REALNAME_STATUS_QUERY_TXN_TYPE("10S002","账户实名状态查询交易类型"),
    CUSTOMER_REALNAME_STATUS_MODIFY_TXN_TYPE("10S003","修改客户实名状态交易类型"),
    CUSTOMER_REALNAME_ACCOUNT_QUERY_TXN_TYPE("100150","查询客户信息、账户信息、实名认证信息总和的交易类型"),
	XTYPE_PREDRAW("181000","提现申请"),
	XTYPE_PREDRAW_CANCEL("181001","提现申请撤销"),
	XTYPE_PREDRAW_END("181010","提现完成"),
	XTYPE_PREDRAW_FAIL("181011","提现失败"),
	XTYPE_TRANSFER_PREDRAW("181100","转账提现申请"),
	XTYPE_TRANSFER_PREDRAW_CANCEL("181101","转账提现申请撤销"),
	XTYPE_TRANSFER_PREDRAW_END("181110","转账提现完成"),
	XTYPE_TRANSFER_PREDRAW_FAIL("181111","转账提现失败"),
	XTYPE_NEW_PREDRAW("181200","新提现申请"),
	XTYPE_NEW_PREDRAW_CANCEL("181201","新提现申请撤销"),
	XTYPE_NEW_PREDRAW_END("181210","新提现完成"),
	XTYPE_NEW_PREDRAW_FAIL("181211","新提现失败"),
	TXN_TYPE_721010("721010", "企业预存款账户网银充值"),
	TXN_TYPE_761010("761010", "企业预存款帐退货"),
	TXN_TYPE_761020("761020", "退货返还手续费");
	

    @Getter
    private String txnInnerType;

    @Getter
    private String txnInnerTypeDesc;

    TxnInnerType(String txnInnerType, String txnInnerTypeDesc) {
    	this.txnInnerType = txnInnerType;
    	this.txnInnerTypeDesc = txnInnerTypeDesc;
    }
    
    
    public static String explain(String txnInnerType) {
        for (TxnInnerType txnType : TxnInnerType.values()) {
            if (Objects.equal(txnInnerType, txnType.txnInnerType)) {
                return txnType.txnInnerTypeDesc;
            }
        }
        return txnInnerType;
    }
}
