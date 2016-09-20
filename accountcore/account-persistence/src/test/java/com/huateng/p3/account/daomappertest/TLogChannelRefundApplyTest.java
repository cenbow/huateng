package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogChannelRefundApplyMapper;
import com.huateng.p3.account.persistence.models.TLogChannelRefundApply;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TLogChannelRefundApplyTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogChannelRefundApplyTest.class);

    @Autowired
    private TLogChannelRefundApplyMapper tLogChannelRefundApplyMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogChannelRefundApply tLogChannelRefundApply = new TLogChannelRefundApply();
        Date date = new Date();
        String s = "a";
        Long l = 11L;
        tLogChannelRefundApply.setId(s);
        tLogChannelRefundApply.setThirdOrderNo(s);
        tLogChannelRefundApply.setCreateTime(s);
        tLogChannelRefundApply.setRefundeFile(s);
        tLogChannelRefundApply.setState(s);
        tLogChannelRefundApply.setRefundeFileTime(s);
        tLogChannelRefundApply.setTotalAmt(l);
        tLogChannelRefundApply.setTxnAmt(l);
        tLogChannelRefundApply.setNo(s);
        tLogChannelRefundApply.setTotalNo(s);
        tLogChannelRefundApply.setRefundeFlag(s);
        tLogChannelRefundApply.setTransDate(s);
        tLogChannelRefundApply.setResvFld1(s);
        tLogChannelRefundApply.setResvFld2(s);
        tLogChannelRefundApply.setResvFld3(s);
        tLogChannelRefundApply.setResvFld4(s);
        tLogChannelRefundApply.setResvFld5(s);
        tLogChannelRefundApply.setOldTxnSeqNo(s);
        tLogChannelRefundApply.setRefundSeqNo(s);
        tLogChannelRefundApply.setPayOrgCode(s);
        int i = tLogChannelRefundApplyMapper.insert(tLogChannelRefundApply);
        System.out.print("----------------------------插入TLogChannelRefundApply返回码:--:" + i);
    }

}
