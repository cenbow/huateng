package cn.com.huateng.payment.util;

import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.enummodel.TxnType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: lizhongwei
 * Date: 14-11-8
 * Time: 下午3:18
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PaymentInfoLogCom {
    private static  final Logger log = LoggerFactory.getLogger(PaymentInfoLogCom.class);
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat
            .forPattern("yyyyMMddHHmmss");


    @Value("#{app.centerOrgCode}")
    private  String orgCode;

    @Value("#{app.terminalNo}")
    private  String terminalNo;

    @Value("#{app.txnChannel}")
    private  String txnChannel;

    @Value("#{app.portalOrgCode}")
    private  String portalOrgCode;
    public PaymentInfo getPaymentInfo(){
        String inputTime = DATE_TIME_FORMAT.print(new Date().getTime());
        PaymentInfo paymentInfo=new PaymentInfo();


        paymentInfo.setTerminalNo(terminalNo);
        paymentInfo.setAcceptOrgCode(orgCode);
        paymentInfo.setAcceptDate(inputTime.substring(0, 8));
        paymentInfo.setAcceptTime(inputTime.substring(8));
        paymentInfo.setAcceptOperatorNo(null);
        paymentInfo.setChannel(txnChannel);
        paymentInfo.setPayOrgCode("");

        paymentInfo.setSupplyOrgCode(portalOrgCode);

        return paymentInfo;
    }
}
