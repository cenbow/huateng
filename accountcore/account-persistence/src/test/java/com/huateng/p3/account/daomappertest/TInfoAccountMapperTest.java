package com.huateng.p3.account.daomappertest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TInfoAccountCardMapper;
import com.huateng.p3.account.persistence.TInfoAccountMapper;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoAccountCard;

/**
 * Created with IntelliJ IDEA.
 * User: wangshushuang
 * Date: 13-12-6
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
public class TInfoAccountMapperTest extends BaseSpringTest {

    private static Logger log =  LoggerFactory.getLogger(TInfoAccountMapperTest.class);

    @Autowired
    private TInfoAccountMapper tInfoAccountMapper;

    @Autowired
    private TInfoAccountCardMapper tInfoAccountCardMapper;

    @Before
    public void init(){
        log.debug("init start....");
        System.out.println("init start....");
    }

    @After
    public void destory(){

        log.debug("destory....");
        System.out.println("destory....");

    }

    /**
     * 测试插入方法
     */
    @Test
    public void testInsert(){

        String accountNo = "8899000008075719";// 8899000008075714
        String accountName = "我的天翼预付卡";
        String customerNo = "8899000008075714";
//        Date openDate = new Date();
        Date openDate = getDateFormatDate("yyyy/MM/dd");
        Date closeDate = getDateFormatDate("yyyy/MM/dd");
        Date validDate = getDateFormatDate("yyyy/MM/dd");
        String txnPasswd = "431C55403632B1F2";
        String initPasswd = "499977";
        String initPasswdModified = "0";
        Long passwdErrNum = new Long(0);
        Date passwdErrLockTime = new Date();
        String status =  "0";
        String commStatus = "1";
        String type = "B";
        String grade = "1";
        String realname = "3";
        String apanage = "002110000010000";
        String unitId = "123";
        String areaCode = "999900";
        String cityCode = "999901";
        String openChannel = "00";
        Long balance = new Long(20000);
        Long lastDayBal = new Long(0);
        Long lastMonthBal = new Long(0);
        Long availableBalance = new Long(20000);
        Long unavailableBalance = new Long(0);
        Long withdrawBalance = new Long(0);
        Long frozenAmount = new Long(0);
        Long preauthorizedAmount = new Long(0);
        String lastTxnDate =  "20130318";
        Long daySumConsAmt = new Long(0);
        Integer daySumConsCnt = new Integer(0);
        Long daySumOutAmt = new Long(0);
        Integer daySumOutCnt = new Integer(0);
        Long daySumInAmt = new Long(0);
        Integer daySumInCnt = new Integer(0);
        Long daySumCashAmt = new Long(0);
        Integer daySumCashCnt = new Integer(0);
        Long daySumChgAmt = new Long(0);
        Integer daySumChgCnt = new Integer(0);
        Long monthSumConsAmt = new Long(0);
        Long monthSumOutAmt = new Long(0);
        Long monthSumInAmt = new Long(0);
        Long monthSumCashAmt = new Long(0);
        Long monthSumChgAmt = new Long(0);
        String allowedClose = "1";
        String closeRtnCash = "1";
        String encryptedMsg = "A73B68A6E896C1AFB95590ECF1DED4C1";
        Date lastUpdateTime = new Date();
        Date lastTxnTime = new Date();
        Long rsvdAmt1 = new Long(0);
        Long rsvdAmt2 = new Long(0);
        String rsvdText1 = "aaa";
        String rsvdText2 = "bbb";
        Long monthSumConsCnt = new Long(0);
        Long monthSumOutCnt = new Long(0);
        Long monthSumInCnt = new Long(0);
        Long monthSumCashCnt = new Long(0);
        Long monthSumChgCnt = new Long(0);
        String masterAccountNo = "123";
        String forbiddenTxn = "234";
        Long extendTimes = new Long(0);
        String forbiddenChannel = "abc";
        String cardNo = "777";
        String cardType = "1";
        String cardBindingMethod = "1";

        int oriDeleteCount =  tInfoAccountMapper.deleteByPrimaryKey(accountNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoAccount tInfoAccount = this.getTInfoAccount(accountNo, accountName, customerNo, openDate, closeDate, validDate, txnPasswd,  initPasswd, initPasswdModified,  passwdErrNum, passwdErrLockTime,status,commStatus,type,grade,realname,
                apanage, unitId, areaCode, cityCode, openChannel, balance, lastDayBal, lastMonthBal, availableBalance, unavailableBalance, withdrawBalance, frozenAmount,preauthorizedAmount, lastTxnDate, daySumConsAmt,
                daySumConsCnt, daySumOutAmt, daySumOutCnt, daySumInAmt, daySumInCnt, daySumCashAmt, daySumCashCnt, daySumChgAmt, daySumChgCnt,
                monthSumConsAmt, monthSumOutAmt, monthSumInAmt, monthSumCashAmt, monthSumChgAmt, allowedClose, closeRtnCash, encryptedMsg, lastUpdateTime, lastTxnTime, rsvdAmt1, rsvdAmt2, rsvdText1, rsvdText2, monthSumConsCnt,
                monthSumOutCnt, monthSumInCnt, monthSumCashCnt, monthSumChgCnt, masterAccountNo, forbiddenTxn, extendTimes, forbiddenChannel, cardNo, cardType,cardBindingMethod);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

    }

    @Test
    public void testUpdateBalanceAndPwdErr(){

        String accountNo = "8899000008075719";//8899000008075714
        String customerNo = "8899000008075714";
        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        Long balance = new Long(30000);
        Long lastDayBal = new Long(1);
        Long lastMonthBal = new Long(2);
        Long availableBalance = new Long(40000);
        Long unavailableBalance = new Long(5);
        Long withdrawBalance = new Long(6);
        Long frozenAmount = new Long(7);
        Long preauthorizedAmount = new Long(8);
        String lastTxnDate =  "20130318";
        Long daySumConsAmt = new Long(9);
        Integer daySumConsCnt = new Integer(10);
        Long daySumOutAmt = new Long(11);
        Integer daySumOutCnt = new Integer(12);
        Long daySumInAmt = new Long(13);
        Integer daySumInCnt = new Integer(14);
        Long daySumCashAmt = new Long(15);
        Integer daySumCashCnt = new Integer(16);
        Long daySumChgAmt = new Long(17);
        Integer daySumChgCnt = new Integer(18);
        Long monthSumConsAmt = new Long(19);
        Long monthSumOutAmt = new Long(20);
        Long monthSumInAmt = new Long(21);
        Long monthSumCashAmt = new Long(22);
        Long monthSumChgAmt = new Long(23);
        String allowedClose = "8";
        String closeRtnCash = "9";
        String encryptedMsg = "A73B68A6E896C1AFB95590ECF1DED4C8";
        Date lastUpdateTime = new Date();

        Long monthSumConsCnt = new Long(24);
        Long monthSumOutCnt = new Long(25);
        Long monthSumInCnt = new Long(26);
        Long monthSumCashCnt = new Long(27);
        Long monthSumChgCnt = new Long(28);
        Long passwdErrNum = new Long(0);
        Date passwdErrLockTime = new Date();

        tInfoAccount = this.getTInfoAccount(accountNo, null, null, null, null, null, null,  null, null,  passwdErrNum, passwdErrLockTime,null,null,null,null,null,
                 null, null, null, null, null, balance, lastDayBal, lastMonthBal, availableBalance, unavailableBalance, withdrawBalance, frozenAmount,preauthorizedAmount, lastTxnDate, daySumConsAmt,
                daySumConsCnt, daySumOutAmt, daySumOutCnt, daySumInAmt, daySumInCnt, daySumCashAmt, daySumCashCnt, daySumChgAmt, daySumChgCnt,
                monthSumConsAmt, monthSumOutAmt, monthSumInAmt, monthSumCashAmt, monthSumChgAmt, allowedClose, closeRtnCash, encryptedMsg, lastUpdateTime, null, null, null, null, null, monthSumConsCnt,
                monthSumOutCnt, monthSumInCnt, monthSumCashCnt, monthSumChgCnt, null, null, null, null, null, null,null);
        int updateCount = tInfoAccountMapper.updateBalanceAndPwdErr(tInfoAccount);
        log.debug("updateCount=[" + updateCount + "]");
        assertThat(updateCount,is(1));

    }


    @Test
    public void testUpdateAccountBalance(){

        String accountNo = "8899000008075719";

        String customerNo = "8899000008075714";
        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        Long balance = new Long(40000);
        Long lastDayBal = new Long(5);
        Long lastMonthBal = new Long(6);
        Long availableBalance = new Long(70000);
        Long unavailableBalance = new Long(8);
        Long withdrawBalance = new Long(9);
        Long frozenAmount = new Long(10);
        Long preauthorizedAmount = new Long(11);
        String lastTxnDate =  "20130319";
        Long daySumConsAmt = new Long(12);
        Integer daySumConsCnt = new Integer(13);
        Long daySumOutAmt = new Long(14);
        Integer daySumOutCnt = new Integer(15);
        Long daySumInAmt = new Long(16);
        Integer daySumInCnt = new Integer(17);
        Long daySumCashAmt = new Long(18);
        Integer daySumCashCnt = new Integer(19);
        Long daySumChgAmt = new Long(20);
        Integer daySumChgCnt = new Integer(21);
        Long monthSumConsAmt = new Long(22);
        Long monthSumOutAmt = new Long(23);
        Long monthSumInAmt = new Long(24);
        Long monthSumCashAmt = new Long(25);
        Long monthSumChgAmt = new Long(26);
        String allowedClose = "2";
        String closeRtnCash = "3";
        String encryptedMsg = "F73B68A6E896C1AFB95590ECF1DED4C1";
        Date lastUpdateTime = new Date();
        Date lastTxnTime = new Date();
        Long passwdErrNum = new Long(2);
        Date passwdErrLockTime = new Date();
        tInfoAccount = this.getTInfoAccount(accountNo, null, null, null, null, null, null,  null, null,  passwdErrNum, passwdErrLockTime,null,null,null,null,null,
                null, null, null, null, null, balance, lastDayBal, lastMonthBal, availableBalance, unavailableBalance, withdrawBalance, frozenAmount,preauthorizedAmount, lastTxnDate, daySumConsAmt,
                daySumConsCnt, daySumOutAmt, daySumOutCnt, daySumInAmt, daySumInCnt, daySumCashAmt, daySumCashCnt, daySumChgAmt, daySumChgCnt,
                monthSumConsAmt, monthSumOutAmt, monthSumInAmt, monthSumCashAmt, monthSumChgAmt, allowedClose, closeRtnCash, encryptedMsg, lastUpdateTime, lastTxnTime, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,null);
        int updateCount = tInfoAccountMapper.updateAccountBalance(tInfoAccount);
        log.debug("updateCount=[" + updateCount + "]");
        assertThat(updateCount,is(1));
    }

    @Test
    public void testFindFundAccountByUnifyIdForUpdate(){

        //需要补充客户信息值

        String unifyId = "8899000008075714";//产品号
        String accountNo = "8899000008075719";
        String customerNo = "8899000008075714";
        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        tInfoAccount = tInfoAccountMapper.findFundAccountByUnifyIdForUpdate(unifyId);
        log.debug("tInfoAccount.getAccountNo=[" + tInfoAccount.getAccountNo() + "]");
        assertThat(accountNo,is(tInfoAccount.getAccountNo()));
        assertThat(customerNo,is(tInfoAccount.getCustomerNo()));

    }

    @Test
    public void testFindFundAccountByAccountNoForUpdate(){

        String accountNo = "8899000008075719";
        String customerNo = "8899000008075714";
        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        tInfoAccount = tInfoAccountMapper.findFundAccountByAccountNoForUpdate(accountNo);
        log.debug("tInfoAccount.getAccountNo=[" + tInfoAccount.getAccountNo() + "]");
        assertThat(accountNo,is(tInfoAccount.getAccountNo()));
        assertThat(customerNo,is(tInfoAccount.getCustomerNo()));

    }

    @Test
    public void testFindFundAccountByCardNoForUpdate(){

        //card_no= #{value,jdbcType=VARCHAR} and c.BINDING_FLAG='1' and a.STATUS!='9'
        Long recordNo = new Long(9488);
        String customerNo = "8630898000000344";
//        String offlineAccountNo = "8632898000000347";
        String offlineAccountNo = "86328980770003" +  new Random().nextInt(100000) ;
        String fundAccountNo = "86318980000003" + new Random().nextInt(100000);
        String cardNo = "8000000700003012" + new Random().nextInt(100000) ;
        String cardType = "6";
        String bindingFlag = "1";
        String bindingMehod = "1";
        Date bindingTime = new Date();
        Date unbindingTime = new Date();
        String bindingAcceptOrgCode = "103460100000001";
        String bingdingAcceptUid = "0000000000000338";
        Date bindingAcceptTime = new Date();
        String unbindingAcceptOrgCode = "123";
        String unbingdingAcceptUid = "234";
        Date unbindingAcceptTime = new Date();
        String areaCode = "460000";
        String cityCode = "460100";

        int oriDeleteCount =  tInfoAccountCardMapper.deleteByPrimaryKey(recordNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoAccountCard tInfoAccountCard = getTInfoAccountCard(recordNo,customerNo,offlineAccountNo,fundAccountNo,
                cardNo, cardType,bindingFlag,bindingMehod,bindingTime,
                unbindingTime, bindingAcceptOrgCode,bingdingAcceptUid, bindingAcceptTime, unbindingAcceptOrgCode,
                unbingdingAcceptUid,  unbindingAcceptTime, areaCode,cityCode);
        int insertCount = tInfoAccountCardMapper.insertSelective(tInfoAccountCard);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        String accountNo = fundAccountNo;
        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertAccountCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertAccountCount=[" + insertAccountCount + "]");
        assertThat(insertAccountCount,is(1));
        tInfoAccount = tInfoAccountMapper.findFundAccountByCardNoForUpdate(cardNo);
        log.debug("tInfoAccount.getAccountNo=[" + tInfoAccount.getAccountNo() + "]");
        assertThat(accountNo,is(tInfoAccount.getAccountNo()));
        assertThat(customerNo,is(tInfoAccount.getCustomerNo()));
        assertThat(cardNo,is(tInfoAccount.getCardNo()));


    }


    @Test
    public void testFindFundAccountByCardNo(){

        Long recordNo = new Long(9488);
        String customerNo = "8630898000000344";
//        String offlineAccountNo = "8632898000000347";
        String offlineAccountNo = "86328980770003" +  new Random().nextInt(100000) ;
        String fundAccountNo = "86318980000003" + new Random().nextInt(100000);
        String cardNo = "8000000700003012" + new Random().nextInt(100000) ;
        String cardType = "6";
        String bindingFlag = "1";
        String bindingMehod = "1";
        Date bindingTime = new Date();
        Date unbindingTime = new Date();
        String bindingAcceptOrgCode = "103460100000001";
        String bingdingAcceptUid = "0000000000000338";
        Date bindingAcceptTime = new Date();
        String unbindingAcceptOrgCode = "123";
        String unbingdingAcceptUid = "234";
        Date unbindingAcceptTime = new Date();
        String areaCode = "460000";
        String cityCode = "460100";

        int oriDeleteCount =  tInfoAccountCardMapper.deleteByPrimaryKey(recordNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoAccountCard tInfoAccountCard = getTInfoAccountCard(recordNo,customerNo,offlineAccountNo,fundAccountNo,
                cardNo, cardType,bindingFlag,bindingMehod,bindingTime,
                unbindingTime, bindingAcceptOrgCode,bingdingAcceptUid, bindingAcceptTime, unbindingAcceptOrgCode,
                unbingdingAcceptUid,  unbindingAcceptTime, areaCode,cityCode);
        int insertCount = tInfoAccountCardMapper.insertSelective(tInfoAccountCard);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        String accountNo = fundAccountNo;
        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertAccountCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertAccountCount=[" + insertAccountCount + "]");
        assertThat(insertAccountCount,is(1));
        tInfoAccount = tInfoAccountMapper.findFundAccountByCardNo(cardNo);
        log.debug("tInfoAccount.getAccountNo=[" + tInfoAccount.getAccountNo() + "]");
        assertThat(accountNo,is(tInfoAccount.getAccountNo()));
        assertThat(customerNo,is(tInfoAccount.getCustomerNo()));
        assertThat(cardNo,is(tInfoAccount.getCardNo()));

    }

    @Test
    public void testFindFundAccountByCustomerNoForUpdate(){
        //TODO 需要进行开发


    }

    @Test
    public void testFindFundAccountByCustomerNo(){

        String accountNo = "8899000008075719";
        String customerNo = "98999000080" + new Random().nextInt(100000) ;
        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        tInfoAccount = tInfoAccountMapper.findFundAccountByCustomerNo(customerNo);
        log.debug("tInfoAccount.getAccountNo=[" + tInfoAccount.getAccountNo() + "]");
        assertThat(accountNo,is(tInfoAccount.getAccountNo()));
        assertThat(customerNo,is(tInfoAccount.getCustomerNo()));

    }


    @Test
    public void testFindFundAccountByAccountNo(){

        String accountNo = "8899000008075719";
        String customerNo = "8899000008075714";
        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        tInfoAccount = tInfoAccountMapper.findFundAccountByAccountNo(accountNo);
        log.debug("tInfoAccount.getAccountNo=[" + tInfoAccount.getAccountNo() + "]");
        assertThat(accountNo,is(tInfoAccount.getAccountNo()));
        assertThat(customerNo,is(tInfoAccount.getCustomerNo()));

    }

    @Test
    public void testFindFundAccountByProductNo(){
        String unifyId = "8899000008075714";//产品号
        String accountNo = "8899000008075719";
        String customerNo = "8899000008075714";
        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        tInfoAccount = tInfoAccountMapper.findFundAccountByUnifyIdForUpdate(unifyId);
        log.debug("tInfoAccount.getAccountNo=[" + tInfoAccount.getAccountNo() + "]");
        assertThat(accountNo,is(tInfoAccount.getAccountNo()));
        assertThat(customerNo,is(tInfoAccount.getCustomerNo()));

    }

  /*  @Test
    public void testFindOfflineAccountByCardNoForUpdate(){

        //card_no= #{value,jdbcType=VARCHAR} and c.BINDING_FLAG='1' and a.STATUS!='9'
        Long recordNo = new Long(9488);
        String customerNo = "8630898000000344";
//        String offlineAccountNo = "8632898000000347";
        String offlineAccountNo = "86328980770003" +  new Random().nextInt(100000) ;
        String fundAccountNo = "8631898000000345";
        String cardNo = "8000000700003012" + new Random().nextInt(100000) ;
        String cardType = "6";
        String bindingFlag = "1";
        String bindingMehod = "1";
        Date bindingTime = new Date();
        Date unbindingTime = new Date();
        String bindingAcceptOrgCode = "103460100000001";
        String bingdingAcceptUid = "0000000000000338";
        Date bindingAcceptTime = new Date();
        String unbindingAcceptOrgCode = "123";
        String unbingdingAcceptUid = "234";
        Date unbindingAcceptTime = new Date();
        String areaCode = "460000";
        String cityCode = "460100";

        int oriDeleteCount =  tInfoAccountCardMapper.deleteByPrimaryKey(recordNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoAccountCard tInfoAccountCard = getTInfoAccountCard(recordNo,customerNo,offlineAccountNo,fundAccountNo,
                cardNo, cardType,bindingFlag,bindingMehod,bindingTime,
                unbindingTime, bindingAcceptOrgCode,bingdingAcceptUid, bindingAcceptTime, unbindingAcceptOrgCode,
                unbingdingAcceptUid,  unbindingAcceptTime, areaCode,cityCode);
        int insertCount = tInfoAccountCardMapper.insertSelective(tInfoAccountCard);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        String accountNo = offlineAccountNo;

        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertAccountCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertAccountCount=[" + insertAccountCount + "]");
        assertThat(insertAccountCount,is(1));
        tInfoAccount = tInfoAccountMapper.findOfflineAccountByCardNoForUpdate(cardNo);
        log.debug("tInfoAccount.getAccountNo=[" + tInfoAccount.getAccountNo() + "]");
        assertThat(accountNo,is(tInfoAccount.getAccountNo()));
        assertThat(customerNo,is(tInfoAccount.getCustomerNo()));
        assertThat(cardNo,is(tInfoAccount.getCardNo()));

    }*/

  /*  @Test
    public void testFindOfflineAccountsByProductNoForUpdate(){
        //需要补充客户信息值
        String productNo = "8899000008075714";//产品号
        String accountNo = "8899000008075719";
        String customerNo = "8899000008075714";
        String type = "2";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        tInfoAccount = tInfoAccountMapper.findOfflineAccountByProductNoForUpdate(productNo);
        log.debug("tInfoAccount.getAccountNo=[" + tInfoAccount.getAccountNo() + "]");
        assertThat(accountNo,is(tInfoAccount.getAccountNo()));
        assertThat(customerNo,is(tInfoAccount.getCustomerNo()));

    }
*/
    @Test
    public void testIncreaseTxnPasswdErrorNum(){

        String accountNo = "8899000008075719";
        String customerNo = "8899000008075714";
        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        Long passwdErrNum = new Long(3);
        Date passwdErrLockTime = new Date();
        passwdErrLockTime = null;

        int updateCount = tInfoAccountMapper.increaseTxnPasswdErrorNum(passwdErrNum, passwdErrLockTime,accountNo);
        log.debug("updateCount=[" + updateCount + "]");
        assertThat(updateCount,is(1));

    }

    @Test
    public void testFindIncludeClosedByAccountNoForUpdate(){

        String accountNo = "8899000008075719";
        String customerNo = "8899000008075714";
        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        tInfoAccount = tInfoAccountMapper.findIncludeClosedByAccountNoForUpdate(accountNo);
        log.debug("accountNo=[" + tInfoAccount.getAccountNo() + "]");
        assertThat(accountNo,is(tInfoAccount.getAccountNo() ));

    }

    @Test
    public void testFindOfflineAccountByCardNoWithClosedForUpdate(){

        Long recordNo = new Long(9488);
        String customerNo = "8630898000000344";
//        String offlineAccountNo = "8632898000000347";
        String offlineAccountNo = "86328980770003" +  new Random().nextInt(100000) ;
        String fundAccountNo = "8631898000000345";
        String cardNo = "8000000700003012" + new Random().nextInt(100000) ;
        String cardType = "6";
        String bindingFlag = "1";
        String bindingMehod = "1";
//        Date bindingTime = new Date();
        Date bindingTime = getNextDay(new Date(), -10);
        Date unbindingTime = new Date();
        unbindingTime = getNextDay(new Date(), 10);
        String bindingAcceptOrgCode = "103460100000001";
        String bingdingAcceptUid = "0000000000000338";
        Date bindingAcceptTime = new Date();
        String unbindingAcceptOrgCode = "123";
        String unbingdingAcceptUid = "234";
        Date unbindingAcceptTime = new Date();
        String areaCode = "460000";
        String cityCode = "460100";

        int oriDeleteCount =  tInfoAccountCardMapper.deleteByPrimaryKey(recordNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoAccountCard tInfoAccountCard = getTInfoAccountCard(recordNo,customerNo,offlineAccountNo,fundAccountNo,
                cardNo, cardType,bindingFlag,bindingMehod,bindingTime,
                unbindingTime, bindingAcceptOrgCode,bingdingAcceptUid, bindingAcceptTime, unbindingAcceptOrgCode,
                unbingdingAcceptUid,  unbindingAcceptTime, areaCode,cityCode);
        int insertCount = tInfoAccountCardMapper.insertSelective(tInfoAccountCard);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        String accountNo = offlineAccountNo;

        String type = "1";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertAccountCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertAccountCount=[" + insertAccountCount + "]");
        assertThat(insertAccountCount, is(1));
        String acceptTransTime = "104242";
        String acceptTransDate = getDateFormat();
        acceptTransDate = getDateFormat(getNextDay(new Date(), 9),"yyyyMMdd");
        tInfoAccount = tInfoAccountMapper.findOfflineAccountByCardNoWithClosedForUpdate(cardNo,acceptTransDate,acceptTransTime);
        log.debug("tInfoAccount.getAccountNo=[" + tInfoAccount.getAccountNo() + "]");
        assertThat(accountNo,is(tInfoAccount.getAccountNo()));
        assertThat(customerNo,is(tInfoAccount.getCustomerNo()));
        assertThat(cardNo,is(tInfoAccount.getCardNo()));


    }

    @Test
    public void testResetPasswdLockInfo(){

        String accountNo = "8899000008075719";
        String customerNo = "8899000008075714";
        String type = "2";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        Date lastUpdateTime = new Date();
        int updateCount = tInfoAccountMapper.resetPasswdLockInfo(lastUpdateTime,accountNo);
        log.debug("updateCount=[" + updateCount + "]");
        assertThat(updateCount,is(1));

    }

    @Test
    public void testFindAccountsAsListByCustomerNo(){

        String accountNo = "8899000008075719";
        String customerNo = "8899000008075714";
        String type = "2";
        TInfoAccount tInfoAccount = getTInfoAccount(accountNo,customerNo,type);
        int insertCount = tInfoAccountMapper.insert(tInfoAccount);
        log.debug("insertCount=[" + insertCount + "]");

        String accountNo1 = "8899000008075715";
        TInfoAccount tInfoAccount1 = getTInfoAccount(accountNo1,customerNo,type);
        int insertCount1 = tInfoAccountMapper.insert(tInfoAccount1);
        log.debug("insertCount1=[" + insertCount1 + "]");
        assertThat(insertCount1,is(1));
        Date lastUpdateTime = new Date();
        List<TInfoAccount> tInfoAccountList = tInfoAccountMapper.findAccountsAsListByCustomerNo(customerNo,type);
        log.debug("size=[" + tInfoAccountList.size() + "]");
        for(TInfoAccount tmpTInfoAccount :tInfoAccountList){
            log.debug("accountNo=[" + tmpTInfoAccount.getAccountNo() + "]");
            assertThat(tmpTInfoAccount.getCustomerNo(),is(customerNo));
            assertThat(tmpTInfoAccount.getType(),is(type));
        }

    }

    @Test
    public void testFindAccountsAsListByCardNo(){
       //TODO 没有这个方法

    }

    public TInfoAccount getTInfoAccount(String accountNo, String accountName, String customerNo, Date openDate, Date closeDate, Date validDate, String txnPasswd, String initPasswd, String initPasswdModified, Long passwdErrNum, Date passwdErrLockTime, String status, String commStatus, String type, String grade, String realname,
                                        String apanage, String unitId, String areaCode, String cityCode, String openChannel, Long balance, Long lastDayBal, Long lastMonthBal, Long availableBalance, Long unavailableBalance, Long withdrawBalance, Long frozenAmount, Long preauthorizedAmount, String lastTxnDate, Long daySumConsAmt,
                                        Integer daySumConsCnt, Long daySumOutAmt, Integer daySumOutCnt, Long daySumInAmt, Integer daySumInCnt, Long daySumCashAmt, Integer daySumCashCnt, Long daySumChgAmt, Integer daySumChgCnt,
                                        Long monthSumConsAmt, Long monthSumOutAmt, Long monthSumInAmt, Long monthSumCashAmt, Long monthSumChgAmt, String allowedClose, String closeRtnCash, String encryptedMsg, Date lastUpdateTime, Date lastTxnTime, Long rsvdAmt1, Long rsvdAmt2, String rsvdText1, String rsvdText2, Long monthSumConsCnt,
                                        Long monthSumOutCnt, Long monthSumInCnt, Long monthSumCashCnt, Long monthSumChgCnt, String masterAccountNo, String forbiddenTxn, Long extendTimes, String forbiddenChannel, String cardNo, String cardType, String cardBindingMethod) {

        TInfoAccount tInfoAccount = new TInfoAccount();

        tInfoAccount.setAccountNo(accountNo);
        tInfoAccount.setAccountName(accountName);
        tInfoAccount.setCustomerNo(customerNo);
        tInfoAccount.setOpenDate(openDate);
        tInfoAccount.setCloseDate(closeDate);
        tInfoAccount.setExpiredDate(validDate);
        tInfoAccount.setPayPasswd(txnPasswd);
        tInfoAccount.setInitPasswd(initPasswd);
        tInfoAccount.setInitPasswdModified(initPasswdModified);
        tInfoAccount.setPasswdErrNum(passwdErrNum);
        tInfoAccount.setPasswdErrLockTime(passwdErrLockTime);
        tInfoAccount.setStatus(status);
        tInfoAccount.setCommStatus(commStatus);
        tInfoAccount.setType(type);
        tInfoAccount.setGrade(grade);
        tInfoAccount.setIsRealname(realname);
        tInfoAccount.setApanage(apanage);
        tInfoAccount.setOrganizationCode(unitId);
        tInfoAccount.setAreaCode(areaCode);
        tInfoAccount.setCityCode(cityCode);
        tInfoAccount.setOpenChannel(openChannel);
        tInfoAccount.setBalance(balance);
        tInfoAccount.setLastDayBal(lastDayBal);
        tInfoAccount.setLastMonthBal(lastMonthBal);
        tInfoAccount.setAvailableBalance(availableBalance);
        tInfoAccount.setUnavailableBalance(unavailableBalance);
        tInfoAccount.setWithdrawBalance(withdrawBalance);
        tInfoAccount.setFrozenAmount(frozenAmount);
        tInfoAccount.setPreauthorizedAmt(preauthorizedAmount);
        tInfoAccount.setLastTxnDate(lastTxnDate);
        tInfoAccount.setDaySumConsAmt(daySumConsAmt);
        tInfoAccount.setDaySumConsCnt(daySumConsCnt);
        tInfoAccount.setDaySumOutAmt(daySumOutAmt);
        tInfoAccount.setDaySumOutCnt(daySumOutCnt);
        tInfoAccount.setDaySumInAmt(daySumInAmt);
        tInfoAccount.setDaySumInCnt(daySumInCnt);
        tInfoAccount.setDaySumCashAmt(daySumCashAmt);
        tInfoAccount.setDaySumCashCnt(daySumCashCnt);
        tInfoAccount.setDaySumDepositAmt(daySumChgAmt);
        tInfoAccount.setDaySumDepositCnt(daySumChgCnt);
        tInfoAccount.setMonthSumConsAmt(monthSumConsAmt);
        tInfoAccount.setMonthSumOutAmt(monthSumOutAmt);
        tInfoAccount.setMonthSumInAmt(monthSumInAmt);
        tInfoAccount.setMonthSumCashAmt(monthSumCashAmt);
        tInfoAccount.setMonthSumChgAmt(monthSumChgAmt);
        tInfoAccount.setIsAllowedClose(allowedClose);
        tInfoAccount.setIsCloseRtnCash(closeRtnCash);
        tInfoAccount.setEncryptedMsg(encryptedMsg);
        tInfoAccount.setLastUpdateTime(lastUpdateTime);
        tInfoAccount.setLastTxnTime(lastTxnTime);
        tInfoAccount.setRsvdAmt1(rsvdAmt1);
        tInfoAccount.setRsvdAmt2(rsvdAmt2);
        tInfoAccount.setRsvdText1(rsvdText1);
        tInfoAccount.setRsvdText2(rsvdText2);
        tInfoAccount.setMonthSumConsCnt(monthSumConsCnt);
        tInfoAccount.setMonthSumOutCnt(monthSumOutCnt);
        tInfoAccount.setMonthSumInCnt(monthSumInCnt);
        tInfoAccount.setMonthSumCashCnt(monthSumCashCnt);
        tInfoAccount.setMonthSumChgCnt(monthSumChgCnt);
        tInfoAccount.setMasterAccountNo(masterAccountNo);
        tInfoAccount.setForbiddenTxn(forbiddenTxn);
        tInfoAccount.setExtendCount(extendTimes);
        tInfoAccount.setForbiddenChannel(forbiddenChannel);
        tInfoAccount.setCardNo(cardNo);
        tInfoAccount.setCardType(cardType);
        tInfoAccount.setCardBindingMethod(cardBindingMethod);
        return tInfoAccount;
    }

    public TInfoAccount getTInfoAccount(String accountNo,String customerNo,String type ){

//        String accountNo = "8899000008075714";
        String accountName = "我的天翼预付卡";
//        String customerNo = "8899000008075714";
        Date openDate = getDateFormatDate("yyyy/MM/dd");
        Date closeDate = getDateFormatDate("yyyy/MM/dd");
        Date validDate = getDateFormatDate("yyyy/MM/dd");
        String txnPasswd = "431C55403632B1F2";
        String initPasswd = "499977";
        String initPasswdModified = "0";
        Long passwdErrNum = new Long(0);
        Date passwdErrLockTime = new Date();
        String status =  "0";
        String commStatus = "1";
//        String type = "B";
        String grade = "1";
        String realname = "3";
        String apanage = "002110000010000";
        String unitId = "123";
        String areaCode = "999900";
        String cityCode = "999901";
        String openChannel = "00";
        Long balance = new Long(20000);
        Long lastDayBal = new Long(0);
        Long lastMonthBal = new Long(0);
        Long availableBalance = new Long(20000);
        Long unavailableBalance = new Long(0);
        Long withdrawBalance = new Long(0);
        Long frozenAmount = new Long(0);
        Long preauthorizedAmount = new Long(0);
        String lastTxnDate =  "20130318";
        Long daySumConsAmt = new Long(0);
        Integer daySumConsCnt = new Integer(0);
        Long daySumOutAmt = new Long(0);
        Integer daySumOutCnt = new Integer(0);
        Long daySumInAmt = new Long(0);
        Integer daySumInCnt = new Integer(0);
        Long daySumCashAmt = new Long(0);
        Integer daySumCashCnt = new Integer(0);
        Long daySumChgAmt = new Long(0);
        Integer daySumChgCnt = new Integer(0);
        Long monthSumConsAmt = new Long(0);
        Long monthSumOutAmt = new Long(0);
        Long monthSumInAmt = new Long(0);
        Long monthSumCashAmt = new Long(0);
        Long monthSumChgAmt = new Long(0);
        String allowedClose = "1";
        String closeRtnCash = "1";
        String encryptedMsg = "A73B68A6E896C1AFB95590ECF1DED4C1";
        Date lastUpdateTime = new Date();
        Date lastTxnTime = new Date();
        Long rsvdAmt1 = new Long(0);
        Long rsvdAmt2 = new Long(0);
        String rsvdText1 = "aaa";
        String rsvdText2 = "bbb";
        Long monthSumConsCnt = new Long(0);
        Long monthSumOutCnt = new Long(0);
        Long monthSumInCnt = new Long(0);
        Long monthSumCashCnt = new Long(0);
        Long monthSumChgCnt = new Long(0);
        String masterAccountNo = "123";
        String forbiddenTxn = "234";
        Long extendTimes = new Long(0);
        String forbiddenChannel = "abc";
        String cardNo = "777";
        String cardType = "1";
        String cardBindingMethod = "1";


        int oriDeleteCount =  tInfoAccountMapper.deleteByPrimaryKey(accountNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoAccount tInfoAccount = this.getTInfoAccount(accountNo, accountName, customerNo, openDate, closeDate, validDate, txnPasswd,  initPasswd, initPasswdModified,  passwdErrNum, passwdErrLockTime,status,commStatus,type,grade,realname,
                apanage, unitId, areaCode, cityCode, openChannel, balance, lastDayBal, lastMonthBal, availableBalance, unavailableBalance, withdrawBalance, frozenAmount,preauthorizedAmount, lastTxnDate, daySumConsAmt,
                daySumConsCnt, daySumOutAmt, daySumOutCnt, daySumInAmt, daySumInCnt, daySumCashAmt, daySumCashCnt, daySumChgAmt, daySumChgCnt,
                monthSumConsAmt, monthSumOutAmt, monthSumInAmt, monthSumCashAmt, monthSumChgAmt, allowedClose, closeRtnCash, encryptedMsg, lastUpdateTime, lastTxnTime, rsvdAmt1, rsvdAmt2, rsvdText1, rsvdText2, monthSumConsCnt,
                monthSumOutCnt, monthSumInCnt, monthSumCashCnt, monthSumChgCnt, masterAccountNo, forbiddenTxn, extendTimes, forbiddenChannel, cardNo, cardType,cardBindingMethod);
        return tInfoAccount;

    }

    public TInfoAccountCard getTInfoAccountCard(Long recordNo, String customerNo, String offlineAccountNo, String fundAccountNo,
                                                String cardNo, String cardType, String bindingFlag, String bindingMehod, Date bindingTime,
                                                Date unbindingTime, String bindingAcceptOrgCode, String bingdingAcceptUid, Date bindingAcceptTime, String unbindingAcceptOrgCode,
                                                String unbingdingAcceptUid, Date unbindingAcceptTime, String areaCode, String cityCode) {

        TInfoAccountCard tInfoAccountCard = new TInfoAccountCard();
        tInfoAccountCard.setRecordNo(recordNo);
        tInfoAccountCard.setCustomerNo(customerNo);
        tInfoAccountCard.setOfflineAccountNo(offlineAccountNo);
        tInfoAccountCard.setFundAccountNo(fundAccountNo);
        tInfoAccountCard.setCardNo(cardNo);
        tInfoAccountCard.setCardType(cardType);
        tInfoAccountCard.setBindingFlag(bindingFlag);
        tInfoAccountCard.setBindingMehod(bindingMehod);
        tInfoAccountCard.setBindingTime(bindingTime);
        tInfoAccountCard.setUnbindingTime(unbindingTime);
        tInfoAccountCard.setBindingAcceptOrgCode(bindingAcceptOrgCode);
        tInfoAccountCard.setBingdingAcceptUid(bingdingAcceptUid);
        tInfoAccountCard.setBindingAcceptTime(bindingAcceptTime);
        tInfoAccountCard.setUnbindingAcceptOrgCode(unbindingAcceptOrgCode);
        tInfoAccountCard.setUnbingdingAcceptUid(unbingdingAcceptUid);
        tInfoAccountCard.setUnbindingAcceptTime(unbindingAcceptTime);
        tInfoAccountCard.setAreaCode(areaCode);
        tInfoAccountCard.setCityCode(cityCode);

        return tInfoAccountCard;
    }

    public String getDateFormat(){

        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");

        return f.format(new Date());

    }

    public String getDateFormat(String format){

        SimpleDateFormat f = new SimpleDateFormat(format);

        return f.format(new Date());

    }

    public String getDateFormat(Date date,String format){

        SimpleDateFormat f = new SimpleDateFormat(format);

        return f.format(date);

    }

    public Date getDateFormatDate(String format){

        SimpleDateFormat f = new SimpleDateFormat(format);
        String  formatDate = f.format(new Date());
        try {
            return  f.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Date getNextDay(Date date,int nextDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, nextDate);
        date = calendar.getTime();
        return date;
    }


}
