package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogAccountAlterMapper;
import com.huateng.p3.account.persistence.models.TLogAccountAlter;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TLogAccountAlterTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogAccountAlterTest.class);

    @Autowired
    private TLogAccountAlterMapper tLogAccountAlterMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogAccountAlter tLogAccountAlter = new TLogAccountAlter();
        Date date = new Date();
        String s = "a";
        Long l = 11L;
        int num = 1;
        tLogAccountAlter.setRecordNo(l);
        tLogAccountAlter.setTxnChannel(s);
        tLogAccountAlter.setTxnType(s);
        tLogAccountAlter.setTxnDscpt(s);
        tLogAccountAlter.setAcceptOrgCode(s);
        tLogAccountAlter.setAcceptTransSeqNo(s);
        tLogAccountAlter.setAcceptTransDate(s);
        tLogAccountAlter.setAcceptTransTime(s);
        tLogAccountAlter.setAccountType(s);
        tLogAccountAlter.setCustomerNo(s);
        tLogAccountAlter.setAccountNo(s);
        tLogAccountAlter.setTxnAmount(s);
        tLogAccountAlter.setTxnTime(date);
        tLogAccountAlter.setMobilePhone(s);
        tLogAccountAlter.setEmail(s);
        tLogAccountAlter.setRemark(s);
        tLogAccountAlter.setRemark2(s);
        tLogAccountAlter.setRsvdText1(s);
        tLogAccountAlter.setRsvdText2(s);
        tLogAccountAlter.setStatus(s);
        tLogAccountAlter.setFeeAmt(s);
        tLogAccountAlter.setRsvdText3(s);
        tLogAccountAlter.setRsvdText4(s);
        tLogAccountAlter.setRsvdText5(s);
        tLogAccountAlter.setRsvdText6(s);

        int i = tLogAccountAlterMapper.insert(tLogAccountAlter);
        System.out.print("----------------------------插入TLogAccountAlter返回码:--:" + i);
    }
}
