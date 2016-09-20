package com.huateng.p3.account.daomappertest;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogPreauthApplyMapper;
import com.huateng.p3.account.persistence.models.TLogPreauthApply;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TLogPreauthApplyTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogPreauthApplyTest.class);

    @Autowired
    private TLogPreauthApplyMapper tLogPreauthApplyMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogPreauthApply logPreauthApply = new TLogPreauthApply();
        Date date = new Date();
        String s = "a";
        long batchNo = 987654332L;
        logPreauthApply.setBatchNo(batchNo);
        logPreauthApply.setAuthEndDate(date);
        logPreauthApply.setTransSeqType(s);
        logPreauthApply.setAcceptOrgCode(s);
        logPreauthApply.setAcceptTransSeqNo(s);
        logPreauthApply.setAcceptTransDate(s);
        logPreauthApply.setAcceptTransTime(s);
        logPreauthApply.setAccountNo(s);
        logPreauthApply.setPreAuthNo(s);
        logPreauthApply.setSupplyOrgCode(s);
        logPreauthApply.setAccountNo(s);
        logPreauthApply.setTxnSeqNo(s);
        int i = tLogPreauthApplyMapper.insert(logPreauthApply);
        System.out.print("----------------------------插入TLogPreauthApply返回码:--:" + i);
    }

    @Test
    public void testfindOldPreAuthApply() {
        String s = "a";
        String transSeqType = s;
        String acceptOrgCode = s;
        String acceptTransSeqNo = s;
        String acceptTransDate = s;
        String acceptTransTime = s;
        List<TLogPreauthApply> list = tLogPreauthApplyMapper.findOldPreAuthApply(transSeqType, acceptOrgCode, acceptTransSeqNo, acceptTransDate, acceptTransTime);
        System.out.print("----------------------------TLogPreauthApply返回码:--:" + list.size());
    }

    @Test
    public void testfindPreAuthApply() {
        String s = "a";
        String preAuthNo = s;
        String supplyOrgCode = s;
        String accountNo = s;
        String transSeqType = s;
        TLogPreauthApply logPreauthApply = tLogPreauthApplyMapper.findPreAuthApply(preAuthNo, supplyOrgCode, accountNo, transSeqType);
        System.out.print("----------------------------TLogPreauthApply返回码:--:" + logPreauthApply);
    }
}
