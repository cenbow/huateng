package com.huateng.p3.account.service;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.component.Response;

/**
* Created with IntelliJ IDEA.
* User: huwenjie
* Date: 13-12-27
* Time: 上午9:33
* o change this template use File | Settings | File Templates.
*/
public class AccountChargeServiceTest extends BaseAccountServiceSpringTest {


    private Logger logger = LoggerFactory.getLogger(AccountChargeServiceTest.class);

    @Autowired
    AccountChargeService accountChargeService;

    @Test
    public void testAll() throws Exception {

        testChargeOk();//充值正确的参数消费
       // testCancelChargeOk();//充值撤销正确的参数消费
    	//testRollbackChargeOk();//充值冲正正确的参数消费
         // testRollbackChargeCancelOk();//充值冲正正确的参数消费

    }



   public void testChargeOk() throws Exception {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setMerchantCode("222222222222222");
        paymentInfo.setTerminalNo("00431000");
        paymentInfo.setAcceptOrgCode("001999999020000");//受理机构
//      paymentInfo.setAcceptOrgCode("004310000040000");//受理机构
        paymentInfo.setAmount(100l);
        paymentInfo.setAcceptDate("20131219");
        paymentInfo.setAcceptTime("164649");
        paymentInfo.setAcceptTxnSeqNo("2013121800094769");//须每次都修改
        paymentInfo.setBussinessType("121010");
        paymentInfo.setChannel("02");
        paymentInfo.setTerminalSeqNo("131218006900456");
        paymentInfo.setTxnDscpt("充值");
        paymentInfo.setSupplyOrgCode("111310049001138");//商户
        AccountInfo accountInfo = new AccountInfo();
//      accountInfo.setAccountKey("18018354882");
        accountInfo.setAccountKey("13524124277");
        accountInfo.setAccountKey("18018354882");
        accountInfo.setKeyType(KeyType.UNIFY);
        Response<TxnResultObject> actual = accountChargeService.charge(paymentInfo, accountInfo);
        logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
    }

   public void testCancelChargeOk() throws Exception {
       PaymentInfo paymentInfo = new PaymentInfo();
       paymentInfo.setMerchantCode("222222222222222");
       paymentInfo.setTerminalNo("00431000");
       paymentInfo.setAcceptOrgCode("004310000040000");//受理机构
       paymentInfo.setInnerTxnType("121010");
       paymentInfo.setAmount(100l);
       paymentInfo.setAcceptDate("20131219");
       paymentInfo.setAcceptTime("164650");
       paymentInfo.setAcceptOperatorNo(null);
       paymentInfo.setAcceptTxnSeqNo("2013121800094768");//需每次修改
       paymentInfo.setBussinessType("121010");
       paymentInfo.setChannel("02");
       paymentInfo.setPayOrgCode("");
       paymentInfo.setTerminalSeqNo("131218006900456");
       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
       paymentInfo.setTxnType(TxnType.TXN_CHARGE);
       paymentInfo.setTxnDscpt("充值撤销");
       paymentInfo.setSupplyOrgCode("111310049001138");//商户


       PaymentInfo oldPaymentInfo = new PaymentInfo();
       oldPaymentInfo.setAcceptOrgCode("001999999020000");
       oldPaymentInfo.setAcceptDate("20131219");
       oldPaymentInfo.setAcceptTime("164649");
       oldPaymentInfo.setAcceptTxnSeqNo("2013121800094769");//每次都修改，必须存在


       AccountInfo accountInfo = new AccountInfo();
       accountInfo.setAccountKey("18018354882");
       accountInfo.setKeyType(KeyType.UNIFY);
       Response<TxnResultObject> actual = accountChargeService.cancelCharge(paymentInfo, oldPaymentInfo, accountInfo);
       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));
       Assert.assertEquals(true, actual.isSuccess());
       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
   }

   public void testRollbackChargeOk() throws Exception {
       PaymentInfo paymentInfo = new PaymentInfo();
       paymentInfo.setMerchantCode("222222222222222");
       paymentInfo.setTerminalNo("00431000");
       paymentInfo.setAcceptOrgCode("004310000040000");//受理机构
       paymentInfo.setInnerTxnType("121011");
       paymentInfo.setAmount(100l);
       paymentInfo.setAcceptDate("20131219");
       paymentInfo.setAcceptTime("164650");
       paymentInfo.setAcceptOperatorNo(null);
       paymentInfo.setAcceptTxnSeqNo("2013121800094770");//每次需修改
       paymentInfo.setBussinessType("121011");
       paymentInfo.setChannel("08");
       paymentInfo.setPayOrgCode("");
       paymentInfo.setTerminalSeqNo("131218006900456");
       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
       paymentInfo.setTxnType(TxnType.TXN_CHARGE);
       paymentInfo.setTxnDscpt("充值冲正");
       paymentInfo.setSupplyOrgCode("111310049001138");//商户
       PaymentInfo oldPaymentInfo = new PaymentInfo();
       oldPaymentInfo.setAcceptOrgCode("001999999020000");
       oldPaymentInfo.setAcceptDate("20131219");
       oldPaymentInfo.setAcceptTime("164649");
       oldPaymentInfo.setAcceptTxnSeqNo("2013121800094769"); //原订单的请求序列号
       oldPaymentInfo.setMerchantCode("222222222222222");
       oldPaymentInfo.setTerminalNo("00431000");
       oldPaymentInfo.setInnerTxnType("121010");
       oldPaymentInfo.setAmount(1l);
       oldPaymentInfo.setAcceptOperatorNo(null);
       oldPaymentInfo.setBussinessType("121010");
       oldPaymentInfo.setChannel("08");
       oldPaymentInfo.setPayOrgCode("");
       oldPaymentInfo.setTerminalSeqNo("131218006900456");
       oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
       oldPaymentInfo.setTxnType(TxnType.TXN_CHARGE);
       oldPaymentInfo.setTxnDscpt("充值");
       oldPaymentInfo.setSupplyOrgCode("111310049001138");



       AccountInfo accountInfo = new AccountInfo();
       accountInfo.setAccountKey("18018354882");
       accountInfo.setKeyType(KeyType.UNIFY);
       Response<TxnResultObject> actual = accountChargeService.rollbackCharge(paymentInfo, oldPaymentInfo, accountInfo);
       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));
       Assert.assertEquals(true, actual.isSuccess());
       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
   }

   public void testRollbackChargeCancelOk() throws Exception {
//	   PaymentInfo paymentInfo = new PaymentInfo();
//       paymentInfo.setMerchantCode("222222222222222");
//       paymentInfo.setTerminalNo("");
//       paymentInfo.setAcceptOrgCode("002330000000000");//受理机构
//       paymentInfo.setInnerTxnType("");
//       paymentInfo.setAmount(1l);
//       paymentInfo.setAcceptDate("20140529");
//       paymentInfo.setAcceptTime("152008");
//       paymentInfo.setAcceptOperatorNo(null);
//       paymentInfo.setAcceptTxnSeqNo("012014052604999999");//每次需修改
//       paymentInfo.setBussinessType("121021");
//       paymentInfo.setChannel("07");
//       paymentInfo.setPayOrgCode("001999900000000");
//       paymentInfo.setTerminalSeqNo("012014052604999999");
//       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
//       paymentInfo.setTxnType(TxnType.TXN_CHARGE);
//       paymentInfo.setTxnDscpt("老网关商户资金账户省平台充值撤销冲正");
//       paymentInfo.setSupplyOrgCode("113410000000000");//商户
//
//       PaymentInfo oldPaymentInfo = new PaymentInfo();
//       oldPaymentInfo.setAcceptOrgCode("002330000000000");
//       oldPaymentInfo.setAcceptDate("20140529");
//       oldPaymentInfo.setAcceptTime("152008");
//       oldPaymentInfo.setAcceptTxnSeqNo("1405291520082624251"); //原订单的请求序列号
//       oldPaymentInfo.setMerchantCode("222222222222222");
//       oldPaymentInfo.setTerminalNo("");
//       oldPaymentInfo.setInnerTxnType("121021");
//       oldPaymentInfo.setAmount(1l);
//       oldPaymentInfo.setAcceptOperatorNo(null);
//       oldPaymentInfo.setBussinessType("121021");
//       oldPaymentInfo.setChannel("07");
//       oldPaymentInfo.setPayOrgCode("001999900000000");
//       oldPaymentInfo.setTerminalSeqNo("1405291520082624251");
//       oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
//       oldPaymentInfo.setTxnType(TxnType.TXN_CHARGE);
//       oldPaymentInfo.setTxnDscpt("老网关商户资金账户省平台充值撤销");
//       oldPaymentInfo.setSupplyOrgCode("113410000000000");
	   PaymentInfo paymentInfo = new PaymentInfo();
       paymentInfo.setMerchantCode("222222222222222");
       paymentInfo.setTerminalNo("");
       paymentInfo.setAcceptOrgCode("004310000040000");//受理机构
       paymentInfo.setInnerTxnType("");
       paymentInfo.setAmount(1l);
       paymentInfo.setAcceptDate("20140528");
       paymentInfo.setAcceptTime("205710");
       paymentInfo.setAcceptOperatorNo(null);
       paymentInfo.setAcceptTxnSeqNo("012014052604998888");//每次需修改
       paymentInfo.setBussinessType("121021");
       paymentInfo.setChannel("07");
       paymentInfo.setPayOrgCode("001999900000000");
       paymentInfo.setTerminalSeqNo("012014052604999999");
       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
       paymentInfo.setTxnType(TxnType.TXN_CHARGE);
       paymentInfo.setTxnDscpt("充值撤销冲正");
       paymentInfo.setSupplyOrgCode("111310049001138");//商户

       PaymentInfo oldPaymentInfo = new PaymentInfo();
       oldPaymentInfo.setAcceptOrgCode("004310000040000");
       oldPaymentInfo.setAcceptDate("20131216");
       oldPaymentInfo.setAcceptTime("164650");
       oldPaymentInfo.setAcceptTxnSeqNo("2013122600094721"); //原订单的请求序列号
       oldPaymentInfo.setMerchantCode("222222222222222");
       oldPaymentInfo.setTerminalNo("");
       oldPaymentInfo.setInnerTxnType("121021");
       oldPaymentInfo.setAmount(1l);
       oldPaymentInfo.setAcceptOperatorNo(null);
       oldPaymentInfo.setBussinessType("121021");
       oldPaymentInfo.setChannel("07");
       oldPaymentInfo.setPayOrgCode("001999900000000");
       oldPaymentInfo.setTerminalSeqNo("2013122600094721");
       oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
       oldPaymentInfo.setTxnType(TxnType.TXN_CHARGE);
       oldPaymentInfo.setTxnDscpt("充值撤销");
       oldPaymentInfo.setSupplyOrgCode("111310049001138");
//       PaymentInfo paymentInfo = new PaymentInfo();
//       paymentInfo.setMerchantCode("222222222222222");
//       paymentInfo.setTerminalNo("00431000");
//       paymentInfo.setAcceptOrgCode("004310000040000");//受理机构
//       paymentInfo.setInnerTxnType("121011");
//       paymentInfo.setAmount(100l);
//       paymentInfo.setAcceptDate("20131219");
//       paymentInfo.setAcceptTime("164650");
//       paymentInfo.setAcceptOperatorNo(null);
//       paymentInfo.setAcceptTxnSeqNo("2013121800094770");//每次需修改
//       paymentInfo.setBussinessType("121011");
//       paymentInfo.setChannel("08");
//       paymentInfo.setPayOrgCode("");
//       paymentInfo.setTerminalSeqNo("131218006900456");
//       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
//       paymentInfo.setTxnType(TxnType.TXN_CHARGE);
//       paymentInfo.setTxnDscpt("充值冲正");
//       paymentInfo.setSupplyOrgCode("111310049001138");//商户
//
//       PaymentInfo oldPaymentInfo = new PaymentInfo();
//       oldPaymentInfo.setAcceptOrgCode("004310000040000");
//       oldPaymentInfo.setAcceptDate("20131219");
//       oldPaymentInfo.setAcceptTime("164649");
//       oldPaymentInfo.setAcceptTxnSeqNo("2013121800094769"); //原订单的请求序列号
//       oldPaymentInfo.setMerchantCode("222222222222222");
//       oldPaymentInfo.setTerminalNo("00431000");
//       oldPaymentInfo.setInnerTxnType("121010");
//       oldPaymentInfo.setAmount(1l);
//       oldPaymentInfo.setAcceptOperatorNo(null);
//       oldPaymentInfo.setBussinessType("121010");
//       oldPaymentInfo.setChannel("08");
//       oldPaymentInfo.setPayOrgCode("");
//       oldPaymentInfo.setTerminalSeqNo("131218006900456");
//       oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//       oldPaymentInfo.setTxnType(TxnType.TXN_CHARGE);
//       oldPaymentInfo.setTxnDscpt("充值");
//       oldPaymentInfo.setSupplyOrgCode("111310049001138");

       AccountInfo accountInfo = new AccountInfo();
       accountInfo.setAccountKey("13524124277");
       //accountInfo.setAccountKey("18018354882");
       accountInfo.setKeyType(KeyType.UNIFY);
       Response<TxnResultObject> actual = accountChargeService.rollbackChargeCancel(paymentInfo, oldPaymentInfo, accountInfo);
       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));
       Assert.assertEquals(true, actual.isSuccess());
       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
   }

}

