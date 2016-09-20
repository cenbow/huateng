/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.web.misc;

import com.aixforce.exception.JsonResponseException;
import com.google.common.base.Objects;
import com.google.common.net.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-28
 */
public class AixForceExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        if (isAjaxRequest(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try {
                PrintWriter out = response.getWriter();
                if (ex instanceof JsonResponseException) {
                    JsonResponseException jsonEx = (JsonResponseException) ex;
                    response.setStatus(jsonEx.getStatus());
                    out.print(ex.getMessage());
                    out.close();
                    return new ModelAndView();
                } else if(ex instanceof BindException){
                    BindException bindException = (BindException)ex;
                    response.setStatus(400);
                    BindingResult result = bindException.getBindingResult();
                    if (result.hasErrors()) {
                        out.print(result.getFieldError().getDefaultMessage());
                    }
                    out.close();
                    return new ModelAndView();
                }else{
                    return null;
                }
            } catch (IOException e) {
                //ignore
            }
        }
        return null;
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return Objects.equal(request.getHeader(HttpHeaders.X_REQUESTED_WITH), "XMLHttpRequest");

    }
}
