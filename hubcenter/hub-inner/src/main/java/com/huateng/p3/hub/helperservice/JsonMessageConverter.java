package com.huateng.p3.hub.helperservice;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.huateng.p3.account.common.util.JsonMapper;

/**
 * 
 * @author huwenjie 
 * spring amqp默认的是jackson 的一个插件,目的将生产者生产的数据转换为json存入消息队列
 * 这里替换为fastjson的一个实现
 */
public class JsonMessageConverter extends AbstractMessageConverter {
	public static final String DEFAULT_CHARSET = "UTF-8";

	private volatile String defaultCharset = DEFAULT_CHARSET;

	public String getDefaultCharset() {
		return defaultCharset;
	}

	public void setDefaultCharset(String defaultCharset) {
		this.defaultCharset = (defaultCharset != null) ? defaultCharset: DEFAULT_CHARSET;

	}

	@Override
	protected Message createMessage(Object object,MessageProperties messageProperties) {
		byte[] bytes = null;
		String jsonString = JsonMapper.nonEmptyMapper().toJson(object);
		try {
			bytes = jsonString.getBytes(this.defaultCharset);
		} catch (UnsupportedEncodingException e) {
			throw new MessageConversionException("Failed to create Message "
					+ jsonString, e);
		}
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
		messageProperties.setContentEncoding(this.defaultCharset);
		if (bytes != null) {
			messageProperties.setContentLength(bytes.length);
		}
		return new Message(bytes, messageProperties);

	}

	public <T> T fromMessage(Message message, T t)
			throws MessageConversionException {
		String json = "";
		try {
			json = new String(message.getBody(), defaultCharset);
		} catch (UnsupportedEncodingException e) {
			throw new MessageConversionException("Failed to from Message "
					+ new String(message.getBody()), e);
		}
		return (T) JsonMapper.nonEmptyMapper().fromJson(json, t.getClass());

	}

	@Override
	/**
	 * 使用另一个方法实现反序列化不同对象,此方法不使用
	 */
	public Object fromMessage(Message message)
			throws MessageConversionException {
		return null;
	}

}
