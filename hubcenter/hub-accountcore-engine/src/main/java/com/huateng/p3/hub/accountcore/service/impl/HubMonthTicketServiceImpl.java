package com.huateng.p3.hub.accountcore.service.impl;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.exception.SubmitBizException;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.inner.MonthTicketManager;
import com.huateng.p3.hub.accountcore.models.MonthTicketObject;
import com.huateng.p3.hub.accountcore.service.HubMonthTicketService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: dongpeiji
 * Date: 14-9-22
 * Time: 下午3:11
 */
@Service
public class HubMonthTicketServiceImpl  implements HubMonthTicketService {

    private static  final Logger log= LoggerFactory.getLogger(HubMonthTicketServiceImpl.class);

    @Autowired
    private MonthTicketManager monthTicketManager;

    @Override
    public Response<MonthTicketObject> saleMonthTicket(AccountInfo accountInfo,ManagerLog managerLog,String monthTicketId,long number){
        log.info("call saleMonthTicket,PARAMETER:{} {}", new Object[]{accountInfo, managerLog});
        Response<MonthTicketObject> result = new Response<MonthTicketObject>();
        try {
            MonthTicketObject monthTicketObject = monthTicketManager.getMonthTicket(accountInfo, managerLog);
            result.setResult(monthTicketObject);
            log.info("success to saleMonthTicket,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
            return result;
        } catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to saleMonthTicket,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
            return result;
        }catch (SubmitBizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to saleMonthTicket,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
            return result;
        } catch (Exception e) {
            log.error("failed to saleMonthTicket,PARAMETER:{} {}, CAUSE:{}", new Object[]{accountInfo, managerLog, Throwables.getStackTraceAsString(e)});
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return result;
        }
    }

    @Override
    public Response<List<MonthTicketObject>> getMonthTicket(AccountInfo accountInfo, ManagerLog managerLog) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
