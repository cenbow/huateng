package com.huateng.p3.account.service.aopcomponent;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huateng.p3.account.common.bizparammodel.PaymentInfo;

/**
 * User: JamesTang
 * Date: 13-12-23
 * Time: 上午11:28
 */
@Aspect
@Service
public class AccountTxnAopComponent {
    @Autowired
    private CurrenCyInvestNotifyCompnent currenCyInvestNotifyCompnent;


    Logger log = LoggerFactory.getLogger(AccountTxnAopComponent.class);

    @Pointcut("execution(* com.bestpay.account.service.*.*(..))")
    private void accountTxnCutMethod() {
    }

    //声明前置通知
    @Before("accountTxnCutMethod()")
    public void doBefore() {
        log.debug("前置通知");
    }

    //声明后置通知
    @AfterReturning(pointcut = "accountTxnCutMethod()", returning = "result")
    public void doAfterReturning(String result) {
        log.debug("后置通知");
        log.debug("---" + result + "---");
    }

    //声明例外通知
    @AfterThrowing(pointcut = "accountTxnCutMethod()", throwing = "e")
    public void doAfterThrowing(Exception e) {
        log.debug("例外通知  Exception  is {}", e.getMessage());
    }

    //声明最终通知
    @After("accountTxnCutMethod()")
    public void doAfter() {
        log.debug("最终通知");
    }

    //声明环绕通知
    @Around("accountTxnCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("进入方法---环绕通知");
        Object o = pjp.proceed();
        try {
            Object[] paras = pjp.getArgs();
            if (paras.length > 0 && paras[0] instanceof PaymentInfo) {
                PaymentInfo paymentInfo = (PaymentInfo) paras[0];
                currenCyInvestNotifyCompnent.InvestNotify(paymentInfo, o);
            }
        } finally {
            log.debug("退出方法---环绕通知");
            return o;
        }
    }
}
