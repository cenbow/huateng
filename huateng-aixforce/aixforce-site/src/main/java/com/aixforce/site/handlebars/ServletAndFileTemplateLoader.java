package com.aixforce.site.handlebars;

import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.ServletContextTemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;

import javax.servlet.ServletContext;
import java.io.IOException;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-8
 */
public class ServletAndFileTemplateLoader extends ServletContextTemplateLoader {
    private FileTemplateLoader fileTemplateLoader;

    public ServletAndFileTemplateLoader(ServletContext servletContext, String prefix, String suffix, String baseDir) {
        super(servletContext, prefix, suffix);
        this.fileTemplateLoader = new IndexFileTemplateLoader(baseDir);
    }

    @Override
    public TemplateSource sourceAt(String uri) throws IOException {
        notEmpty(uri, "The uri is required.");
        uri = normalize(uri);
        // if uri have `resource:` prefix, handle with self
        if (uri.startsWith("resource:")) {
            return super.sourceAt(uri.substring(9));
        } else { // else use fileTemplateLoader
            return fileTemplateLoader.sourceAt(uri);
        }
    }
}
