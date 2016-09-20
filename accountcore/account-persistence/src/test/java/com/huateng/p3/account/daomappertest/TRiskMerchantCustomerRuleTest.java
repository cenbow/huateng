package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TRiskMerchantCustomerRuleMapper;
import com.huateng.p3.account.persistence.models.TRiskMerchantCustomerRule;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TRiskMerchantCustomerRuleTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TRiskMerchantCustomerRuleTest.class);

    @Autowired
    private TRiskMerchantCustomerRuleMapper tRiskMerchantCustomerRuleMapper;

    /*
    int deleteByPrimaryKey(Short ruleNo);

    int insert(TRiskMerchantCustomerRule rule);

    int insertSelective(TRiskMerchantCustomerRule rule);

    TRiskMerchantCustomerRule selectByPrimaryKey(Short ruleNo);

    int updateByPrimaryKeySelective(TRiskMerchantCustomerRule rule);

    int updateByPrimaryKey(TRiskMerchantCustomerRule rule);
     */

    /**
     *测试完成
     */
    @Test
    public void testinsert() {
        try {
            TRiskMerchantCustomerRule rule = new TRiskMerchantCustomerRule();
            Date date = new Date();
            rule.setRecordNo(987654323L);
            rule.setlRecordNo(987654323L);
            rule.setAccountType("2");
            rule.setArchivedFlag("2");
            rule.setArchiveTime(date);
            rule.setCheckFlag("0");
            rule.setCheckUid("chengmoutao");
            rule.setCheckTime(date);
            rule.setCreateTime(date);
            rule.setCreateUid("createUid");
            Short dayTxnAmt = 100;
            Short daytxnnum = 10;
            rule.setDayTxnAmt(dayTxnAmt);
            rule.setDayTxnNum(daytxnnum);
            rule.setEffectiveDate(date);
            rule.setLastModifyTime(date);
            rule.setLastModifyUid("chengmoutao");
            rule.setMerchantCode("1111111111111");
            Short maxtxnamt = 11;
            rule.setMaxTxnAmt(maxtxnamt);
            Short testshort = 22;
            rule.setMonthTxnAmt(testshort);
            rule.setMonthTxnNum(testshort);
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setResvFld2("2");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("1");
            rule.setTxnType("1");
            rule.setUseFlag("1");
            rule.setUserGrade("1");
            rule.setValidDate(date);
            int i =  tRiskMerchantCustomerRuleMapper.insert(rule);
            System.out.print("----------------------------插入TRiskMerchantCustomerRule返回码:--:"+i);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     *测试完成
     */
    @Test
    public void testinsertSelective() {
        try {
            TRiskMerchantCustomerRule rule = new TRiskMerchantCustomerRule();
            Date date = new Date();
            rule.setRecordNo(987654324L);
            rule.setlRecordNo(987654324L);
            rule.setAccountType("2");
            rule.setArchivedFlag("2");
            rule.setArchiveTime(date);
            rule.setCheckFlag("0");
            rule.setCheckUid("chengmoutao");
            rule.setCheckTime(date);
            rule.setCreateTime(date);
            rule.setCreateUid("createUid");
            Short dayTxnAmt = 100;
            Short daytxnnum = 10;
            rule.setDayTxnAmt(dayTxnAmt);
            rule.setDayTxnNum(daytxnnum);
            rule.setEffectiveDate(date);
            rule.setLastModifyTime(date);
            rule.setLastModifyUid("chengmoutao");
            rule.setMerchantCode("1111111111111");
            Short maxtxnamt = 11;
            rule.setMaxTxnAmt(maxtxnamt);
            Short testshort = 22;
            rule.setMonthTxnAmt(testshort);
            rule.setMonthTxnNum(testshort);
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setResvFld2("2");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("1");
            rule.setTxnType("1");
            rule.setUseFlag("1");
            rule.setUserGrade("1");
            rule.setValidDate(date);
            int i =  tRiskMerchantCustomerRuleMapper.insertSelective(rule);
            System.out.print("----------------------------插入TRiskMerchantCustomerRule返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 已经测试完成
     */
    @Test
    public void testselectByPrimaryKey() {
        try {
            long ruleNo = 987654323L;
            TRiskMerchantCustomerRule tRiskMerchantCustomerRule = tRiskMerchantCustomerRuleMapper.selectByPrimaryKey(ruleNo);
            System.out.print("------------------------------------------------------------"+tRiskMerchantCustomerRule.getCheckUid()+tRiskMerchantCustomerRule.getRemark());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *测试完成
     */
    @Test
    public void testupdateByPrimaryKeySelective() {
        try {
            TRiskMerchantCustomerRule TRiskMerchantCustomerRule = new TRiskMerchantCustomerRule();
            TRiskMerchantCustomerRule rule = new TRiskMerchantCustomerRule();
            Date date = new Date();
            rule.setRecordNo(987654324L);
            rule.setlRecordNo(987654324L);
            rule.setAccountType("2");
            rule.setArchivedFlag("2");
            rule.setArchiveTime(date);
            rule.setCheckFlag("0");
            rule.setCheckUid("chengmout2ao");
            rule.setCheckTime(date);
            rule.setCreateTime(date);
            rule.setCreateUid("createUid1");
            Short dayTxnAmt = 100;
            Short daytxnnum = 10;
            rule.setDayTxnAmt(dayTxnAmt);
            rule.setDayTxnNum(daytxnnum);
            rule.setEffectiveDate(date);
            rule.setLastModifyTime(date);
            rule.setLastModifyUid("chengmouta1o");
            rule.setMerchantCode("1111111111111");
            Short maxtxnamt = 11;
            rule.setMaxTxnAmt(maxtxnamt);
            Short testshort = 22;
            rule.setMonthTxnAmt(testshort);
            rule.setMonthTxnNum(testshort);
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setResvFld2("2");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("2");
            rule.setTxnType("2");
            rule.setUseFlag("2");
            rule.setUserGrade("2");
            rule.setValidDate(date);
            int i = tRiskMerchantCustomerRuleMapper.updateByPrimaryKeySelective(rule);
            System.out.print("----------------------------updateByPrimaryKeySelective返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *测试完成
     */
    @Test
    public void testupdateByPrimaryKey() {
        try {
            TRiskMerchantCustomerRule rule = new TRiskMerchantCustomerRule();
            Date date = new Date();
            rule.setRecordNo(987654323L);
            rule.setlRecordNo(987654324L);
            rule.setAccountType("2");
            rule.setArchivedFlag("2");
            rule.setArchiveTime(date);
            rule.setCheckFlag("0");
            rule.setCheckUid("chengmout2ao");
            rule.setCheckTime(date);
            rule.setCreateTime(date);
            rule.setCreateUid("createUid1");
            Short dayTxnAmt = 100;
            Short daytxnnum = 10;
            rule.setDayTxnAmt(dayTxnAmt);
            rule.setDayTxnNum(daytxnnum);
            rule.setEffectiveDate(date);
            rule.setLastModifyTime(date);
            rule.setLastModifyUid("chengmouta1o");
            rule.setMerchantCode("1111111111111");
            Short maxtxnamt = 11;
            rule.setMaxTxnAmt(maxtxnamt);
            Short testshort = 22;
            rule.setMonthTxnAmt(testshort);
            rule.setMonthTxnNum(testshort);
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setResvFld2("2");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("2");
            rule.setTxnType("2");
            rule.setUseFlag("2");
            rule.setUserGrade("2");
            rule.setValidDate(date);
            int i=tRiskMerchantCustomerRuleMapper.updateByPrimaryKey(rule);
            System.out.print("----------------------------testupdateByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *测试完成
     */
    @Test
    public void testdeleteByPrimaryKey() {
        try {
            long ruleNo = 987654324L;
            int i = tRiskMerchantCustomerRuleMapper.deleteByPrimaryKey(ruleNo);
            System.out.print("----------------------------testdeleteByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
