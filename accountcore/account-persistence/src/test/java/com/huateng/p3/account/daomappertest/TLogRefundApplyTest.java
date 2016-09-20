package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogRefundApplyMapper;
import com.huateng.p3.account.persistence.models.TLogRefundApply;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TLogRefundApplyTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogRefundApplyTest.class);

    @Autowired
    private TLogRefundApplyMapper tLogRefundApplyMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogRefundApply logRefundApply = new TLogRefundApply();
        Date date = new Date();
        String s = "a";
        logRefundApply.setRecordNo("987654332");
        logRefundApply.setRemark(s);
        logRefundApply.setAccountNo(s);
        logRefundApply.setApplyTime(date);
        logRefundApply.setRefundSeqNo("aa");

        int i = tLogRefundApplyMapper.insert(logRefundApply);
        System.out.print("----------------------------插入TLogRefundApply返回码:--:" + i);
    }

    @Test
    public void testselectByrefundapplySeq() {
        String refundSeqNo = "aa";
        TLogRefundApply logRefundApply = tLogRefundApplyMapper.selectByrefundapplySeq(refundSeqNo);
        System.out.print("----------------------------TLogRefundApply返回码:--:" + logRefundApply);
    }
}
