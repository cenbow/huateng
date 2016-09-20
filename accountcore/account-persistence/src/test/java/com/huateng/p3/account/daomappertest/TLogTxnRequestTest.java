package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogTxnRequestMapper;
import com.huateng.p3.account.persistence.models.TLogTxnRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TLogTxnRequestTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogTxnRequestTest.class);

    @Autowired
    private TLogTxnRequestMapper tLogTxnRequestMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogTxnRequest txnRequest = new TLogTxnRequest();
        Date date = new Date();
        String s = "a";
        long recordNo = 987654332L;
        txnRequest.setTxnChannel(s);
        txnRequest.setResvFld1(s);
        txnRequest.setResvFld2(s);
        int i = tLogTxnRequestMapper.insert(txnRequest);
        System.out.print("----------------------------插入TLogTxnRequest返回码:--:" + i);
    }


}
