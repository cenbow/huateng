package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TParaMerchantRefundRuleMapper;
import com.huateng.p3.account.persistence.models.TParaMerchantRefundRule;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TParaMerchantRefundRuleTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TParaMerchantRefundRuleTest.class);

    @Autowired
    private TParaMerchantRefundRuleMapper tParaMerchantRefundRuleMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TParaMerchantRefundRule merchant = new TParaMerchantRefundRule();
        Date date = new Date();
        String s = "a";
        long recordNo = 987654332L;
        merchant.setRecordNo(recordNo);
        merchant.setMerchantCode("aaa");
        merchant.setCheckTime(date);
        merchant.setCheckUid(s);
        merchant.setArchiveTime(date);
        merchant.setArchivedFlag(s);
        merchant.setCreateTime(date);
        merchant.setCreateUid(s);
        merchant.setEffectiveDate(date);
        merchant.setLastModifyTime(date);
        merchant.setLastModifyUid(s);
        merchant.setRemark(s);
        merchant.setResvFld1(s);
        merchant.setResvFld2(s);
        merchant.setUseFlag(s);
        merchant.setValidDate(date);
        int i = tParaMerchantRefundRuleMapper.insert(merchant);
        System.out.print("----------------------------插入TParaMerchantRefundRule返回码:--:" + i);
    }

    @Test
    public void testfindMerchantRefundRule(){
        String merchantCode = "aaa";
        Date currentDate = new Date();
        tParaMerchantRefundRuleMapper.findMerchantRefundRule(merchantCode, currentDate);
    }

}
