package cn.com.huateng.web.controller.api.card;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-08-06
 */
@Component
public class CardErrorCode {

    private final Properties errorCodes;

    public CardErrorCode() throws IOException {
        errorCodes = new Properties();
        errorCodes.load(Resources.newReaderSupplier(Resources.getResource("card_error_code.properties"), Charsets.UTF_8).getInput());
    }

    public String getProperty(String key) {
        return errorCodes.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return errorCodes.getProperty(key, defaultValue);
    }
}
