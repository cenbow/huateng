package com.huateng.p3.account.component;

import com.huateng.p3.account.common.tools.activemq.AppCode;
import com.huateng.p3.account.common.tools.activemq.IClientSendMessage;
import com.huateng.p3.account.common.tools.activemq.RecvMessageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * User: dongpeiji
 * Date: 14-9-14
 * Time: 下午2:59
 */
@Slf4j
@Component("activeMqTemplateComponent")
public class ActiveMqTemplateComponent implements IClientSendMessage {
    private JmsTemplate template;
    private JmsTemplate jmsTemplate4async;
    private Destination destination;
    private Destination respDest;
    private Map<String, Destination> destinationMap;
    private Map<String, Destination> respDestMap;
    /** 以报文中通讯流水为主键的一个线程安全hashmap */
    private static ConcurrentHashMap<String, RecvMessageBean> excuterthreadsmap = new ConcurrentHashMap<String, RecvMessageBean>();

    /**
     * 无返回的消息
     *
     * @see com.huateng.p3.account.common.tools.activemq.IClientSendMessage#aSyncSendMsg(java.lang.String,
     *      java.lang.String, java.lang.Object, java.lang.String[])
     */
    public void aSyncSendMsg(final String seq, final String reqQueueName,
                             final Object context, final String... expand) {
        destination = destinationMap.get(reqQueueName);
        if (destination == null) {
            destination = destinationMap.get("9999");
        }
        jmsTemplate4async.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage msg = session.createObjectMessage();
                msg.setStringProperty("reqTxnSeq", seq);
                msg.setBooleanProperty("isaSync", true);
                msg.setObject((Serializable)context);
                return msg;
            }
        });
    }

    /**
     * 发送信息并获取返回信息 (non-Javadoc)
     *
     * @see com.huateng.p3.account.common.tools.activemq.IClientSendMessage#sendMsg(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String[])
     */
    public Message sendMsg(final String reqTxnSeq, final String reqQueueName,
                           final String rcvQueueName, final String context,
                           final String... expand) {
        destination = destinationMap.get(reqQueueName);
        respDest = respDestMap.get(rcvQueueName);
        if (destination == null || respDest == null) {
            destination = destinationMap.get("9999");
            respDest = respDestMap.get("9999");
        }

        RecvMessageBean temprecv = new RecvMessageBean();
        RecvMessageBean exTempRecv = excuterthreadsmap.putIfAbsent(reqTxnSeq,
                temprecv);
        RecvMessageBean tempresult = null;
        // 如果已存在，就打个警告
        if (exTempRecv != null) {
            log.error("{} key duplicated", reqTxnSeq);
        }
        boolean acqureresult = false;
        try {
            template.send(destination, new MessageCreator() {
                public Message createMessage(Session session)
                        throws JMSException {
                    Message msg = session.createObjectMessage(context);
                    msg.setJMSReplyTo(respDest);
                    msg.setStringProperty("senderid", AppCode.INST_ID);
                    msg.setStringProperty("reqTxnSeq", reqTxnSeq);
                    msg.setBooleanProperty("isaSync", false);
                    return msg;
                }
            });

            log.debug("message sended to:{},reqTxnSeq:{},context:{}",
                    new Object[] { destination.toString(), reqTxnSeq, context });
            // 挂在该资源上，等待N秒内有回文
            acqureresult = temprecv.getRecvsemap().tryAcquire(
                    template.getReceiveTimeout(), TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            log.error("message sended reqTxnSeq:" + reqTxnSeq, e);
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            log.error("message sended reqTxnSeq:" + reqTxnSeq, e);
            throw new RuntimeException(e);
        }finally {
            // 从hashmap里拿到收到的消息
            tempresult = excuterthreadsmap.remove(reqTxnSeq);
        }

        if (acqureresult && tempresult != null) {
            Message msg = tempresult.getRecvmsg();
            if (msg != null) {
                log.debug(
                        "succeed to receive message from:{},reqTxnSeq:{},returnMsg:{}",
                        new Object[] { respDest.toString(), reqTxnSeq, msg });
                return msg;
            } else {
                log.error(
                        "receive message time out! return null, reqTxnSeq:{}",
                        reqTxnSeq);
                return null;
                // throw new RuntimeException("receive message time out");
            }
        } else {
            log.error(
                    "receive message time out! return null, reqTxnSeq:{},map size is:{}",
                    new Object[] { reqTxnSeq, excuterthreadsmap.size()});
            // throw new RuntimeException("receive message time out");
            return null;
        }
    }

    /**
     * 发送信息并获取返回文字信息 (non-Javadoc)
     *
     * @see com.huateng.p3.account.common.tools.activemq.IClientSendMessage#sendTextRtnMessage(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String[])
     */
    public String sendTextRtnMessage(final String seq,
                                     final String reqQueueName, final String rcvQueueName,
                                     final String context, final String... expand) {

        TextMessage msg = (TextMessage) sendMsg(seq, reqQueueName,
                rcvQueueName, context);
        String resp = null;
        try {
            if (msg != null) {
                resp = msg.getText();
            }
        } catch (Exception e) {
            log.error("get text msg from return message result error : {}", e);
            throw new RuntimeException("", e);
        }
        return resp;
    }

    /**
     * @return the template
     */
    public JmsTemplate getTemplate() {
        return template;
    }

    /**
     * @param template
     *            the template to set
     */
    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    /**
     * @return the destination
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * @param destination
     *            the destination to set
     */
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    /**
     * @return the respDest
     */
    public Destination getRespDest() {
        return respDest;
    }

    /**
     * @param respDest
     *            the respDest to set
     */
    public void setRespDest(Destination respDest) {
        this.respDest = respDest;
    }

    public void recvmessagefromserver(Message recvmessage) {
        if (recvmessage == null) {
            log.info(" recvmessagefromserver error");
            return;
        }
		/* 得到通讯报文中包号 */

        String key;
        try {
            key = recvmessage.getStringProperty("reqTxnSeq");
            RecvMessageBean temprecv = excuterthreadsmap.get(key);

            if (temprecv != null) {
                temprecv.setRecvmsg(recvmessage);
                temprecv.getRecvsemap().release();
            } else {
                log.info(" map size is [{}] , recvmessagefromserver key [{}]: is null ", new Object[] { excuterthreadsmap.size(),key});
            }
        } catch (JMSException e) {
            log.error("", e);
        }
        // 挂在资源上的消息放入，同时唤醒工作线程
    }

    /**
     * @return the jmsTemplate4async
     */
    public JmsTemplate getJmsTemplate4async() {
        return jmsTemplate4async;
    }

    /**
     * @param jmsTemplate4async
     *            the jmsTemplate4async to set
     */
    public void setJmsTemplate4async(JmsTemplate jmsTemplate4async) {
        this.jmsTemplate4async = jmsTemplate4async;
    }

    public Map<String, Destination> getDestinationMap() {
        return destinationMap;
    }

    public void setDestinationMap(Map<String, Destination> destinationMap) {
        this.destinationMap = destinationMap;
    }

    public Map<String, Destination> getRespDestMap() {
        return respDestMap;
    }

    public void setRespDestMap(Map<String, Destination> respDestMap) {
        this.respDestMap = respDestMap;
    }
}
