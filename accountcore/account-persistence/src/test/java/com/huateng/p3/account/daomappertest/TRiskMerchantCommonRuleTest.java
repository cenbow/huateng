package com.huateng.p3.account.daomappertest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.persistence.TRiskMerchantCommonRuleMapper;
import com.huateng.p3.account.persistence.models.TRiskMerchantCommonRule;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TRiskMerchantCommonRuleTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TRiskMerchantCommonRuleTest.class);

    @Autowired
    private TRiskMerchantCommonRuleMapper tRiskMerchantCommonRuleMapper;

    /*
    int deleteByPrimaryKey(Short ruleNo);

    int insert(TRiskMerchantCommonRule rule);

    int insertSelective(TRiskMerchantCommonRule rule);

    TRiskMerchantCommonRule selectByPrimaryKey(Short ruleNo);

    int updateByPrimaryKeySelective(TRiskMerchantCommonRule rule);

    int updateByPrimaryKey(TRiskMerchantCommonRule rule);
     */

    /**
     *测试完成
     */
    @Test
    public void testinsert() {
        try {
            TRiskMerchantCommonRule rule = new TRiskMerchantCommonRule();
            Date date = new Date();
            rule.setRecordNo(987654325L);
            rule.setlRecordNo(987654326L);
            rule.setBusinessType("1");
            rule.setRuleType("1");
            rule.setTerminalNo("000000001");
            rule.setArchivedFlag("2");
            rule.setArchiveTime(date);
            rule.setCheckFlag("0");
            rule.setCheckUid("chengmout2ao");
            rule.setCheckTime(date);
            rule.setCreateTime(date);
            rule.setCreateUid("createUid1");
            rule.setEffectiveDate(date);
            rule.setLastModifyTime(date);
            rule.setLastModifyUid("chengmouta1o");
            rule.setMerchantType("3");
            rule.setMerchantCode("1111111111111");
            long maxtxnamt = 11;
            rule.setMaxTxnAmt(maxtxnamt);
            long testshort = 22;
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setResvFld2("2");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("2");
            rule.setUseFlag("2");
            rule.setValidDate(date);
            rule.setMaxSumTxnAmt(23L);
            int i =  tRiskMerchantCommonRuleMapper.insert(rule);
            System.out.print("----------------------------插入TRiskMerchantCommonRule返回码:--:"+i);
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
            TRiskMerchantCommonRule rule = new TRiskMerchantCommonRule();
            Date date = new Date();
            rule.setRecordNo(987654326L);
            rule.setlRecordNo(987654327L);
            rule.setBusinessType("1");
            rule.setRuleType("1");
            rule.setTerminalNo("000000001");
            rule.setArchivedFlag("2");
            rule.setArchiveTime(date);
            rule.setCheckFlag("0");
            rule.setCheckUid("chengmout2ao");
            rule.setCheckTime(date);
            rule.setCreateTime(date);
            rule.setCreateUid("createUid1");
            rule.setEffectiveDate(date);
            rule.setLastModifyTime(date);
            rule.setLastModifyUid("chengmouta1o");
            rule.setMerchantType("3");
            rule.setMerchantCode("1111111111111");
            long maxtxnamt = 11;
            rule.setMaxTxnAmt(maxtxnamt);
            long testshort = 22;
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setResvFld2("2");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("2");
            rule.setUseFlag("2");
            rule.setValidDate(date);
            rule.setMaxSumTxnAmt(23L);
            int i =  tRiskMerchantCommonRuleMapper.insertSelective(rule);
            System.out.print("----------------------------插入TRiskMerchantCommonRule返回码:--:"+i);
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
            long ruleNo = 987654326L;
            TRiskMerchantCommonRule TRiskMerchantCommonRule = tRiskMerchantCommonRuleMapper.selectByPrimaryKey(ruleNo);
            System.out.print("------------------------------------------------------------"+TRiskMerchantCommonRule.getCheckUid()+TRiskMerchantCommonRule.getRemark());
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
            TRiskMerchantCommonRule rule = new TRiskMerchantCommonRule();
            Date date = new Date();
            rule.setRecordNo(987654326L);
            rule.setlRecordNo(987654327L);
            rule.setBusinessType("1");
            rule.setRuleType("1");
            rule.setTerminalNo("000000002");
            rule.setArchivedFlag("2");
            rule.setArchiveTime(date);
            rule.setCheckFlag("0");
            rule.setCheckUid("chengmoutao2");
            rule.setCheckTime(date);
            rule.setCreateTime(date);
            rule.setCreateUid("createUid2");
            rule.setEffectiveDate(date);
            rule.setLastModifyTime(date);
            rule.setLastModifyUid("chengmoutao2");
            rule.setMerchantType("3");
            rule.setMerchantCode("3333");
            long maxtxnamt = 11;
            rule.setMaxTxnAmt(maxtxnamt);
            long testshort = 22;
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setResvFld2("2");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("2");
            rule.setUseFlag("2");
            rule.setValidDate(date);
            rule.setMaxSumTxnAmt(23L);
            int i = tRiskMerchantCommonRuleMapper.updateByPrimaryKeySelective(rule);
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
            TRiskMerchantCommonRule rule = new TRiskMerchantCommonRule();
            Date date = new Date();
            rule.setRecordNo(987654326L);
            rule.setlRecordNo(987654327L);
            rule.setBusinessType("1");
            rule.setRuleType("1");
            rule.setTerminalNo("000000002");
            rule.setArchivedFlag("2");
            rule.setArchiveTime(date);
            rule.setCheckFlag("0");
            rule.setCheckUid("chengmoutao2");
            rule.setCheckTime(date);
            rule.setCreateTime(date);
            rule.setCreateUid("createUid1");
            rule.setEffectiveDate(date);
            rule.setLastModifyTime(date);
            rule.setLastModifyUid("chengmoutao1");
            rule.setMerchantType("3");
            rule.setMerchantCode("444");
            long maxtxnamt = 11;
            rule.setMaxTxnAmt(maxtxnamt);
            long testshort = 22;
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setResvFld2("2");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("2");
            rule.setUseFlag("2");
            rule.setValidDate(date);
            rule.setMaxSumTxnAmt(23L);
            int i=tRiskMerchantCommonRuleMapper.updateByPrimaryKey(rule);
            System.out.print("----------------------------testupdateByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试完成
     */
    @Test
    public void testfindMerchantRisk(){
        try{
        String merchantCode = "444";
        String txnChannel ="2";
        String businessType = "1";
        String terminalNo = "000000002";
        Map map = new HashMap();
        map.put("merchantCode",merchantCode);
        map.put("txnChannel",txnChannel);
            map.put("businessType",businessType);
            map.put("currentDate",new Date());
            map.put("terminalNo",terminalNo);

        TRiskMerchantCommonRule testRule = tRiskMerchantCommonRuleMapper.findMerchantRisk(map);
            System.out.print("getRecordNo==============================================:"+testRule);
        }catch (BizException e){
            e.printStackTrace();
        }
    }
    /**
     *测试完成
     */
    @Test
    public void testdeleteByPrimaryKey() {
        try {
            long ruleNo = 987654326L;
            int i = tRiskMerchantCommonRuleMapper.deleteByPrimaryKey(ruleNo);
            System.out.print("----------------------------testdeleteByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
