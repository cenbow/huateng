package cn.com.huateng.mall.job;

import cn.com.huateng.mall.service.ItemServiceProxyImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-08-29
 */
@Component
public class ItemSearchDumper {
    private final static Logger log = LoggerFactory.getLogger(ItemSearchDumper.class);

    private final ItemServiceProxyImpl itemServiceProxy;

    @Autowired
    public ItemSearchDumper(ItemServiceProxyImpl itemServiceProxy) {
        this.itemServiceProxy = itemServiceProxy;
    }

    /**
     * run every 15 minutes;
     */
    @Scheduled(cron = "0 0/15 * * * ?")  //每隔15分钟触发一次
    public void deltaDump(){
        log.info("begin to delta dump item");
        this.itemServiceProxy.deltaDump(15);
        log.info("finish delta dump item");
    }

    /**
     * run every midnight
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void fullDump(){
        log.info("begin to full dump item");
        this.itemServiceProxy.fullDump();
        log.info("finish full dump item");
    }
}
