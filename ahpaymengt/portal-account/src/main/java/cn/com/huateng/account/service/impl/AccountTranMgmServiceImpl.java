package cn.com.huateng.account.service.impl;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TInfoCustomer;
import cn.com.huateng.account.service.UserService;
import cn.com.huateng.account.util.ManagerLogCom;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.account.service.AccountTranMgmService;
import com.google.common.base.Strings;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;
import com.huateng.p3.account.risk.RiskCheckService;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubRiskCheckService;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 账户交易管理  交易限额设置
 *
 * @author lzw
 * @date 2013-08-07
 */
@Service
public class AccountTranMgmServiceImpl implements AccountTranMgmService {

    private static final Logger logger = LoggerFactory.getLogger(AccountTranMgmServiceImpl.class);
    @Autowired
    private UserService userService;

    @Autowired
    private HubRiskCheckService hubRiskCheckService;

    @Autowired
    ManagerLogCom managerLogCom;

    @Value("#{app.consumeTransMaxNum}")
    private long consumeTransMaxNum;

    @Value("#{app.consumeTransMinAmt}")
    private long consumeTransMinAmt;

    @Value("#{app.monthMaxConsCnt}")
    private long monthMaxConsCnt;

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat
            .forPattern("yyyyMMddHHmmss");


    /**
     * 查询个人交易风控规则
     *
     * @param commonUser 公共用户对象  必填
     * @return 如果操作成功, 则返回风控规则, 否则返回失败原因
     */
    @Override
    public Response<TRiskCustomerCommonRule> findRiskCustomerCommonRule(
            CommonUser commonUser) {
        // 必填判断
        Response<TRiskCustomerCommonRule> response = new Response<TRiskCustomerCommonRule>();
        TRiskCustomerCommonRule tRiskRule = new TRiskCustomerCommonRule();
        if (commonUser == null || commonUser.getId() == null) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_800001
                    .getErrordesc());
            return response;
        }

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);

        ManagerLog managerLog = managerLogCom.getManagerLog();
        try {
            response = hubRiskCheckService.queryRiskCustomerCommonRule(accountInfo, managerLog);
        } catch (Exception e) {
            logger.error("fail to findRiskCustomerCommonRule cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }
        if (response.isSuccess()) {
            logger.info("findRiskCustomerCommonRule success tRiskCustomerCommonRule:{}  ", response.toString());
            if(response.getResult()==null){
                tRiskRule.setConsumeTransMaxAmt(Long.parseLong("0"));
                tRiskRule.setConsumeTransMaxAmtSum(Long.parseLong("0"));
                tRiskRule.setMonthMaxConsAmt(Long.parseLong("0"));
                response.setResult(tRiskRule);
            }else{
            tRiskRule.setConsumeTransMaxAmt(response.getResult().getConsumeTransMaxAmt());
            tRiskRule.setConsumeTransMaxAmtSum(response.getResult().getConsumeTransMaxAmtSum());
            tRiskRule.setMonthMaxConsAmt(response.getResult().getMonthMaxConsAmt());
            tRiskRule.setAcceptChannel(response.getResult().getAcceptChannel());
            }
            return response;
        } else {
            logger.error("fail to findRiskCustomerCommonRule accountInfo:{} managerLog:{} cause by {}", new Object[]{accountInfo, managerLog, response.getErrorCode() + ":" + response.getErrorMsg()});

            return response;
        }




    }

    /**
     * 设置个人交易风控规则
     *
     * @param commonUser          公共用户对象  必填
     * @param consumeTxnMaxAmt    单笔消费最大金额   选填   默认为null
     * @param consumeTxnMaxAmtSum 日消费累计金额  选填  默认为null
     * @param monthMaxConsAmt     月消费累计金额  选填  默认为null
     */
    public Response<String> modifyRiskParamPage(CommonUser commonUser,String consumeTxnMaxAmt, String consumeTxnMaxAmtSum, String monthMaxConsAmt) {

        // 必填判断
        Response<String> response = new Response<String>();
        if (commonUser == null || Strings.isNullOrEmpty(commonUser.getUnifyId())) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_800001
                    .getErrordesc());
            return response;
        }
        //查询客户信息
        TInfoCustomer customer = userService.findUserByPK(commonUser.getId()
                .toString()).getResult();
        if (customer == null) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_700009
                    .getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_700009
                    .getErrordesc());
            return response;
        }
        TRiskCustomerCommonRule tRiskCustomerCommonRule = new TRiskCustomerCommonRule();
        tRiskCustomerCommonRule.setConsumeTransMaxAmt(Long.parseLong(consumeTxnMaxAmt));
        tRiskCustomerCommonRule.setConsumeTransMaxNum(consumeTransMaxNum);
        tRiskCustomerCommonRule.setConsumeTransMinAmt(consumeTransMinAmt);
        tRiskCustomerCommonRule.setConsumeTransMaxAmtSum(Long.parseLong(consumeTxnMaxAmtSum));

        tRiskCustomerCommonRule.setMonthMaxConsAmt(Long.parseLong(monthMaxConsAmt));
        tRiskCustomerCommonRule.setMonthMaxConsCnt(monthMaxConsCnt);

        tRiskCustomerCommonRule.setTransType("131010");

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);

        ManagerLog managerLog = managerLogCom.getManagerLog();
        // 判断成功调用 设置个人交易风控 规则
        try {
            response = hubRiskCheckService.setRiskCustomerCommonRule(accountInfo, managerLog, tRiskCustomerCommonRule);
        } catch (Exception e) {
            logger.error("fail to modifyRiskParamPage cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }
        if (response.isSuccess()) {
            logger.info("modifyRiskParamPage success tRiskCustomerCommonRule:{}  ", response.toString());
            return response;
        } else {
            logger.error("fail to modifyRiskParamPage  tRiskCustomerCommonRule:{} accountInfo:{} managerLog:{} cause by {}", new Object[]{tRiskCustomerCommonRule, accountInfo, managerLog, response.getErrorCode() + ":" + response.getErrorMsg()});
            return response;
        }



    }

}
