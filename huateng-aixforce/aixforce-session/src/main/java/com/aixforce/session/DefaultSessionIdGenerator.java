package com.aixforce.session;

import com.aixforce.session.util.WebUtil;
import com.google.common.hash.Hashing;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-05-21
 */
public class DefaultSessionIdGenerator implements SessionIdGenerator {

    public static final Character SEP='Z';

    private final String hostIpMd5;

    public DefaultSessionIdGenerator() {
        String hostIp;
        try {
            hostIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostIp = UUID.randomUUID().toString();
        }
        hostIpMd5 = Hashing.md5().hashString(hostIp).toString().substring(0,8);
    }

    /**
     * 生成唯一的sessionId
     *
     * 生成规则:
     * 0-3 md5(remoteIp)的前4位
     * 4:Z 分割符
     * 5-8 md5(thisIp)的前4位
     * 9:Z 分割符
     * and Long.toHexString(System.currentTimeMillis)
     * Z 分隔符
     * UUID 前4位
     * @param request request
     * @return sessionId
     */
    @Override
    public String generateId(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder(30);
        String remoteIpMd5 =  Hashing.md5().hashString(WebUtil.getClientIpAddr(request)).toString().substring(0,8);
        builder.append(remoteIpMd5).append(SEP).append(hostIpMd5)
                .append(SEP).append(Long.toHexString(System.currentTimeMillis()))
                .append(SEP).append(UUID.randomUUID().toString().substring(0,4));
        return builder.toString();
    }
}
