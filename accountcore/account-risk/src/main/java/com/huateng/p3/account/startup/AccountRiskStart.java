package com.huateng.p3.account.startup;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.util.concurrent.AbstractIdleService;

public class AccountRiskStart extends AbstractIdleService {

    private static final Logger log = LoggerFactory.getLogger(AccountRiskStart.class);

    private ClassPathXmlApplicationContext context;

    public static void main(String[] args) {
        AccountRiskStart accountRisktart = new AccountRiskStart();
        accountRisktart.startAsync();
        try {
            Object lock = new Object();
            synchronized (lock) {
                while (true) {
                    lock.wait();
                }
            }
        } catch (InterruptedException ex) {
            log.error("ignore interruption");
        }
    }

    /**
     * Start the service.
     */
    @Override
    protected void startUp() throws Exception {
        context = new ClassPathXmlApplicationContext(
                new String[]{"account-risk.xml"});
        context.start();
        context.registerShutdownHook();
        log.info("service started successfully");
    }

    /**
     * Stop the service.
     */
    @Override
    protected void shutDown() throws Exception {
        context.stop();
        log.info("service stopped successfully");
    }
}
