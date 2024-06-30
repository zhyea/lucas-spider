package org.chobit.spider.process.transform;

import org.chobit.spider.model.Anchor;
import org.chobit.spider.model.content.Content;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * 网页内容读取
 *
 * @author robin
 */
public interface Transformer<E> {


	/**
	 * 解析链接信息
	 *
	 * @param docIndex 目录页对象
	 * @param baseUrl  默认根路径
	 * @return 链接信息
	 */
	List<Anchor> postAnchors(Document docIndex, String baseUrl);


	/**
	 * 提取文章内容
	 *
	 * @param docPost 文章页对象
	 * @param title   标题
	 * @param parent  父级标签
	 * @return 文章内容
	 */
	Content<E> extract(Document docPost, String title, String parent);


	/**
	 * 正文路径选择器
	 *
	 * @return 正文路径选择器
	 */
	String postUrlSelector();


	/**
	 * 正文标题选择器
	 *
	 * @return 正文标题选择器
	 */
	String postTitleSelector();


	/**
	 * 正文内容选择器
	 *
	 * @return 正文内容选择器
	 */
	String postContentSelector();

}
