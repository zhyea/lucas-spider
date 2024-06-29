package org.chobit.spider.tools.xml;

import org.dom4j.*;
import org.dom4j.tree.DefaultElement;

/**
 * 名称空间visitor
 *
 * @author robin
 */
public class NameSpaceVisitor extends VisitorSupport {


	private static final String XML_NS = "xmlns";

	private static final String XSI_PREFIX = "xsi:";


	@Override
	public void visit(Document document) {
		((DefaultElement) document.getRootElement()).setNamespace(Namespace.NO_NAMESPACE);
		document.getRootElement().additionalNamespaces().clear();
	}


	@Override
	public void visit(Namespace namespace) {
		namespace.detach();
	}


	@Override
	public void visit(Attribute node) {
		if (node.toString().contains(XML_NS) || node.toString().contains(XSI_PREFIX)) {
			node.detach();
		}
	}


	@Override
	public void visit(Element node) {
		if (node instanceof DefaultElement) {
			((DefaultElement) node).setNamespace(Namespace.NO_NAMESPACE);
		}
	}
}