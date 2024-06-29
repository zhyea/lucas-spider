package org.chobit.spider.tools;

import org.chobit.spider.tools.xml.NameSpaceVisitor;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xml处理辅助类
 *
 * @author robin
 */
public final class XmlHelper {


	private static final Logger logger = LoggerFactory.getLogger(XmlHelper.class);


	public static Document readXml(String url) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(url);
			document.accept(new NameSpaceVisitor());
			String xml = document.asXML();

			return DocumentHelper.parseText(xml);
		} catch (Exception e) {
			logger.error("read xml file error", e);
			return null;
		}
	}


	private XmlHelper() {
	}

}
