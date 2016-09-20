package cn.com.huateng.web.controller;

import com.aixforce.common.utils.CommonConstants;
import com.aixforce.site.container.PageRender;
import com.aixforce.site.container.RenderConstants;
import com.aixforce.site.container.exception.NotFound404Exception;
import com.aixforce.site.container.exception.Server500Exception;
import com.aixforce.site.container.exception.UnAuthorize401Exception;
import com.aixforce.user.base.UserUtil;
import com.aixforce.user.base.exception.UserNotLoginException;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-09-18
 */
@Component
public class ViewRender {

    private static final Logger log = LoggerFactory.getLogger(ViewRender.class);

    @Autowired
    private PageRender pageRender;

    @Autowired
    private CommonConstants commonConstants;

    public String view(String domain, String path, HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> context, boolean rendHeadFoot) {

        if (request != null) {
            for (Object name : request.getParameterMap().keySet()) {
                context.put((String) name, request.getParameter((String) name));
            }
        }
        context.put(RenderConstants.USER, UserUtil.getCurrentUser());
        boolean isClassic = true;
        try {
            isClassic = pageRender.render(domain, path, context, true, rendHeadFoot);

        } catch (UserNotLoginException e) {

            try {
                response.sendRedirect("http://" + commonConstants.getMainSite() + "/login");
            } catch (IOException e1) {
                //ignore this fucking exception
            }
        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, NotFound404Exception.class);
            Throwables.propagateIfInstanceOf(e, Server500Exception.class);
            Throwables.propagateIfInstanceOf(e, UnAuthorize401Exception.class);
            log.error("render page(domain={},path={})failed,cause:{}", domain, path, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new Server500Exception(e.getMessage(), e);
        }
        if (isClassic) {
            return "resource:view";
        } else {
            try {
                response.getWriter().write((String) context.get(RenderConstants.HTML));
            } catch (IOException e) {
                // ignore it
            }
            return null;
        }
    }

}
