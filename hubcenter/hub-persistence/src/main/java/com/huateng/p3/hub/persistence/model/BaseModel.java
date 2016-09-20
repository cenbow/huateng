package com.huateng.p3.hub.persistence.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import lombok.Data;

/**
 * @author cmt
 * @version 2014-1-15 上午12:20:11
 */

public class BaseModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Long intTxnSeq;

	public String toString() {

		StringBuffer buffer = new StringBuffer();
		Class<? extends BaseModel> clazz = this.getClass();
		String simpleName = clazz.getSimpleName();
		buffer.append(simpleName);
		buffer.append("{");
		//
		Field[] fs = clazz.getDeclaredFields();
		Class<?> ftype = null;
		String fname = null;
		Object fvalue = null;
		for (Field f : fs) {
			ftype = f.getType();
			fname = f.getName();
			f.setAccessible(true);
			try {
				fvalue = f.get(this);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch ( IllegalAccessException e){
                throw new RuntimeException(e);
            }
			// 是否是基本数据类型
			if ((ftype.isPrimitive() || ftype == Integer.class
					|| ftype == Long.class || ftype == Short.class
					|| ftype == Boolean.class || ftype == Character.class
					|| ftype == Double.class || ftype == Float.class || ftype == String.class)
					&& !Modifier.isStatic(f.getModifiers())) {
				buffer.append(fname);
				buffer.append(":");
				buffer.append(fvalue);
				buffer.append(",");
			}
		}
		//
		buffer.append("}");
		return buffer.toString();

	}

}
