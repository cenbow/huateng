package com.huateng.p3.hub.accountcore.startup;

import com.google.common.util.concurrent.AbstractIdleService;
import com.huateng.p3.hub.accountcore.socket.MultiIntegralServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 下午12:01
 */
public class Bootstrap extends AbstractIdleService {

    private final static Logger log = LoggerFactory.getLogger(Bootstrap.class);

    private ClassPathXmlApplicationContext context;

    public static void main(String[] args) {
        log.info("Bootstrap start..................");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.startAsync();
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
        context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        context.start();
        context.registerShutdownHook();
        log.info("service started successfully");
        new MultiIntegralServer().service();
        log.info("integral server start successfully");
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
