/**
 * 
 */
package com.huateng.p3.hub.dataprocess.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;




/**
 * 记录日志（拦截器）
 * 
 * @author cmt
 * 
 * 效率太低不建议使用
 * 
 */

//@Component("LogAroundIntercepter")
//@Aspect
//@Slf4j
public class LogAroundIntercepter {
	private static final Logger log = LoggerFactory.getLogger(LogAroundIntercepter.class);
	

	@Around("((execution(* *..*service.add*(..)) or execution(* *..*service.del*(..)) or execution(* *..*service.modify*(..))) and !bean(LogAroundIntercepter))")
	public Object doAround(ProceedingJoinPoint pjp) throws Exception {

		StringBuilder sb = new StringBuilder();
		//类名
		String  className = pjp.getSignature().getName();
		// 方法名
		String methodName = pjp.getTarget().getClass().toString();
		// 操作参数
		Object[] objArgs = pjp.getArgs();
		
		sb.append("#调用#");
		sb.append("[className:" + className);
		sb.append(",methodName:" + methodName+"]");
		
		for (Object arg : objArgs) {
			Object  obj = arg;
			if (obj.getClass().isArray()) {
				Object[] beans = (Object[]) obj;
				for (Object o : beans) {
					sb.append("{"+o2String(o))	;
					sb.append("}");
				}
			}else {
				sb.append("{"+o2String(obj)+"}");
			}
		}
		
		log.info(sb.toString());
		
		// 调用目标对象的方法
		Object ret = null;
	    sb = new StringBuilder();
		sb.append("#返回#");
		try {
			ret = pjp.proceed();
			if(ret != null){
				if (ret.getClass().isArray()) {
					Object[] beans = (Object[]) ret;
					for (Object o : beans) {
						sb.append("{"+o2String(o))	;
						sb.append("}");
					}
				}else {
					sb.append("{"+o2String(ret)+"}");
				}
				
			}
			log.info(sb.toString());
		} catch (Throwable e) {
			log.error("", e);
			throw new Exception("错误妈", e);
		} finally {
			log.info("");
			// 入库操作 //TODO
		}
		return ret;
	}
	
	
	/**
	 * 遇到异常操作
	 * 
	 * @author cmt
	 */
	@AfterThrowing(pointcut = "((execution(* *..*service.add*(..)) or execution(* *..*service.del*(..)) or execution(* *..*service.modify*(..))) and !bean(LogAroundIntercepter))", throwing = "ex")
	public void exception(JoinPoint joinPoint,
			Exception ex) {
		
		
		StringBuilder sb = new StringBuilder();
		//类名
		String  className = joinPoint.getSignature().getName();
		// 方法名
		String methodName = joinPoint.getTarget().getClass().toString();
		// 操作参数
		Object[] objArgs = joinPoint.getArgs();
		sb.append("#Exception#");
		sb.append("[className:" + className);
		sb.append(",methodName:" + methodName+"]");
		
		for (Object arg : objArgs) {
			Object  obj = arg;
			if (obj.getClass().isArray()) {
				Object[] beans = (Object[]) obj;
				for (Object o : beans) {
					sb.append("{"+o2String(o))	;
					sb.append("}");
				}
			}else {
				sb.append("{"+o2String(obj)+"}");
			}
		}

		log.info(sb.toString(),ex);

	}
	
	
	

	private  String o2String(Object o) {
		
		StringBuffer buffer = new StringBuffer();
		Class<?> clazz = o.getClass();
		buffer.append("(");
		Field[] fs = clazz.getDeclaredFields();
		Class<?> ftype = null ;
		String fname = null ;
		Object fvalue = null ;
		for(Field f : fs){
			ftype = f.getType();
			fname = f.getName();
			f.setAccessible(true);
			try {
				fvalue = f.get(o);
			}  catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch ( IllegalAccessException e){
                throw new RuntimeException(e);
            }
			//是否是基本数据类型
			if((ftype.isPrimitive()
					||ftype == Integer.class
					||ftype == Long.class
					||ftype == Short.class
					||ftype == Boolean.class
					||ftype == Character.class
					||ftype == Double.class
					||ftype == Float.class
					||ftype == String.class)
					&& !Modifier.isStatic(f.getModifiers())
					){
				buffer.append(fname);
				buffer.append(":");
				buffer.append(fvalue);
				buffer.append(",");
			}
		}
		//
			buffer.append(")");
			return buffer.toString();

		}
	
	
	
	

	
	
}
