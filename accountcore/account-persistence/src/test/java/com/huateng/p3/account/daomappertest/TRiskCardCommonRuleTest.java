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
import com.huateng.p3.account.persistence.TRiskCardCommonRuleMapper;
import com.huateng.p3.account.persistence.models.TRiskCardCommonRule;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TRiskCardCommonRuleTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TRiskCardCommonRuleTest.class);

    @Autowired
    private TRiskCardCommonRuleMapper tRiskCardCommonRuleMapper;

    /*
    int deleteByPrimaryKey(Short ruleNo);

    int insert(TRiskCardCommonRule rule);

    int insertSelective(TRiskCardCommonRule rule);

    TRiskCardCommonRule selectByPrimaryKey(Short ruleNo);

    int updateByPrimaryKeySelective(TRiskCardCommonRule rule);

    int updateByPrimaryKey(TRiskCardCommonRule rule);
     */

    /**
     *测试完成
     */
    @Test
    public void testinsert() {
        try {
            TRiskCardCommonRule rule = new TRiskCardCommonRule();
            Date date = new Date();
            long lt1 = 22L;
            rule.setRecordNo(987654330L);
            rule.setlRecordNo(987654331L);
            rule.setConsumeTxnMaxNum(11L);
            rule.setConsumeTxnMaxAmt(22L);
            rule.setConsumeTxnMaxAmtSum(33L);
            rule.setConsumeTxnMinAmt(11L);
            rule.setMonthMaxConsAmt(11L);
            rule.setMonthMaxCashCnt(lt1);
            rule.setMonthMaxChgAmt(lt1);
            rule.setMonthMaxChgCnt(lt1);
            rule.setMonthMaxConsCnt(lt1);
            rule.setMonthMaxConsAmt(lt1);
            rule.setMonthMaxInAmt(lt1);
            rule.setMonthMaxInCnt(lt1);
            rule.setMonthMaxOutAmt(lt1);
            rule.setMonthMaxOutCnt(lt1);
            rule.setRuleType("1");
            rule.setWithdrawTxnMaxAmt(lt1);
            rule.setWithdrawTxnMaxAmtSum(lt1);
            rule.setWithdrawTxnMaxNum(lt1);
            rule.setWithdrawTxnMinAmt(lt1);
            rule.setTransferTxnMaxAmt(lt1);
            rule.setTransferTxnMaxNum(lt1);
            rule.setTransferTxnMaxAmtSum(lt1);
            rule.setChargeTxnMaxAmt(lt1);
            rule.setChargeTxnMaxAmtSum(lt1);
            rule.setChargeTxnMaxNum(lt1);
            rule.setChargeTxnMinAmt(lt1);
            rule.setRuleType("1");
            rule.setArchivedFlag("2");
            rule.setArchiveTime(date);
            rule.setCheckFlag("0");
            rule.setCheckUid("chengmoutao");
            rule.setCheckTime(date);
            rule.setCreateTime(date);
            rule.setCreateUid("createUid1");
            rule.setEffectiveDate(date);
            rule.setLastModifyTime(date);
            rule.setLastModifyUid("chengmouta1o");
            long maxtxnamt = 11;
            long testshort = 22;
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("2");
            rule.setUseFlag("2");
            rule.setValidDate(date);
            int i =  tRiskCardCommonRuleMapper.insert(rule);
            System.out.print("----------------------------插入TRiskCardCommonRule返回码:--:"+i);
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
            TRiskCardCommonRule rule = new TRiskCardCommonRule();
            Date date = new Date();
            long lt1 = 22L;
            rule.setRecordNo(987654331L);
            rule.setlRecordNo(987654332L);
            rule.setConsumeTxnMaxNum(11L);
            rule.setConsumeTxnMaxAmt(22L);
            rule.setConsumeTxnMaxAmtSum(33L);
            rule.setConsumeTxnMinAmt(11L);
            rule.setMonthMaxConsAmt(11L);
            rule.setMonthMaxCashCnt(lt1);
            rule.setMonthMaxChgAmt(lt1);
            rule.setMonthMaxChgCnt(lt1);
            rule.setMonthMaxConsCnt(lt1);
            rule.setMonthMaxConsAmt(lt1);
            rule.setMonthMaxInAmt(lt1);
            rule.setMonthMaxInCnt(lt1);
            rule.setMonthMaxOutAmt(lt1);
            rule.setMonthMaxOutCnt(lt1);
            rule.setRuleType("1");
            rule.setWithdrawTxnMaxAmt(lt1);
            rule.setWithdrawTxnMaxAmtSum(lt1);
            rule.setWithdrawTxnMaxNum(lt1);
            rule.setWithdrawTxnMinAmt(lt1);
            rule.setTransferTxnMaxAmt(lt1);
            rule.setTransferTxnMaxNum(lt1);
            rule.setTransferTxnMaxAmtSum(lt1);
            rule.setChargeTxnMaxAmt(lt1);
            rule.setChargeTxnMaxAmtSum(lt1);
            rule.setChargeTxnMaxNum(lt1);
            rule.setChargeTxnMinAmt(lt1);
            rule.setRuleType("1");
            rule.setArchivedFlag("2");
            rule.setArchiveTime(date);
            rule.setCheckFlag("0");
            rule.setCheckUid("chengmoutao");
            rule.setCheckTime(date);
            rule.setCreateTime(date);
            rule.setCreateUid("createUid1");
            rule.setEffectiveDate(date);
            rule.setLastModifyTime(date);
            rule.setLastModifyUid("chengmouta1o");
            long maxtxnamt = 11;
            long testshort = 22;
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setValidDate(date);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("2");
            rule.setUseFlag("2");
            int i =  tRiskCardCommonRuleMapper.insertSelective(rule);
            System.out.print("----------------------------插入TRiskCardCommonRule返回码:--:"+i);
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
            long ruleNo = 987654331L;
            TRiskCardCommonRule TRiskCardCommonRule = tRiskCardCommonRuleMapper.selectByPrimaryKey(ruleNo);
            System.out.print("------------------------------------------------------------"+TRiskCardCommonRule.getCheckUid()+TRiskCardCommonRule.getRemark());
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
            TRiskCardCommonRule rule = new TRiskCardCommonRule();
            Date date = new Date();
            long lt1 = 33L;
            rule.setRecordNo(987654331L);
            rule.setlRecordNo(987654332L);
            rule.setConsumeTxnMaxNum(11L);
            rule.setConsumeTxnMaxAmt(22L);
            rule.setConsumeTxnMaxAmtSum(33L);
            rule.setConsumeTxnMinAmt(11L);
            rule.setMonthMaxConsAmt(11L);
            rule.setMonthMaxCashCnt(lt1);
            rule.setMonthMaxChgAmt(lt1);
            rule.setMonthMaxChgCnt(lt1);
            rule.setMonthMaxConsCnt(lt1);
            rule.setMonthMaxConsAmt(lt1);
            rule.setMonthMaxInAmt(lt1);
            rule.setMonthMaxInCnt(lt1);
            rule.setMonthMaxOutAmt(lt1);
            rule.setMonthMaxOutCnt(lt1);
            rule.setRuleType("1");
            rule.setWithdrawTxnMaxAmt(lt1);
            rule.setWithdrawTxnMaxAmtSum(lt1);
            rule.setWithdrawTxnMaxNum(lt1);
            rule.setWithdrawTxnMinAmt(lt1);
            rule.setTransferTxnMaxAmt(lt1);
            rule.setTransferTxnMaxNum(lt1);
            rule.setTransferTxnMaxAmtSum(lt1);
            rule.setChargeTxnMaxAmt(lt1);
            rule.setChargeTxnMaxAmtSum(lt1);
            rule.setChargeTxnMaxNum(lt1);
            rule.setChargeTxnMinAmt(lt1);
            rule.setRuleType("1");
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
            long maxtxnamt = 11;
            long testshort = 22;
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setValidDate(date);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("2");
            rule.setUseFlag("2");


            int i = tRiskCardCommonRuleMapper.updateByPrimaryKeySelective(rule);
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
            TRiskCardCommonRule rule = new TRiskCardCommonRule();
            Date date = new Date();
            long lt1 = 44L;
            rule.setRecordNo(987654331L);
            rule.setlRecordNo(987654332L);
            rule.setConsumeTxnMaxNum(11L);
            rule.setConsumeTxnMaxAmt(22L);
            rule.setConsumeTxnMaxAmtSum(33L);
            rule.setConsumeTxnMinAmt(11L);
            rule.setMonthMaxConsAmt(11L);
            rule.setMonthMaxCashCnt(lt1);
            rule.setMonthMaxChgAmt(lt1);
            rule.setMonthMaxChgCnt(lt1);
            rule.setMonthMaxConsCnt(lt1);
            rule.setMonthMaxConsAmt(lt1);
            rule.setMonthMaxInAmt(lt1);
            rule.setMonthMaxInCnt(lt1);
            rule.setMonthMaxOutAmt(lt1);
            rule.setMonthMaxOutCnt(lt1);
            rule.setRuleType("1");
            rule.setWithdrawTxnMaxAmt(lt1);
            rule.setWithdrawTxnMaxAmtSum(lt1);
            rule.setWithdrawTxnMaxNum(lt1);
            rule.setWithdrawTxnMinAmt(lt1);
            rule.setTransferTxnMaxAmt(lt1);
            rule.setTransferTxnMaxNum(lt1);
            rule.setTransferTxnMaxAmtSum(lt1);
            rule.setChargeTxnMaxAmt(lt1);
            rule.setChargeTxnMaxAmtSum(lt1);
            rule.setChargeTxnMaxNum(lt1);
            rule.setChargeTxnMinAmt(lt1);
            rule.setRuleType("1");
            rule.setArchivedFlag("2");
            rule.setArchiveTime(date);
            rule.setCheckFlag("0");
            rule.setCheckUid("chengmoutao");
            rule.setCheckTime(date);
            rule.setCreateTime(date);
            rule.setCreateUid("createUid1");
            rule.setEffectiveDate(date);
            rule.setLastModifyTime(date);
            rule.setLastModifyUid("chengmouta1o");
            long maxtxnamt = 11;
            long testshort = 22;
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setValidDate(date);
            rule.setRsvdAmt5(testshort);
            rule.setTxnChannel("2");
            rule.setUseFlag("2");
            int i= tRiskCardCommonRuleMapper.updateByPrimaryKey(rule);
            System.out.print("----------------------------testupdateByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testfindCardRisk(){
        try {
            String txnChannel = "2";
            String cardNo = "13213";
            String cardType ="1";
            char grade = '1';
            Map map = new HashMap();
            map.put("txnChannel",txnChannel);
            map.put("cardNo",cardNo);
            map.put("cardType",cardType);
            map.put("grade",grade);

            TRiskCardCommonRule rule = tRiskCardCommonRuleMapper.findCardRisk(map);
            System.out.print("----------------------------testfindCardRisk返回:--getRecordNo:"+rule);
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
            long ruleNo = 987654330L;
            int i = tRiskCardCommonRuleMapper.deleteByPrimaryKey(ruleNo);
            System.out.print("----------------------------testdeleteByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
