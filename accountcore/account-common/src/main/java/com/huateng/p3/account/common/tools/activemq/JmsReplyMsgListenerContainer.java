/**
 * 
 */
package com.huateng.p3.account.common.tools.activemq;

import org.springframework.jms.listener.DefaultMessageListenerContainer;


/**
 * @author cmt
 *
 */
public class JmsReplyMsgListenerContainer extends DefaultMessageListenerContainer {

	//private String selector = "senderid='" + AppCode.INST_ID + "'";
	/**
	 * 
	 */
	public JmsReplyMsgListenerContainer() {
		super();
		//设置过滤器
		//setMessageSelector(selector);
	}

}
