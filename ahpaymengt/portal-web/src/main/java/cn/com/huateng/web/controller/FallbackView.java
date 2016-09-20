package cn.com.huateng.web.controller;

import com.aixforce.site.container.exception.NotFound404Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-09-18
 */
@Controller
public class FallbackView {

    @Autowired
    private ViewRender viewRender;

    /**
     * 二级域名页面渲染入口
     */
    @RequestMapping(method = RequestMethod.GET)
    public String path(@RequestHeader("Host") String domain,HttpServletRequest request, HttpServletResponse response, Map<String, Object> context) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if(path.startsWith("/api/")||path.startsWith("/design/")){
            throw new  NotFound404Exception();
        }
        // remove first "/"1
        return viewRender.view(domain, path.substring(1), request, response, context, true);
    }
}
