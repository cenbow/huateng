package com.huateng.p3.hub.accountcore.mapper;

import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.models.MonthTicketObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: dongpeiji
 * Date: 14-9-22
 * Time: 下午4:34
 */

public interface TMonthTicketMapper {

    /**
     * 获取所有月票列表
     * @return
     */
    public List<MonthTicketObject> getMonthTicketList();


    public MonthTicketObject selectMonthTicket();

}
