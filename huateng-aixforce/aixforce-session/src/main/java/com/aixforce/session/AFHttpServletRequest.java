package com.aixforce.session;

import com.aixforce.session.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.*;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-05-21
 */
public class AFHttpServletRequest extends HttpServletRequestWrapper {
    private final static Logger log = LoggerFactory.getLogger(AFHttpServletRequest.class);

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    private String sessionCookieName;
    private String cookieDomain;
    private String cookieContextPath;
    private int maxInactiveInterval;
    private AFSessionManager sessionManager;
    private AFSession afSession;
    private int cookieMaxAge; //cookie的生存时间,以秒为单位, >0 表示多少秒后cookie过期, 0表示立即删除,<0 表示不持久存储cookie

    /**
     * Constructs a request object wrapping the given request.
     *
     * @throws IllegalArgumentException if the request is null
     */
    public AFHttpServletRequest(HttpServletRequest request, HttpServletResponse response) {
        super(request);
        this.request = request;
        this.response = response;
        sessionManager = AFSessionManager.instance();
    }

    /**
     * The default behavior of this method is to return getSession()
     * on the wrapped request object.
     */
    @Override
    public HttpSession getSession(boolean create) {
        return doGetSession(create);
    }

    /**
     * 获取会话实例，如果不存在则创建。
     *
     * @return 会话实例。
     */
    @Override
    public HttpSession getSession() {
        return doGetSession(true);
    }


    /**
     * 获取在Cookie中保存sessionID的属性名称.
     *
     * @return 属性名称.
     */
    public String getSessionCookieName() {
        return sessionCookieName;
    }

    /**
     * 设置在Cookie中保存sessionID的属性名称.
     *
     * @param sessionCookieName 属性名称。
     */
    public void setSessionCookieName(String sessionCookieName) {
        this.sessionCookieName = sessionCookieName;
    }

    /**
     * cookie的存放域。
     *
     * @return 存放域。
     */
    public String getCookieDomain() {
        return cookieDomain;
    }

    /**
     * 设置cookie的存放域。
     *
     * @param cookieDomain 存放域。
     */
    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    /**
     * Cookie的有效路径。
     *
     * @return 有效路径。
     */
    public String getCookieContextPath() {
        return cookieContextPath;
    }

    /**
     * 设置Session的Cookie有效路径。
     *
     * @param cookieContextPath 有效路径。
     */
    public void setCookieContextPath(String cookieContextPath) {
        this.cookieContextPath = cookieContextPath;
    }


    /**
     * 设置会话的最大不活动时间。单位秒。
     *
     * @param maxInactiveInterval 最大不活动时间。
     */
    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }



    public void setCookieMaxAge(int cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    /**
     * 获取当前的AFSession实例。
     *
     * @return　HttpSession实例。
     */
    public AFSession currentSession() {
        return afSession;
    }

    /**
     * 实际构造会话实例的方法。根据参数来决定如果当前没有绑定时是否进行创建。
     *
     * @param create true创建，false不创建。
     * @return 会话实例。
     */
    private HttpSession doGetSession(boolean create) {
        if (afSession == null) {
            Cookie cookie = WebUtil.findCookie(this, getSessionCookieName());
            if (cookie != null) {
                String value = cookie.getValue();
                log.debug("Find session`s id from cookie.[{}]", value);
                afSession = buildAFSession(value, false);
            } else {
                afSession = buildAFSession(create);
            }
        } else {
            log.debug("Session[{}] was existed.", afSession.getId());
        }
        return afSession;
    }

    /**
     * 根据指定的id构造一个新的会话实例。
     *
     * @param sessionId 会话id.
     * @param cookie    是否更新cookie值。true更新，false不更新。
     * @return 会话实例。
     */
    private AFSession buildAFSession(String sessionId, boolean cookie) {
        AFSession session = new AFSession(sessionManager, request, sessionId);
        session.setMaxInactiveInterval(maxInactiveInterval);
        if (cookie) {
            WebUtil.addCookie(this, response, getSessionCookieName(), sessionId,
                    getCookieDomain(), getCookieContextPath(), cookieMaxAge, true);
        }
        return session;
    }

    /**
     * 构造一个会话实例。如果create为false则返回null.
     *
     * @param create false方法调用返回null.
     * @return 会话实例。
     */
    private AFSession buildAFSession(boolean create) {
        if (create) {
            afSession = buildAFSession(sessionManager.getSessionIdGenerator().generateId(request), true);
            log.debug("Build new session[{}].", afSession.getId());
            return afSession;
        } else {
            return null;
        }
    }
}
