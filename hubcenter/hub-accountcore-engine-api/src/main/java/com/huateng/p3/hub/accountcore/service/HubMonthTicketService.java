package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.models.MonthTicketObject;

import java.util.List;

/**
 * 月票购买
 * User: dongpeiji
 * Date: 14-9-22
 * Time: 下午2:08
 */
public interface HubMonthTicketService {

    /**
     * 月票发售
     * @param accountInfo  账户信息
     * @return  月票信息
     */
    public Response<MonthTicketObject> saleMonthTicket(AccountInfo accountInfo,ManagerLog managerLog,String monthTicketId,long number);

    /**
     * 月票列表
     * @param accountInfo 账户信息
     * @param managerLog 交易信息
     * @return  月票列表
     */
    public Response<List<MonthTicketObject>> getMonthTicket(AccountInfo accountInfo,ManagerLog managerLog);

}
