package cn.com.huateng.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Author: 韩纳威
 * Date: 13-11-21
 * Time: 上午10:57
 */
public class ExceptionInterceptor implements HandlerExceptionResolver {

    private final static Logger log = LoggerFactory.getLogger(ExceptionInterceptor.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        log.error(ex.getMessage(), ex);
        return null;
    }
}
