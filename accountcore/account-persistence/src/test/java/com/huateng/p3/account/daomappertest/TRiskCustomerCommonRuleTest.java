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
import com.huateng.p3.account.persistence.TRiskCustomerCommonRuleMapper;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TRiskCustomerCommonRuleTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TRiskCustomerCommonRuleTest.class);

    @Autowired
    private TRiskCustomerCommonRuleMapper tRiskCustomerCommonRuleMapper;

    /*
    int deleteByPrimaryKey(Short ruleNo);

    int insert(TRiskCustomerCommonRule rule);

    int insertSelective(TRiskCustomerCommonRule rule);

    TRiskCustomerCommonRule selectByPrimaryKey(Short ruleNo);

    int updateByPrimaryKeySelective(TRiskCustomerCommonRule rule);

    int updateByPrimaryKey(TRiskCustomerCommonRule rule);
     */

    /**
     *测试完成
     */
    @Test
    public void testinsert() {
        try {
            TRiskCustomerCommonRule rule = new TRiskCustomerCommonRule();
            Date date = new Date();
            long lt1 = 22L;
            rule.setRecordNo(9876543291L);
            rule.setlRecordNo(987654328L);
            rule.setAccountType("1");
            rule.setAccountNo("13213");
            rule.setConsumeTransMaxNum(11L);
            rule.setConsumeTransMaxAmt(22L);
            rule.setConsumeTransMaxAmtSum(33L);
            rule.setConsumeTransMinAmt(11L);
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
            rule.setExistRiskRule(true);
            rule.setWithdrawTransMaxAmt(lt1);
            rule.setWithdrawTransMaxAmtSum(lt1);
            rule.setWithdrawTransMaxNum(lt1);
            rule.setWithdrawTransMinAmt(lt1);
            rule.setTransferInMaxAmt(lt1);
            rule.setTransferInMaxAmtSum(lt1);
            rule.setTransferInMaxNum(lt1);
            rule.setTransferInMinAmt(lt1);
            rule.setTransferTransMaxAmt(lt1);
            rule.setTransferTransMaxNum(lt1);
            rule.setTransferTransMaxAmtSum(lt1);
            rule.setChargeTransMaxAmt(lt1);
            rule.setChargeTransMaxAmtSum(lt1);
            rule.setChargeTransMaxNum(lt1);
            rule.setChargeTransMinAmt(lt1);
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
            rule.setAcceptChannel("2");
            rule.setUseFlag("2");
            rule.setValidDate(date);
            int i =  tRiskCustomerCommonRuleMapper.insert(rule);
            System.out.print("----------------------------插入TRiskCustomerCommonRule返回码:--:"+i);
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
            TRiskCustomerCommonRule rule = new TRiskCustomerCommonRule();
            Date date = new Date();
            long lt1 = 22L;
            rule.setRecordNo(987654328L);
            rule.setlRecordNo(987654329L);
            rule.setAccountType("1");
            rule.setAccountNo("13213");
            rule.setConsumeTransMaxNum(11L);
            rule.setConsumeTransMaxAmt(22L);
            rule.setConsumeTransMaxAmtSum(33L);
            rule.setConsumeTransMinAmt(11L);
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
            rule.setExistRiskRule(true);
            rule.setWithdrawTransMaxAmt(lt1);
            rule.setWithdrawTransMaxAmtSum(lt1);
            rule.setWithdrawTransMaxNum(lt1);
            rule.setWithdrawTransMinAmt(lt1);
            rule.setTransferInMaxAmt(lt1);
            rule.setTransferInMaxAmtSum(lt1);
            rule.setTransferInMaxNum(lt1);
            rule.setTransferInMinAmt(lt1);
            rule.setTransferTransMaxAmt(lt1);
            rule.setTransferTransMaxNum(lt1);
            rule.setTransferTransMaxAmtSum(lt1);
            rule.setChargeTransMaxAmt(lt1);
            rule.setChargeTransMaxAmtSum(lt1);
            rule.setChargeTransMaxNum(lt1);
            rule.setChargeTransMinAmt(lt1);
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
            rule.setAcceptChannel("2");
            rule.setUseFlag("2");
            rule.setValidDate(date);
            int i =  tRiskCustomerCommonRuleMapper.insertSelective(rule);
            System.out.print("----------------------------插入TRiskCustomerCommonRule返回码:--:"+i);
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
            long ruleNo = 987654328L;
            TRiskCustomerCommonRule TRiskCustomerCommonRule = tRiskCustomerCommonRuleMapper.selectByPrimaryKey(ruleNo);
            System.out.print("------------------------------------------------------------"+TRiskCustomerCommonRule.getCheckUid()+TRiskCustomerCommonRule.getRemark());
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
            TRiskCustomerCommonRule rule = new TRiskCustomerCommonRule();
            Date date = new Date();
            long lt1 = 33L;
            rule.setRecordNo(987654328L);
            rule.setlRecordNo(987654329L);
            rule.setAccountType("1");
            rule.setAccountNo("13213");
            rule.setConsumeTransMaxNum(11L);
            rule.setConsumeTransMaxAmt(22L);
            rule.setConsumeTransMaxAmtSum(33L);
            rule.setConsumeTransMinAmt(11L);
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
            rule.setExistRiskRule(true);
            rule.setWithdrawTransMaxAmt(lt1);
            rule.setWithdrawTransMaxAmtSum(lt1);
            rule.setWithdrawTransMaxNum(lt1);
            rule.setWithdrawTransMinAmt(lt1);
            rule.setTransferInMaxAmt(lt1);
            rule.setTransferInMaxAmtSum(lt1);
            rule.setTransferInMaxNum(lt1);
            rule.setTransferInMinAmt(lt1);
            rule.setTransferTransMaxAmt(lt1);
            rule.setTransferTransMaxNum(lt1);
            rule.setTransferTransMaxAmtSum(lt1);
            rule.setChargeTransMaxAmt(lt1);
            rule.setChargeTransMaxAmtSum(lt1);
            rule.setChargeTransMaxNum(lt1);
            rule.setChargeTransMinAmt(lt1);
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
            rule.setAcceptChannel("2");
            rule.setUseFlag("2");
            rule.setValidDate(date);

            int i = tRiskCustomerCommonRuleMapper.updateByPrimaryKeySelective(rule);
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
            TRiskCustomerCommonRule rule = new TRiskCustomerCommonRule();
            Date date = new Date();
            long lt1 = 4L;
            rule.setRecordNo(987654328L);
            rule.setlRecordNo(987654329L);
            rule.setUserGrade("1");
            rule.setAccountType("1");
            rule.setAccountNo("13213");
            rule.setConsumeTransMaxNum(11L);
            rule.setConsumeTransMaxAmt(22L);
            rule.setConsumeTransMaxAmtSum(33L);
            rule.setConsumeTransMinAmt(11L);
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
            rule.setExistRiskRule(true);
            rule.setWithdrawTransMaxAmt(lt1);
            rule.setWithdrawTransMaxAmtSum(lt1);
            rule.setWithdrawTransMaxNum(lt1);
            rule.setWithdrawTransMinAmt(lt1);
            rule.setTransferInMaxAmt(lt1);
            rule.setTransferInMaxAmtSum(lt1);
            rule.setTransferInMaxNum(lt1);
            rule.setTransferInMinAmt(lt1);
            rule.setTransferTransMaxAmt(lt1);
            rule.setTransferTransMaxNum(lt1);
            rule.setTransferTransMaxAmtSum(lt1);
            rule.setChargeTransMaxAmt(lt1);
            rule.setChargeTransMaxAmtSum(lt1);
            rule.setChargeTransMaxNum(lt1);
            rule.setChargeTransMinAmt(lt1);
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
            rule.setAcceptChannel("2");
            rule.setUseFlag("2");
            rule.setValidDate(date);

            int i=tRiskCustomerCommonRuleMapper.updateByPrimaryKey(rule);
            System.out.print("----------------------------testupdateByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testfindAccountGeneralRisk(){
        try {
            String AcceptChannel = "2";
            String accountType ="1";
            String grade = "1";

            TRiskCustomerCommonRule rule = tRiskCustomerCommonRuleMapper.findAccountGeneralRisk(AcceptChannel,accountType,grade,new Date());
            System.out.print("----------------------------testfindAccountGeneralRisk返回:--getRecordNo:"+rule.getRecordNo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testfindPhoneNumPayAccountRisk(){
        try {
            String AcceptChannel = "2";
            String accountNo = "13213";
            String accountType ="1";
            char grade = '1';
            Map map = new HashMap();
            map.put("currentDate",new Date());
            map.put("AcceptChannel",AcceptChannel);
            map.put("accountNo",accountNo);
            map.put("accountType",accountType);
            map.put("grade",grade);

            TRiskCustomerCommonRule rule = tRiskCustomerCommonRuleMapper.findPhoneNumPayAccountRisk(map);
            System.out.print("----------------------------testfindPhoneNumPayAccountRisk返回:--getRecordNo:"+rule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testfindCustomerSelfDefineAccountRisk(){
        try {
            String AcceptChannel = "2";
            String accountNo = "13213";
            String accountType ="1";
            char grade = '1';
            Map map = new HashMap();
            map.put("currentDate",new Date());
            map.put("AcceptChannel",AcceptChannel);
            map.put("accountNo",accountNo);
            map.put("accountType",accountType);
            map.put("grade",grade);

            TRiskCustomerCommonRule rule = tRiskCustomerCommonRuleMapper.findPhoneNumPayAccountRisk(map);
            System.out.print("----------------------------testfindCustomerSelfDefineAccountRisk返回:--getRecordNo:"+rule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testsychronyzeCustomerSelfDefineAccountRisk(){
        String AcceptChannel = "2";
        String accountNo = "13213";
        String accountType ="1";
        char grade = '1';

        int i = tRiskCustomerCommonRuleMapper.sychronyzeCustomerSelfDefineAccountRisk(AcceptChannel, accountNo, accountType, grade, null);
        System.out.print("----------------------------testsychronyzeCustomerSelfDefineAccountRisk返回:--getRecordNo:"+i);
    }
    @Test
    public void testinsertNewCustomerRiskRule(){
        try {
            TRiskCustomerCommonRule rule = new TRiskCustomerCommonRule();
            Date date = new Date();
            long lt1 = 4L;
            rule.setlRecordNo(654330L);
            rule.setUserGrade("1");
            rule.setAccountType("1");
            rule.setAccountNo("13213");
            rule.setConsumeTransMaxNum(11L);
            rule.setConsumeTransMaxAmt(22L);
            rule.setConsumeTransMaxAmtSum(33L);
            rule.setConsumeTransMinAmt(11L);
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
            rule.setExistRiskRule(true);
            rule.setWithdrawTransMaxAmt(lt1);
            rule.setWithdrawTransMaxAmtSum(lt1);
            rule.setWithdrawTransMaxNum(lt1);
            rule.setWithdrawTransMinAmt(lt1);
            rule.setTransferInMaxAmt(lt1);
            rule.setTransferInMaxAmtSum(lt1);
            rule.setTransferInMaxNum(lt1);
            rule.setTransferInMinAmt(lt1);
            rule.setTransferTransMaxAmt(lt1);
            rule.setTransferTransMaxNum(lt1);
            rule.setTransferTransMaxAmtSum(lt1);
            rule.setChargeTransMaxAmt(lt1);
            rule.setChargeTransMaxAmtSum(lt1);
            rule.setChargeTransMaxNum(lt1);
            rule.setChargeTransMinAmt(lt1);
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
            long testshort = 22;
            rule.setRemark("test");
            rule.setResvFld1("1");
            rule.setRsvdAmt1(testshort);
            rule.setRsvdAmt2(testshort);
            rule.setRsvdAmt3(testshort);
            rule.setRsvdAmt4(testshort);
            rule.setRsvdAmt5(testshort);
            rule.setAcceptChannel("2");
            rule.setUseFlag("2");
            rule.setValidDate(date);
            int i = tRiskCustomerCommonRuleMapper.insertNewCustomerRiskRule(rule);
            System.out.print("----------------------------testinsertNewCustomerRiskRule返回:--getRecordNo:"+i);
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
            long ruleNo = 14808L;
            int i = tRiskCustomerCommonRuleMapper.deleteByPrimaryKey(ruleNo);
            System.out.print("----------------------------testdeleteByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
