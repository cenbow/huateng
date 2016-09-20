package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogAccountManagementMapper;
import com.huateng.p3.account.persistence.models.TLogAccountManagement;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TLogAccountManagementTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogAccountManagementTest.class);

    @Autowired
    private TLogAccountManagementMapper logAccountManagementMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogAccountManagement logAccountManagement = new TLogAccountManagement();
        Date date = new Date();
        String s = "a";
        Long l = 11L;
        logAccountManagement.setTransSerialNo(s);
        logAccountManagement.setTransType(s);
        logAccountManagement.setTransMemo(s);
        logAccountManagement.setTransTime(date);
        logAccountManagement.setCustomerNo(s);
        logAccountManagement.setCityCode(s);
        logAccountManagement.setAcceptChannel(s);
        logAccountManagement.setResvFld1(s);
        logAccountManagement.setResvFld2(s);
        int i = logAccountManagementMapper.insert(logAccountManagement);
        System.out.print("----------------------------插入TLogAccountManagement返回码:--:" + i);
    }
}
