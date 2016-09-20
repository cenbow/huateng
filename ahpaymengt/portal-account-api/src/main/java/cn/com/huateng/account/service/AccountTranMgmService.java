package cn.com.huateng.account.service;

import cn.com.huateng.CommonUser;
import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;
import com.huateng.p3.component.Response;

import java.util.List;

/**
 * 账户交易管理 。交易限额设置
 *
 * @author lzw
 * 
 */
public interface AccountTranMgmService {


    /**
     * 查询个人交易风控规则
     * @param commonUser 公共用户对象  必填
     * @return  如果操作成功,则返回风控规则list,否则返回失败原因
     */
    public Response<TRiskCustomerCommonRule> findRiskCustomerCommonRule(@ParamInfo("commonUser") CommonUser commonUser);
	
	/**
	 * 设置个人交易风控规则
	 * @param commonUser 公共用户对象  必填
	 * @param consumeTxnMaxAmt  单笔消费最大金额   选填   默认为null
	 * @param consumeTxnMaxAmtSum  日消费累计金额  选填  默认为null
	 * @param monthMaxConsAmt  月消费累计金额  选填  默认为null
	 * @return 如果操作成功,则返回SUCCESS,否则返回失败原因
	 */
	Response<String> modifyRiskParamPage(@ParamInfo("commonUser") CommonUser commonUser,
                                         @ParamInfo("consumeTxnMaxAmt") String consumeTxnMaxAmt, @ParamInfo("consumeTxnMaxAmtSum") String consumeTxnMaxAmtSum, @ParamInfo("monthMaxConsAmt") String monthMaxConsAmt);

}
