package com.huateng.p3.account.daomappertest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TInfoOtapwdTransferMapper;
import com.huateng.p3.account.persistence.models.TInfoOtapwdTransfer;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TInfoOtapwdTransferTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TInfoOtapwdTransferTest.class);

    @Autowired
    private TInfoOtapwdTransferMapper tInfoOtapwdTransferMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TInfoOtapwdTransfer tInfoOtapwdTransfer = new TInfoOtapwdTransfer();
        String s = "a";
        tInfoOtapwdTransfer.setCardNo(s);
        tInfoOtapwdTransfer.setOcxPasswd(s);
        tInfoOtapwdTransfer.setOtaPasswd(s);
        tInfoOtapwdTransfer.setRsvdText1(s);
        tInfoOtapwdTransfer.setRsvdText2(s);
        int i = tInfoOtapwdTransferMapper.insert(tInfoOtapwdTransfer);
        System.out.println("----------------------------插入TInfoOtapwdTransfer返回码:--:" + i);
    }

    @Test
    public void testupdateOtaPasswdCardNoCloseStatus(){
        String accountNo = "a";
        int i = tInfoOtapwdTransferMapper.updateOtaPasswdCardNoCloseStatus(accountNo);
        System.out.println("-----testupdateOtaPasswdCardNoCloseStatus 返回状态:------------"+i);
    }
}
