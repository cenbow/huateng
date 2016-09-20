package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.SecurityResultObject;
import com.huateng.p3.account.persistence.models.TDictCode;
import com.huateng.p3.account.persistence.models.TInfoAccountBankCard;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;

import java.util.List;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 上午10:57
 */
public interface HubCustomerQueryService {

    /**
     * 客户信息查询
     * * @param accountInfo
     *
     * @return
     */
    Response<CustomerResultObject> queryCustomerInfo(AccountInfo accountInfo)throws Exception;

    /**
     * 查询客户获取鉴权信息（提供给用户密码重置使用）
     *
     * @param accountInfo
     *
     * @return 反馈报文
     *         1、成功:CustomerResultObject
     *         2、失败:"错误代码(6):错误描述"
     */
    Response<CustomerResultObject> queryCheckCardHandingInfo(AccountInfo accountInfo)throws Exception;

    /**
     * 查询客户密保问题与密保问题内容
     * @param accountInfo
     * @return
     */
    Response<SecurityResultObject> queryCustomerSecurityInfo(AccountInfo accountInfo)throws Exception;

    /**
     * 查询黑名单
     * @param accountInfo
     * @param managerLog 身份证 银行卡号验证
     * @return
     */
    Response<String> queryRiskBlack(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;

    /**
     * 查询密保问题列表
     * @param managerLog
     * @return TDictCode中 codeValue为密保问题编号 codeDesc为密保问题内容
     */
    Response<List<TDictCode>> querySecurityQuestions(ManagerLog managerLog)throws Exception;

    /**
     * 查询国籍、职业等系统数据列表
     * @param managerLog
     * @param dictEng
     * @return TDictCode中 codeValue为编号 codeDesc为内容
     */
    Response<List<TDictCode>> querySecuritySysData(ManagerLog managerLog,String dictEng)throws Exception;

    /**
     * 手机绑定查询
     * @param customer
     * @return
     */
    Response<TInfoCustomer> queryMobilePhoneBinding(TInfoCustomer customer)throws Exception;

    /**
     * 银行卡绑定查询
     * @param tInfoBankcard
     * @return
     */
    Response<List<TInfoBankcard>> queryBankCardBinding(TInfoBankcard tInfoBankcard);

    /**
     * 查询客户绑定的银行卡
     * @param tInfoAccountBankCard
     * @return
     */
    public Response<List<TInfoAccountBankCard>> queryAccountBankCardBinding(TInfoAccountBankCard tInfoAccountBankCard);
}
