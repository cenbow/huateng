package com.huateng.p3.account.risk.risklisener;

import com.huateng.p3.account.common.tools.activemq.IClientSendMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huateng.p3.account.common.bizparammodel.RiskCustomerTxnDataMergeInfo;
import com.huateng.p3.account.risk.RiskMergeService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * User: JamesTang
 * Date: 13-12-12
 * Time: 下午2:26
 */
@Slf4j
@Component
public class QueueRiskLisener implements MessageListener {

    @Autowired
    RiskMergeService riskMergeService;

    //Logger log = LoggerFactory.getLogger(QueueRiskLisener.class);

    /**
     * client templeate
     */
    private IClientSendMessage clientrecv;

    public IClientSendMessage getClientrecv() {
        return clientrecv;
    }

    public void setClientrecv(IClientSendMessage c) {
        this.clientrecv = c;
    }

    @Override
    public void onMessage(Message message) {
        RiskCustomerTxnDataMergeInfo riskCustomerTxnDataMergeInfo = new RiskCustomerTxnDataMergeInfo();
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage objMessage = (ObjectMessage) message;
                Object obj = objMessage.getObject();
                riskCustomerTxnDataMergeInfo = (RiskCustomerTxnDataMergeInfo) obj;
                log.info("接收到一个ObjectMessage，包含RiskCustomerTxnDataMergeInfo对象,参数{}", riskCustomerTxnDataMergeInfo);
                riskMergeService.accountRiskMerge(riskCustomerTxnDataMergeInfo);
                clientrecv.recvmessagefromserver(message);
            }
        } catch (JMSException e) {
            log.error("jms exception {}", e);
        } catch (Exception ex) {
            log.error("error onMessage data :" + riskCustomerTxnDataMergeInfo.toString(), ex);
        }
        log.debug(" data :" + riskCustomerTxnDataMergeInfo.getAccountNo());
    }
}

