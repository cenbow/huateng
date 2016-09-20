package cn.com.huateng.common;

/**
 * Created with IntelliJ IDEA.
 * User: xueweijie
 * Date: 14-10-22
 * Time: 上午9:33
 * To change this template use File | Settings | File Templates.
 */
public class Constants {

    /********************************************** 账户类型 ***************************************/
    /** 资金子账户 */
    public static final String ACCOUNT_TYPE_FUND = "1";
    /** 脱机子账户 */
    public static final String ACCOUNT_TYPE_OFFLINE = "2";
    /** 代金券子账户 */
    public static final String ACCOUNT_TYPE_BOND = "3";
    /** 百事购卡注册类型是 A */
    public static final String REGISTER_BST = "A";

    public static final String REGISTER_TYCARD = "B";

    /** 账户ID类型 1-账户号；2-卡号；3-电信产品号（手机号、固话或宽带号） */
    public static final String ACCOUNT_ID_TYPE_MANAGER_ACCOUNT = "1";
    /** 账户ID类型 1-账户号；2-卡号；3-电信产品号（手机号、固话或宽带号） */
    public static final String ACCOUNT_ID_TYPE_MANAGER_CARD = "2";
    /** 账户ID类型 1-账户号；2-卡号；3-电信产品号（手机号、固话或宽带号） */
    public static final String ACCOUNT_ID_TYPE_MANAGER_PHONE = "3";


    /********************加密方式************************/
    /**
     * 加密方式  1：MD5加密
     */
    public static final String ENCODETYPE_1 = "1";
    /********************调理想网关************************/
    /**
     * 理想网关 交易类型代码0001支付
     */
    public static final String BUSICODE_0001 = "0001";
    /**
     * 理想网关    受理机构号
     */
    public static final String ACCORGCODE_SL1000000001 = "SL1000000001";
    /**
     * 理想网关 交易类型代码0001000001翼支付账户充值
     */
    public static final String BUSICODE_0001000001 = "0001000001";
    /**
     * 理想网关 业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_01 = "01";
    /**
     * 理想网关 业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_02 = "02";
    /**
     * 理想网关 业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_03 = "03";
    /**
     * 理想网关 业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_04 = "04";
    /**
     * 理想网关 业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_05 = "05";
    /**
     * 理想网关 业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_06 = "06";
    /**
     * 理想网关 业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_07 = "07";
    /**
     * 理想网关 业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_08 = "08";
    /**
     * 理想网关 业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_09 = "09";
    /**
     * 理想网关 业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_10 = "10";
    /**
     * 理想网关   业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_11 = "11";
    /**
     * 理想网关   业务标识01	电信类充值02	电信类缴费03	终端代理商资金归集04	空充代理商资金归集05	外部商户资金归集06	订房订票07	非实名订购（语音）08	一般网购09	虚拟点卡销售10	团购类11	招投标及大宗交易99	其他
     */
    public static final String PRODUCTID_99 = "99";

    /**
     * 资金账户网银充值121010
     */
    public static final String ACCWEBGETPAY_121010 = "121010";

    /********************订单总表业务************************/
    /**
     * 订单总表业务类型   1001:账户充值
     */
    public static final String PORTORDERBASE_1001 = "1001";
    /**
     * 订单总表业务类型   1002:账户支付
     */
    public static final String PORTORDERBASE_1002 = "1002";

    /**
     * 订单总表状态   1:订单生成
     */
    public static final String PORTORDERBASESTATUS_1 = "1";
    /**
     * 订单总表状态   2:支付成功
     */
    public static final String PORTORDERBASESTATUS_2 = "2";
    /**
     * 订单总表状态   3:支付失败
     */
    public static final String PORTORDERBASESTATUS_3 = "3";
    /**
     * 订单总表状态   4:销账成功
     */
    public static final String PORTORDERBASESTATUS_4 = "4";
    /**
     * 订单总表状态   5:销账失败
     */
    public static final String PORTORDERBASESTATUS_5 = "5";
    /**
     * 订单总表状态   6:充值成功
     */
    public static final String PORTORDERBASESTATUS_6 = "6";
    /**
     * 订单总表状态   7:充值失败
     */
    public static final String PORTORDERBASESTATUS_7 = "7";
    /********************网关订单表业务************************/
    /**
     * 网关订单表状态   1:订单生成
     */
    public static final String WEBGETPAY_STATUS_1 = "1";
    /**
     * 网关订单表状态   2:支付成功
     */
    public static final String WEBGETPAY_STATUS_2 = "2";
    /**
     * 网关订单表状态   3:支付失败
     */
    public static final String WEBGETPAY_STATUS_3 = "3";
    /**
     * 网关订单表状态   4:账户充值成功
     */
    public static final String WEBGETPAY_STATUS_4 = "4";
    /**
     * 网关订单表状态   5:账户充值失败
     */
    public static final String WEBGETPAY_STATUS_5 = "5";
    /**
     * 币种 RMB
     */
    public static final String RMB = "RMB";
    /*QQ 缴费单位代码 用于bill 销帐 billOrgId*/
    public static final String QQBillOrgCode = "440000440100A001";
    /*游戏 缴费单位代码 用于bill 销帐 billOrgId*/
    public static final String GAMEBillOrgCode = "150000150000A002";
    /*四川交罚 缴费单位代码 用于bill 销帐 billOrgId*/
    public static final String SICHUANTrafficBillOrgCode = "5100005100007001";
    /*至尊交罚 缴费单位代码 用于bill 销帐 billOrgId*/
    public static final String ZHIZUNrafficBillOrgCode = "9999009999017002";

    // 交易类型
	/* 用于帐务流水. */
    /******************************************* 交易类型 用于帐务流水 *****************************************/
    /** 帐务 交易类型 支付. */
    public static final String XTYPE_PAY = "00"; // 支付
    /** 帐务 交易类型 退货. */
    public static final String XTYPE_RETURN = "01"; // 退货
    /** 帐务 交易类型 充值. */
    public static final String XTYPE_CHARGE = "02"; // 充值
    /** 帐务 交易类型 提现申请. */
    public static final String XTYPE_PREDRAW = "181000"; // 提现申请

    /** 帐务 交易类型 提现申请. */
    public static final String XTYPE_PREDRAW_CANCEL = "181001"; // 提现申请撤销

    public static final String XTYPE_PREDRAW_END = "181010"; // 提现完成

    public static final String XTYPE_PREDRAW_FAIL = "181011"; // 提现失败

    /** 帐务 交易类型 提现. */
    public static final String XTYPE_DRAW1 = "03"; // 提现
    /** 帐务 交易类型 转帐. */
    public static final String XTYPE_TRANSFER = "04"; // 转帐
    /** 账务交易类型 转账 付款人payOwner */
    public static final String TRANSFER_PAYER = "OUT";
    /** 账务交易类型 转账 收款人payOwner */
    public static final String TRANSFER_PAYEE = "IN";
    /** 帐务 交易类型 余额查询. */
    public static final String XTYPE_BALANCEQUERY = "05"; // 余额查询
    /** 帐务 交易类型 预授权. */
    public static final String XTYPE_AUTH = "06"; // 预授权
    /** 帐务 交易类型 预授权取消. */
    public static final String XTYPE_AUTHCANCEL = "07"; // 预授权取消
    /** 帐务 交易类型 预授权完成. */
    public static final String XTYPE_AUTHENCASH = "08"; // 预授权完成
    /** 帐务 交易类型 预授权完成取消. */
    public static final String XTYPE_AUTHCASHCANEL = "09";
    /** 帐务 交易类型 返券支付. */
    public static final String XTYPE_REPAY = "10"; // 返券支付
    /** 帐务 交易类型 返券预授权. */
    public static final String XTYPE_REAUTH = "11"; // 返券预授权
    /** 帐务 交易类型 批量存款. */
    public static final String XTYPE_BATHDEPOSIT = "65"; // 批量存款
    /** 帐务 交易类型 发卡. */
    public static final String XTYPE_CARDSEND = "86"; // 发卡
    /** 帐务 交易类型 实物卡激活. */
    public static final String XTYPE_CARDACTIVE = "87"; // 实物卡激活
    /** 帐务 交易类型 抵用券发放. */
    public static final String XTYPE_PROVIDETICKETS = "47"; // 抵用券发放
    /** 帐务 交易类型 扣除抵用券. */
    public static final String XTYPE_DEDUCTTICKETS = "49"; // 扣除抵用券
    /** 帐务 交易类型 扣除商户手续费. */
    public static final String XTYPE_GETMERFEE = "36"; // 扣除商户手续费

	/* 用于交易日志流水. */
    /******************************************* 交易类型 用于交易日志流水 *****************************************/
    /** 流水 交易类型 账户注册. */
    public static final String XTYPE_VCARDREG = "41";
    /** 流水 交易类型 账户注销. */
    public static final String XTYPE_VCARDCANCEL = "42";
    /** 流水 交易类型 账户密码修改. */
    public static final String XTYPE_VCARDPWDMODIFY = "43";
    /** 流水 交易类型 账户密码重置. */
    public static final String XTYPE_VCARDPWDRESET = "44";
    /** 流水 交易类型 密码重置(控制台). */
    public static final String XTYPE_VCPWDRESET = "34"; // 密码重置(控制台)
    /** 流水 交易类型 转帐. */
    public static final String XTYPE_VCARDTRANSFER = "45";
    /** 流水 交易类型 充值. */
    public static final String XTYPE_VCARDCHARGE = "46";
    /** 流水 交易类型 实物卡注销. */
    public static final String XTYPE_CARDCANCEL = "81"; // 注销
    /** 流水 交易类型 实物卡密码修改. */
    public static final String XTYPE_CARDPWDMODIFY = "82"; // 密码修改
    /** 流水 交易类型 实物卡密码重置. */
    public static final String XTYPE_CARDPWDRESET = "83"; // 密码重置
    /** 流水 交易类型 实物卡挂失与解挂. */
    public static final String XTYPE_CARDLOST = "84"; // 挂失与解挂
    /** 流水 交易类型 实物卡绑定. */
    public static final String XTYPE_CARDBIND = "85"; // 绑定
    /** 流水 交易类型 争议交易. */
    public static final String XTYPE_TRADEDISPUTE = "48"; // 争议交易
    /** 流水 交易类型 日终. */
    public static final String XTYPE_DAYEND = "32"; // 日终
    /** 流水 交易类型 日终处理(重新发起). */
    public static final String XTYPE_DAYENDADJUST = "33";// 日终处理(重新发起)
    /** 流水 交易类型 批量退货. */
    public static final String XTYPE_BATCHREFUND = "64";// 批量退货
    /** 流水 交易类型 批量存款. */
    public static final String XTYPE_BATCHDEPOSIT = "65";// 批量存款
    /** 流水 交易类型 实物卡批量充值. */
    public static final String XTYPE_BATCHCARDCHARGE = "66";// 实物卡批量充值
    /** 流水 交易类型 密码保护. */
    public static final String XTYPE_CARDSAVE = "89"; // 密码保护
    /** 流水 交易类型 线下充值;. */
    public static final String XTYPE_OFFLINECHARGE = "87"; // 线下充值;
    /** 流水 交易类型 商城信息/权限修改. */
    public static final String XTYPE_MERINFOAUT = "88"; // 商城信息/权限修改
    /** 流水 交易类型 日终时间重置. */
    public static final String XTYPE_DAYENDTIMESET = "92"; // 日终时间重置
    /** 流水 交易类型 担保转账请求. */
    public static final String XTYPE_WARRANT_REQUIRE = "93";
    /** 流水 交易类型 担保转账完成. */
    public static final String XTYPE_WARRANT_ENSURE = "94";
    /** 流水 交易类型 担保转账取消. */
    public static final String XTYPE_WARRANT_CANCEL = "95";

    /********************** 加密机网关************************ 终端代码 ***************************************/
    /** 网关终端代码 */
    public static final String TERMINAL_NO = "99999999";
    /** 网关商户代码 */
    public static final String MERCHANT_CODE = "999999999999999";
    /** 网关交易渠道 2位字符，01-POS、02-网上、03-短信、04-IVR、05-OTA、06-WAP、07-营业厅 */
    public static final String TXN_CHANNEL = "02";
    /** 外部业务类型 */
    public static final String OUT_BUSSINESS_TYPE = "181010";

    /** 受理操作员编号 */
    public static final String ACCEPT_OPER_NO = "00";

    /** 服务收费标志 */
    public static final long FEEAMT = 0;

    /****************************
     * 申请提现************************* 老状态: 提现新接口 1-已申请 2-已撤销 3-正在处理 4-已提现 5-提现失败
     * 6-已拒绝 新状态: 1-已申请 2-已撤销 6-受理失败 3-已受理 8-银行已拒绝 9-银行拒绝，撤销失败 4-已提现 A-已提现，扣款失败
     * 5-提现失败，已入账 7-提现失败，无法入账
     **************************************************************/
    /*** 已申请 */
    public static final String CASH_STATUS_1 = "1";
    /*** 已撤销 */
    public static final String CASH_STATUS_2 = "2";
    /*** 已受理 */
    public static final String CASH_STATUS_3 = "3";
    /*** 已提现 */
    public static final String CASH_STATUS_4 = "4";
    /*** 提现失败，已入账 */
    public static final String CASH_STATUS_5 = "5";
    /*** 受理失败 */
    public static final String CASH_STATUS_6 = "6";
    /*** 提现失败，无法入账 */
    public static final String CASH_STATUS_7 = "7";
    /*** 银行已拒绝 */
    public static final String CASH_STATUS_8 = "8";
    /*** 银行拒绝，撤销失败 */
    public static final String CASH_STATUS_9 = "9";
    /*** 已提现，扣款失败 */
    public static final String CASH_STATUS_A = "A";

    /**************************** 提现新接口失败标志 1 申请状态 2失败 ********************/
    public static final String CASH_FAIL_FLAG_1 = "1";
    public static final String CASH_FAIL_FLAG_2 = "2";
}
