package com.huateng.p3.hub.accountcore.inner;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.hub.accountcore.models.MonthTicketObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: dongpeiji
 * Date: 14-9-22
 * Time: 下午3:43
 */
@Component
public class MonthTicketManager {

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MonthTicketObject getMonthTicket(AccountInfo accountInfo,ManagerLog managerLog){
         return new MonthTicketObject();
    }
}
