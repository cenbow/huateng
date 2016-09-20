package com.huateng.p3.account.manager;

import java.util.List;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.SecurityResultObject;
import com.huateng.p3.account.persistence.models.TDictCode;
import com.huateng.p3.account.persistence.models.TInfoAccountBankCard;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;


/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-4
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
public interface CustomerQueryService {


    /**
     * 客户信息查询
     * * @param accountInfo
     *
     * @return
     */
    Response<CustomerResultObject>queryCustomerInfo(AccountInfo accountInfo);

    /**
	 * 查询客户获取鉴权信息（提供给用户密码重置使用）
	 * 
	 * @param accountInfo
	 *            
	 * @return 反馈报文
	 *         1、成功:CustomerResultObject
	 *         2、失败:"错误代码(6):错误描述"
	 */
	Response<CustomerResultObject> queryCheckCardHandingInfo(AccountInfo accountInfo);
	
	/**
	 * 查询客户密保问题与密保问题内容
	 * @param accountInfo
	 * @return
	 */
	Response<SecurityResultObject> queryCustomerSecurityInfo(AccountInfo accountInfo);
	
	/**
	 * 查询黑名单
	 * @param accountInfo
	 * @param managerLog 身份证 银行卡号验证
	 * @return
	 */
	Response<String> queryRiskBlack(AccountInfo accountInfo, ManagerLog managerLog);
	
	 /**
     * 查询密保问题列表
     * @param managerLog
     * @return TDictCode中 codeValue为密保问题编号 codeDesc为密保问题内容 
     */
	Response<List<TDictCode>> querySecurityQuestions(ManagerLog managerLog);
	
	/**
	 * 手机绑定查询
	 * @param customer
	 * @return
	 */
	Response<TInfoCustomer> queryMobilePhoneBinding(TInfoCustomer customer);
	
	/**
	 * 银行卡绑定查询
	 * @param tInfoBankcard
	 * @return
	 */
	Response<List<TInfoBankcard>> queryBankCardBinding(TInfoBankcard tInfoBankcard);
	
	/**
	 * 查询客户绑定的银行卡
	 * @param TInfoAccountBankCard
	 * @return
	 */
	Response<List<TInfoAccountBankCard>> queryAccountBankCardBinding(TInfoAccountBankCard tInfoAccountBankCard);
	
	/**
     * 查询国籍、职业等系统数据列表 
     * @param managerLog
     * @param dictEng 查询类型
     * @return TDictCode中 codeValue为编号 codeDesc为内容 
     */
	Response<List<TDictCode>> querySecuritySysData(ManagerLog managerLog,String dictEng);


	
}