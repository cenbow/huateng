package com.aixforce.site.handlebars;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-9-3
 */
@Component
public class LinnerHelper {
    @Autowired
    private HandlebarEngine handlebarEngine;

    private Map<String, String> assertPaths;

    @PostConstruct
    public void init() throws IOException {
        File manifest = new File(handlebarEngine.getNuwaHome() + "/../manifest.yml");
        if (manifest.exists()) {
            String fileContent = Files.toString(manifest, Charset.defaultCharset());
            //noinspection unchecked
            assertPaths = new Yaml().loadAs(fileContent, Map.class);
        }

        handlebarEngine.registerHelper("fillPath", new Helper<String>() {
            @Override
            public CharSequence apply(String assertPath, Options options) throws IOException {
                if (assertPaths != null && assertPaths.get(assertPath) != null) {
                    return assertPaths.get(assertPath);
                }
                return assertPath;
            }
        });
    }
}
