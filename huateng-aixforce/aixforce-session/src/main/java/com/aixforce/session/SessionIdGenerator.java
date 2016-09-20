package com.aixforce.session;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-05-21
 */
public interface SessionIdGenerator {
    /**
     * 生成唯一的sessionId
     * @param request request
     * @return sessionId
     */
    String generateId(HttpServletRequest request);

}
