package com.aixforce.site.handlebars;

import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import com.github.jknack.handlebars.io.URLTemplateSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-8
 */
public class IndexFileTemplateLoader extends FileTemplateLoader {
    public IndexFileTemplateLoader(String basedir) {
        super(basedir);
    }

    @Override
    public TemplateSource sourceAt(String uri) throws IOException {
        // 先找自己 找不到找index
        notEmpty(uri, "The uri is required.");
        String location = resolve(normalize(uri));
        URL resource = getResource(location);
        if (resource != null) {
            return new URLTemplateSource(location, resource);
        }
        location = resolve(normalize(uri + "/index"));
        resource = getResource(location);
        if (resource == null) {
            throw new FileNotFoundException(location);
        }
        return new URLTemplateSource(location, resource);
    }
}
