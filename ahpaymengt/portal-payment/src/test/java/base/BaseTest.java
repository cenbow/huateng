package base;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Resources;
import com.thoughtworks.xstream.XStream;
import junit.framework.TestSuite;

import java.io.IOException;
import java.net.URL;


public class BaseTest extends TestSuite {
    @SuppressWarnings("unchecked")
    protected <T> T mockEntity(String filePath, String alias, Class<T> clazz) {
        try {
            URL url = Resources.getResource(filePath);
            XStream stream = new XStream();
            if (!Strings.isNullOrEmpty(alias)) {
                stream.alias(alias, clazz);
            }
            return (T) stream.fromXML(Resources.toString(url, Charsets.UTF_8));

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
