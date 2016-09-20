package com.aixforce.web.handlebar;

import com.github.jknack.handlebars.Template;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-08-15
 */
public class HandlebarsView extends AbstractTemplateView {

    /**
     * The compiled template.
     */
    private Template template;

    /**
     * Merge model into the view. {@inheritDoc}
     */
    @Override
    protected void renderMergedTemplateModel(final Map<String, Object> model,
                                             final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        template.apply(model, response.getWriter());
    }

    /**
     * Set the compiled template.
     *
     * @param template The compiled template. Required.
     */
    void setTemplate(final Template template) {
        checkNotNull(template, "A handlebars template is required.");
        this.template = template;
    }
}
