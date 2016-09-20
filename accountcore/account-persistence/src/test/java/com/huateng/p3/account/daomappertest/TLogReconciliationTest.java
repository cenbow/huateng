package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogReconciliationMapper;
import com.huateng.p3.account.persistence.models.TLogReconciliation;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TLogReconciliationTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogReconciliationTest.class);

    @Autowired
    private TLogReconciliationMapper tLogReconciliationMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogReconciliation logReconciliation = new TLogReconciliation();
        Date date = new Date();
        String s = "a";
        long recordNo = 987654332L;
        logReconciliation.setRecordNo(recordNo);
        logReconciliation.setRemark(s);
        logReconciliation.setApplyTime(date);

        int i = tLogReconciliationMapper.insert(logReconciliation);
        System.out.print("----------------------------插入TLogReconciliation返回码:--:" + i);
    }
}
