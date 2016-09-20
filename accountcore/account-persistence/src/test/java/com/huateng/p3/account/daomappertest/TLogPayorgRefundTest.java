package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogPayorgRefundMapper;
import com.huateng.p3.account.persistence.models.TLogPayorgRefund;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TLogPayorgRefundTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogPayorgRefundTest.class);

    @Autowired
    private TLogPayorgRefundMapper tLogPayorgRefundMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogPayorgRefund logPayorgRefund = new TLogPayorgRefund();
        Date date = new Date();
        String s = "a";
        long batchNo = 987654332L;
        logPayorgRefund.setBatchNo(batchNo);
        logPayorgRefund.setAcceptOrgCode(s);
        logPayorgRefund.setSupplyOrgCode(s);
        logPayorgRefund.setTxnSeqNo(s);
        int i = tLogPayorgRefundMapper.insert(logPayorgRefund);
        System.out.print("----------------------------插入TLogPayorgRefund返回码:--:" + i);
    }
}
