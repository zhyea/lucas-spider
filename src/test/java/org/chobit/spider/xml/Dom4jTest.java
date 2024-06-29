package org.chobit.spider.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.junit.Test;

import java.util.List;

import static org.chobit.spider.tools.XmlHelper.readXml;

/**
 * @author robin
 */
public class Dom4jTest {


    @Test
    public void read() throws DocumentException {
        String url = "http://localhost:82/toc.ncx";

        Document document = readXml(url);
        /*document.accept(new NameSpaceCleaner());
        document.asXML();*/


        System.out.println("Root element :" + document.getRootElement().getName());

        List<Node> nodes = document.selectNodes("//navPoint");
        for (Node node : nodes) {
            System.out.println("Current Element :" + node.selectSingleNode("content").valueOf("@src"));
            System.out.println("Current Element :" + node.selectSingleNode("navLabel/text").getText());
        }
    }



}
