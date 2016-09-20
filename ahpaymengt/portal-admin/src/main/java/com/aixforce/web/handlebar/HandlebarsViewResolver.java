package com.aixforce.web.handlebar;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ServletContextTemplateLoader;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-25
 */
public class HandlebarsViewResolver extends AbstractTemplateViewResolver {

    /**
     * The default content type.
     */
    public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=UTF-8";

    /**
     * The handlebars instance.
     */
    private Handlebars handlebars;

    /**
     * Creates a new {@link com.aixforce.web.handlebar.HandlebarsViewResolver}.
     *
     * @param handlebars The handlebars object. Required.
     */
    public HandlebarsViewResolver(final Handlebars handlebars) {
        checkNotNull(handlebars, "The handlebars object is required.");

        this.handlebars = handlebars;
        setViewClass(HandlebarsView.class);
        setContentType(DEFAULT_CONTENT_TYPE);
    }

    @Override
    public void setPrefix(final String prefix) {
        throw new UnsupportedOperationException("Use "
                + ServletContextTemplateLoader.class.getName() + "#setPrefix");
    }

    @Override
    public void setSuffix(final String suffix) {
        throw new UnsupportedOperationException("Use "
                + ServletContextTemplateLoader.class.getName() + "#setSuffix");
    }

    /**
     * Creates a new {@link com.aixforce.web.handlebar.HandlebarsViewResolver}.
     */
    public HandlebarsViewResolver() {
        this(new Handlebars());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractUrlBasedView buildView(final String viewName)
            throws Exception {
        return configure((HandlebarsView) super.buildView(viewName));
    }

    /**
     * Configure the handlebars view.
     *
     * @param view The handlebars view.
     * @return The configured view.
     * @throws java.io.IOException If a resource cannot be loaded.
     */
    protected AbstractUrlBasedView configure(final HandlebarsView view)
            throws IOException {
        String url = view.getUrl();
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        Template template = handlebars.compile(url);
        view.setTemplate(template);
        return view;
    }

    /**
     * The required view class.
     *
     * @return The required view class.
     */
    @Override
    protected Class<?> requiredViewClass() {
        return HandlebarsView.class;
    }

}

