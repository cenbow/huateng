package com.huateng.p3.account.common.tools.activemq;

import javax.jms.Message;

/**
 * @author cmt
 * 
 */
public interface IClientSendMessage {

	public void recvmessagefromserver(Message recvmessage);

	void aSyncSendMsg(final String seq, final String reqQueueName, final Object context,
                      final String... expand);

	Message sendMsg(final String seq, final String reqQueueName, final String rcvQueueName, final String context, final String... expand)
			;

	String sendTextRtnMessage(final String seq, final String reqQueueName, final String rcvQueueName, final String context,
                              final String... expand) ;
}
