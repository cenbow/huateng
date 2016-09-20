package com.huateng.p3.account.manager;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountManagerSpringTest;

import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.component.Response;

/**
 * Created by 夏海虎 on 2014/3/26.
 */
@Slf4j
public class OrgQueryServiceTest extends BaseAccountManagerSpringTest {

    @Autowired
    OrgQueryService orgQueryService;

    @Test
    public void checkOrgPayment() {
        ManagerLog managerLog = new ManagerLog();
        managerLog.setAcceptOrgCode("001999900000000");
        managerLog.setInnerTxnType("101231");
        Response<String> response = orgQueryService.checkOrgPayment(managerLog);
        log.info("checkOrgPayment response RESULT: {}", response.getResult());
    }
}
