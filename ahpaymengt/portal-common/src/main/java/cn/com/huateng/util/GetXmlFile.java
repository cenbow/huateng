package cn.com.huateng.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.net.URL;
import java.util.List;

/**
 * User: 董培基
 * Date: 13-9-5
 * Time: 上午9:24
 */
public class GetXmlFile {


    private String fileName;
    private Document document;

    public GetXmlFile(String s) {
        fileName = "";
        document = null;
        URL url = GetXmlFile.class.getResource(s);
        if (url != null)
            fileName = GetXmlFile.class.getResource(s).toString();
        else
            fileName = s;
        try {
            SAXReader saxreader = new SAXReader();
            document = saxreader.read(fileName);
        } catch (DocumentException documentexception) {
            documentexception.printStackTrace();
        }
    }

    public GetXmlFile() {
        fileName = "";
        document = null;
    }

    public Document setXmlString(String s) {
        StringReader stringreader = new StringReader(s);
        InputSource inputsource = new InputSource(stringreader);
        SAXReader saxreader = new SAXReader();
        try {
            document = saxreader.read(inputsource);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return document;
    }

    public Document GetDocument() {
        return document;
    }

    public List GetListByXpath(String s) {
        return document.selectNodes(s);
    }

    public Node getElement(String s) {
        return document.selectSingleNode(s);
    }
}
