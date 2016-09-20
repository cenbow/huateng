package com.huateng.p3.hub.dataprocess.interceptor;
/*package cn.com.bestpay.account.aop;

import java.io.PrintWriter;
import java.io.StringWriter;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import cn.com.bestpay.Response;
import cn.com.bestpay.account.model.BussErrorCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Aspect
public class LogInterceptorAlter {
	private  Logger logger= LoggerFactory.getLogger(LogInterceptorAlter.class);
	
	//com.bestpay.account.accountservice.impl.AccountAlertServiceImpl
	
	// @Before("execution(* *(..))")
		// 定义切点,匹配规则为(返回类型 方法(参数设置)

		//@Before("execution(* cn.com.bestpay.account.service.impl.*.*(..))")
		public void before() {
			//System.out.println("Before:*******************************************");
		}

		//@Around("execution(* com.tml.aopsample.service.impl.TmlService.*(..))")
		
		@Around("execution(* cn.com.bestpay.account.service.impl.*.*(..))")
		public Object runOnAround(ProceedingJoinPoint point) throws Throwable {
			System.out.println("Around:*******************************************");
			//logger.info("begin around");
			System.out.println(point.getSignature());
			StringBuffer buffer = new StringBuffer();
			String className = point.getTarget().getClass().toString();
			String methodName = point.getSignature().getName();
			buffer.append(className).append(".").append(methodName).append("(");
			Response object = null;
			
			try {
			    object = (Response)point.proceed();
			    if (object.isSuccess()){
			    	logger.info(buffer + "------" + object.isSuccess());
			    }else{
			    	logger.info(buffer + "------" + object.isSuccess()+ "------" +object.getErrorCode());
			    }
			    
			    
			    
			}  catch (DataIntegrityViolationException e) {
				
				object = new Response();
				if (e.toString().indexOf("ORA-00001") != -1) {
					object.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
				}else{
					object.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
				}
				object.setException(e);
				StringWriter w = new StringWriter();
				e.printStackTrace(new PrintWriter(w));
				logger.error(buffer + "------" + object.isSuccess()+ "------" +object.getErrorCode() + w.toString());
				return object;
			} catch (CannotAcquireLockException e) {
				object = new Response();
				object.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
				StringWriter w = new StringWriter();
				e.printStackTrace(new PrintWriter(w));
				logger.error(buffer + "------" + object.isSuccess()+ "------" +object.getErrorCode() + w.toString());
				return object;
			} catch (Exception e) {
				object = new Response();
				object.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
				StringWriter w = new StringWriter();
				e.printStackTrace(new PrintWriter(w));
				logger.error(buffer + "------" + object.isSuccess()+ "------" +object.getErrorCode() + w.toString());

				return object;
			}
			//logger.info("end around");
			return object;
		}

		// @After("execution(public * *.*(..))")
		//@After("execution(* cn.com.bestpay.account.service.impl.*.*(..))")
		// 定义切点,匹配规则为(范围 返回类型 返回类.方法(参数设置)
		public void after() {
			//System.out.println("After:....................");
		}

}
*/