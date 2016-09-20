package com.aixforce.session;

import com.aixforce.session.util.WebUtil;
import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-05-21
 */
public class AFSessionFilter implements Filter {
    private final static Logger log = LoggerFactory.getLogger(AFSessionFilter.class);

    private final AFSessionManager sessionManager;
    private String sessionCookieName;
    private int maxInactiveInterval;
    private String cookieDomain;
    private String cookieContextPath;
    private int cookieMaxAge;

    public AFSessionFilter() {
        this.sessionManager = AFSessionManager.instance();
    }

    /**
     * Called by the web container to indicate to a filter that it is
     * being placed into service.
     * <p/>
     * <p>The servlet container calls the init
     * method exactly once after instantiating the filter. The init
     * method must complete successfully before the filter is asked to do any
     * filtering work.
     * <p/>
     * <p>The web container cannot place the filter into service if the init
     * method either
     * <ol>
     * <li>Throws a ServletException
     * <li>Does not return within a time period defined by the web container
     * </ol>
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            initParameters(filterConfig);
        } catch (Exception ex) {
            log.error("failed to init cache session filter", ex);
            throw new ServletException(ex);
        }
    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the
     * container each time a request/response pair is passed through the
     * chain due to a client request for a resource at the end of the chain.
     * The FilterChain passed in to this method allows the Filter to pass
     * on the request and response to the next entity in the chain.
     * <p/>
     * <p>A typical implementation of this method would follow the following
     * pattern:
     * <ol>
     * <li>Examine the request
     * <li>Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering
     * <li>Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering
     * <li>
     * <ul>
     * <li><strong>Either</strong> invoke the next entity in the chain
     * using the FilterChain object
     * (<code>chain.doFilter()</code>),
     * <li><strong>or</strong> not pass on the request/response pair to
     * the next entity in the filter chain to
     * block the request processing
     * </ul>
     * <li>Directly set headers on the response after invocation of the
     * next entity in the filter chain.
     * </ol>
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof AFHttpServletRequest) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        AFHttpServletRequest afRequest = new AFHttpServletRequest(httpRequest, httpResponse);
        afRequest.setSessionCookieName(sessionCookieName);
        afRequest.setMaxInactiveInterval(maxInactiveInterval);
        afRequest.setCookieDomain(cookieDomain);
        afRequest.setCookieContextPath(cookieContextPath);
        afRequest.setCookieMaxAge(cookieMaxAge);

        chain.doFilter(afRequest, response);
        AFSession session = afRequest.currentSession();
        if (session != null) {
            if(!session.isValid()){ //if invalidate , delete login cookie
                log.debug("delete login cookie");
                WebUtil.failureCookie(httpRequest, httpResponse, sessionCookieName, cookieDomain, cookieContextPath);
            }else if (session.isDirty()) {//should flush to db
                if (log.isDebugEnabled()) {
                    log.debug("try to flush session to session store");
                }
                Map<String, Object> snapshot = session.snapshot();
                if (sessionManager.save(session.getId(), snapshot, maxInactiveInterval)) {
                    if (log.isDebugEnabled()) {
                        log.debug("succeed to flush session {} to store, key is:{}", snapshot, session.getId());
                    }
                } else {
                    log.error("failed to save session to redis");
                    WebUtil.failureCookie(httpRequest, httpResponse, sessionCookieName, cookieDomain, cookieContextPath);
                }

            } else {//refresh expire time
                sessionManager.refreshExpireTime(session, maxInactiveInterval);
            }
        }
    }

    /**
     * Called by the web container to indicate to a filter that it is being
     * taken out of service.
     * <p/>
     * <p>This method is only called once all threads within the filter's
     * doFilter method have exited or after a timeout period has passed.
     * After the web container calls this method, it will not call the
     * doFilter method again on this instance of the filter.
     * <p/>
     * <p>This method gives the filter an opportunity to clean up any
     * resources that are being held (for example, memory, file handles,
     * threads) and make sure that any persistent state is synchronized
     * with the filter's current state in memory.
     */
    @Override
    public void destroy() {
        sessionManager.destroy();
    }

    /**
     * 初始化。
     */
    private void initParameters(FilterConfig filterConfig)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        String sessionCookieNameParameter = "sessionCookieName";
        String maxInactiveIntervalParameter = "maxInactiveInterval";
        String cookieDomainParameter = "cookieDomain";
        String cookieContextPathParameter = "cookieContextPath";

        String temp = filterConfig.getInitParameter(sessionCookieNameParameter);
        sessionCookieName = (temp == null) ? "afsid" : temp;

        temp = filterConfig.getInitParameter(maxInactiveIntervalParameter);
        maxInactiveInterval = (temp == null) ? 30 * 60 : Integer.valueOf(temp);

        cookieDomain = filterConfig.getInitParameter(cookieDomainParameter);

        temp = filterConfig.getInitParameter(cookieContextPathParameter);
        cookieContextPath = (temp == null) ? "/" : temp;

        cookieMaxAge = Integer.parseInt(Objects.firstNonNull(filterConfig.getInitParameter("cookieMaxAge"),"-1"));

        log.info("CacheSessionFilter (sessionCookieName={},maxInactiveInterval={},cookieDomain={})", sessionCookieName,
                maxInactiveInterval,
                cookieDomain);
    }
}
