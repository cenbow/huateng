/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.handlebars;

import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-25
 */
public class HandlebarsView extends AbstractTemplateView {

    /**
     * The compiled template.
     */
    private HandlebarEngine handlebarEngine;
    private String path;

    /**
     * Merge model into the view. {@inheritDoc}
     */
    @Override
    protected void renderMergedTemplateModel(final Map<String, Object> model,
                                             final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        response.getWriter().write(handlebarEngine.naiveExec(null, path, model, false));
    }

    /**
     * init the view.
     */
    void init(final HandlebarEngine handlebarEngine, final String path) {
        checkNotNull(handlebarEngine, "A handlebarEngine is required.");
        checkNotNull(path, "A path is required.");
        this.handlebarEngine = handlebarEngine;
        this.path = path;
    }
}
