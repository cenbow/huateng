package com.huateng.p3.account.common.tools.activemq;

import javax.jms.Message;
import javax.jms.MessageListener;

import lombok.extern.slf4j.Slf4j;


/**
 * @author cmt
 *
 */
@Slf4j
public class JmsClientRecvListener implements MessageListener {
	

	/**
	 * client templeate
	 */
	private IClientSendMessage clientrecv;

	public IClientSendMessage getClientrecv() {
		return clientrecv;
	}

	public void setClientrecv(IClientSendMessage c) {
		this.clientrecv =c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
	
		try {
			clientrecv.recvmessagefromserver(message);

		} catch (Exception e) {
			log.error("",e);

		}

	}
}
