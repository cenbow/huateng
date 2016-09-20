package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TParaAccountBalMapper;
import com.huateng.p3.account.persistence.models.TParaAccountBal;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TParaAccountBalTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TParaAccountBalTest.class);

    @Autowired
    private TParaAccountBalMapper tParaAccountBalMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TParaAccountBal accountBal = new TParaAccountBal();
        Date date = new Date();
        String s = "a";
        long recordNo = 987654332L;
        accountBal.setRecordNo(recordNo);
        accountBal.setCheckTime(date);
        accountBal.setCheckUid(s);
        accountBal.setArchiveTime(date);
        accountBal.setArchivedFlag(s);
        accountBal.setCreateTime(date);
        accountBal.setCreateUid(s);
        accountBal.setEffectiveDate(date);
        accountBal.setLastModifyTime(date);
        accountBal.setLastModifyUid(s);
        accountBal.setRemark(s);
        accountBal.setResvFld1(s);
        accountBal.setResvFld2(s);
        accountBal.setUseFlag(s);
        accountBal.setValidDate(date);
        int i = tParaAccountBalMapper.insert(accountBal);
        System.out.print("----------------------------插入TParaAccountBal返回码:--:" + i);
    }

    @Test
    public void testfindAccountRiskCfg(){
        String accountType = "1";
        String accountGrade = "1";
        Date currentDate = new Date();
        TParaAccountBal a = tParaAccountBalMapper.findAccountRiskCfg(accountType, accountGrade, currentDate);
        System.out.print("--adf-ds-fa-sf-a-dfasdf-s-f-查询返回结果-："+a);
    }

}
