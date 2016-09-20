package cn.com.huateng.payment.service.impl;

import cn.com.huateng.account.service.SeqGeneratorService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.common.FeeType;
import cn.com.huateng.payment.dao.TPortOrderBaseMapper;
import cn.com.huateng.payment.manage.TPortOrderBaseManage;
import cn.com.huateng.payment.manage.TTransferOrderManage;
import cn.com.huateng.payment.manage.TTransferRecordManage;
import cn.com.huateng.payment.model.TPortOrderBase;
import cn.com.huateng.payment.model.TTransferOrder;
import cn.com.huateng.payment.model.TTransferRecord;
import cn.com.huateng.payment.service.AccountTransferService;
import cn.com.huateng.payment.util.PaymentInfoLogCom;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubAccountTransferService;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: lzw
 * Date: 14-10-29
 * Time: 下午1:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AccountTransferServiceImpl implements AccountTransferService {

    private static final Logger logger = LoggerFactory.getLogger(AccountTransferServiceImpl.class);
    @Autowired
    HubAccountTransferService hubAccountTransferService;

    @Autowired
    PaymentInfoLogCom paymentInfoLogCom;

    @Autowired
    private TPortOrderBaseManage tPortOrderBaseManage;

    @Autowired
    private SeqGeneratorService seqGeneratorService;

    @Autowired
    private TTransferOrderManage tTransferOrderManage;

    @Autowired
    private TTransferRecordManage tTransferRecordManage;


    @Override
    public Response<TxnResultObject> transfer(String unifyId,String amount,String transAmount,String transPhone,String field,String pwd) {
        Response<TxnResultObject> response = new Response<TxnResultObject>();

        TTransferOrder tTransferOrder=new  TTransferOrder();
        TTransferRecord tTransferRecord=new TTransferRecord();
        if(unifyId.equals(transPhone)){
            response.setErrorCode(BussErrorCode.ERROR_CODE_500208.getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_500208.getErrordesc());
            return response;
        }
        try {
            int amountD=(int)(Double.parseDouble(amount)*100);
            Long amountL= Long.parseLong(amountD+"");
            int transAmountD=(int)(Double.parseDouble(transAmount)*100);
            Long transAmountL=   Long.parseLong(transAmountD+"");
            if(transAmountL>amountL || amountL<=0){
                response.setErrorCode(BussErrorCode.ERROR_CODE_700402.getErrorcode());
                response.setErrorMsg(BussErrorCode.ERROR_CODE_700402.getErrordesc());
                return response;
            }
            String orderSeq=seqGeneratorService.TransferOrderSeq("core.t_transfer_order");
            PaymentInfo paymentInfo=paymentInfoLogCom.getPaymentInfo();
            paymentInfo.setInnerTxnType("121010");
            paymentInfo.setAmount(transAmountL);
            paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
            paymentInfo.setTxnType(TxnType.TXN_TRANSFER);
            paymentInfo.setAcceptTxnSeqNo(orderSeq);
            paymentInfo.setTxnDscpt("转账");
            paymentInfo.setMerchantCode("222222222222222");
            paymentInfo.setPayOrgCode("");

            AccountInfo accountInfo=new   AccountInfo();
            accountInfo.setAccountKey(unifyId);
            accountInfo.setKeyType(KeyType.UNIFY);
            accountInfo.setTargetAccountKey(transPhone);
            accountInfo.setTargetKeyType(KeyType.UNIFY);
            String txnPwd= Hashing.md5().hashString(pwd, Charsets.UTF_8).toString();
            accountInfo.setTxnPassword(txnPwd);










            response = hubAccountTransferService.transfer(paymentInfo, accountInfo);




            if (response.isSuccess()) {
                logger.info("transfer success TDictCode:{}  ", response.toString());

                Long id=seqGeneratorService.generateOrderNo("core.t_transfer_record_id");
                tTransferRecord.setOrderseq(orderSeq);
                tTransferRecord.setAmount(transAmountL);
                tTransferRecord.setPayee(transPhone);
                tTransferRecord.setId(id.toString());



                tTransferOrder.setOrderseq(orderSeq);
                tTransferOrder.setAmount(transAmountL);
                tTransferOrder.setPayer(unifyId);
                tTransferOrder.setRemark("转账");
                tTransferOrder.setBlackFlag("2");//1已申请  2成功  3失败
                tTransferOrder.setIsSendMsg("0");    //0不发送  1发送
                tTransferOrder.setCreateTime(new Date());
                tTransferOrder.setTransactionTime(new Date());



                tTransferOrderManage.insertTransferOrder(tTransferOrder);
                tTransferRecordManage.insertTransferRecord(tTransferRecord);




                return response;
            } else {

                logger.error("fail to transfer cause by {}", response.getErrorCode() + ":" + response.getErrorMsg());

                return response;
            }
        } catch (Exception e) {

            logger.error("fail to transfer cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return response;
        }
    }

    @Override
    public Response<TxnResultObject> transferCheck(String unifyId,String amount,String transAmount,String transPhone,String field) {
        Response<TxnResultObject> response = new Response<TxnResultObject>();
if(unifyId.equals(transPhone)){
    response.setErrorCode(BussErrorCode.ERROR_CODE_500208.getErrorcode());
    response.setErrorMsg(BussErrorCode.ERROR_CODE_500208.getErrordesc());
    return response;
}
        try {
            int amountD=(int)(Double.parseDouble(amount)*100);
            Long amountL= Long.parseLong(amountD+"");
            int transAmountD=(int)(Double.parseDouble(transAmount)*100);
            Long transAmountL=   Long.parseLong(transAmountD+"");
            if(transAmountL>amountL || amountL<=0){
                response.setErrorCode(BussErrorCode.ERROR_CODE_700402.getErrorcode());
                response.setErrorMsg(BussErrorCode.ERROR_CODE_700402.getErrordesc());
                return response;
            }

            PaymentInfo paymentInfo=paymentInfoLogCom.getPaymentInfo();
            paymentInfo.setInnerTxnType("121010");
            paymentInfo.setAmount(transAmountL);
            paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
            paymentInfo.setTxnType(TxnType.TXN_TRANSFER);
            paymentInfo.setAcceptTxnSeqNo("");
            paymentInfo.setTxnDscpt("转账");
            paymentInfo.setMerchantCode("222222222222222");
            paymentInfo.setPayOrgCode("");

            AccountInfo accountInfo=new   AccountInfo();
            accountInfo.setAccountKey(unifyId);
            accountInfo.setKeyType(KeyType.UNIFY);
            accountInfo.setTargetAccountKey(transPhone);
            accountInfo.setTargetKeyType(KeyType.UNIFY);

            response = hubAccountTransferService.transferCheck(paymentInfo, accountInfo);
            if (response.isSuccess()) {
                logger.info("transferCheck success TDictCode:{}  ", response.toString());
                return response;
            } else {
                logger.error("fail to transferCheck cause by {}", response.getErrorCode() + ":" + response.getErrorMsg());
                return response;
            }
        } catch (Exception e) {
            logger.error("fail to transferCheck cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return response;
        }
    }

}