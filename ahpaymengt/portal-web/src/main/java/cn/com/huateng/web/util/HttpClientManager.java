package cn.com.huateng.web.util;

import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: 董培基
 * Date: 13-9-17
 * Time: 上午10:26
 */
public class HttpClientManager {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 日志对象
     */

    private static Logger logger = LoggerFactory.getLogger(HttpClientManager.class);

    static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

    static {
        /*** 构造连接池 */
        connectionManager.setMaxConnectionsPerHost(20);
        // 每个主机的最大并行链接数，默认为2 当前是20
        connectionManager.setMaxTotalConnections(30);
        // maxTotalConnections 客户端总并行链接最大数，默认为20 当前是30
    }

    public static MultiThreadedHttpConnectionManager getConnectionManager() {
        return connectionManager;
    }

}
