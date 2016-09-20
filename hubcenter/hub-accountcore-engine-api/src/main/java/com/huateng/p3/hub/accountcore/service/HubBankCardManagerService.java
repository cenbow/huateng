package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 上午10:55
 */
public interface HubBankCardManagerService {
    /**
     * 绑定银行卡
     *
     * @param productNo
     *            用户
     * @param bindingType
     *            绑定类型（1-转入用户绑定,2-转出用户绑定,3-转出转入用户同时绑定）
     * @param idType
     *            证件类型
     * @param idNo
     *            证件号码
     * @param realName
     *            证件名称
     * @param txnDscpt
     *            交易描述
     * @param acceptAreaCode
     *            省级代码
     * @param acceptCityCode
     *            城市代码
     * @param acceptOrgCode
     *            中心机构码
     * @param accountApanage
     *            用户地区码 传(可以null)
     * @param txnChannel
     *            交易渠道
     * @param feeFlag
     *            服务收费标志（1-不收费,2-现金,3-从账户扣取）
     * @param feeAmt
     *            收费金额(0)
     * @param acceptTxnSeqNo
     *            接受交易流水号
     * @param bankCardNo
     *            银行卡号
     * @param bankAccountNo
     *            银行户号
     * @param bankCardType
     *            银行卡类型
     * @param bankAccountName
     *            银行名称
     * @param bankCode
     *            银行号
     * @param openBankInfo
     *            开户信息
     * @param province
     *            开户省份
     * @param city
     *            开户城市
     * @param subBank
     *            开户支行
     * @param ownerFlag
     *            对公对私
     * @param cardBookFlag
     *            卡折区分
     * @param expireDate
     *            银行卡有效期
     * @param creditLogo
     *            是否信用卡
     * @param inputUid
     *            录入人(null)
     * @param inputTime
     *            录入时间
     * @param checkUid
     *            审核人(null)
     * @param checkTime
     *            审核时间(null)
     * @return
     */
    public Response<String> bindBankCard(TInfoBankcard bankCard,String bindingType, TInfoCustomer customer, ManagerLog managerLog)throws Exception;




    /**
     * 解绑银行卡
     *
     * @param productNo
     *            用户
     * @param bindingType
     *            绑定类型（1-转入用户绑定,2-转出用户绑定,3-转出转入用户同时绑定）
     * @param idType
     *            证件类型
     * @param idNo
     *            证件号码
     * @param realName
     *            证件名称
     * @param txnDscpt
     *            交易描述
     * @param acceptAreaCode
     *            省级代码
     * @param acceptCityCode
     *            城市代码
     * @param acceptOrgCode
     *            中心机构码
     * @param accountApanage
     *            用户地区码 传(可以null)
     * @param txnChannel
     *            交易渠道
     * @param feeFlag
     *            服务收费标志（1-不收费,2-现金,3-从账户扣取）
     * @param feeAmt
     *            收费金额(0)
     * @param acceptTxnSeqNo
     *            接受交易流水号
     * @param bankCardNo
     *            银行卡号
     * @param bankAccountNo
     *            银行户号
     * @param bankCardType
     *            银行卡类型
     * @param bankAccountName
     *            银行名称
     * @param bankCode
     *            银行号
     * @param openBankInfo
     *            开户信息
     * @param province
     *            开户省份
     * @param city
     *            开户城市
     * @param subBank
     *            开户支行
     * @param ownerFlag
     *            对公对私
     * @param cardBookFlag
     *            卡折区分
     * @param expireDate
     *            银行卡有效期
     * @param creditLogo
     *            是否信用卡
     * @param inputUid
     *            录入人(null)
     * @param inputTime
     *            录入时间
     * @param checkUid
     *            审核人(null)
     * @param checkTime
     *            审核时间(null)
     * @return
     */
    public Response<String> unbindBankCardByBankCardNo(TInfoBankcard bankCard, TInfoCustomer customer, ManagerLog managerLog)throws Exception;


}
