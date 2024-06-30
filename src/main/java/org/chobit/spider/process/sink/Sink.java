package org.chobit.spider.process.sink;

import org.chobit.spider.model.content.Content;
import org.chobit.spider.model.Volume;

import java.util.LinkedList;
import java.util.List;

/**
 * 用来实现内容输出
 *
 * @author robin
 */
public interface Sink<E>{


	/**
	 * 数据输出
	 *
	 * @param contents 内容
	 */
	void sink(List<Content<E>> contents);


	/**
	 * 整理目录
	 *
	 * @param postList 内容
	 * @return 整理好的结果
	 */
	default List<Volume<E>> tidyCatalog(List<Content<E>> postList) {
		List<Volume<E>> result = new LinkedList<>();
		Volume<E> tmp = null;
		for (Content<E> content : postList) {
			// 首次需要
			boolean needNew = (null == tmp);
			// 切换新的卷需要
			needNew = needNew || (null != content.getVolumeName() && !content.getVolumeName().equals(tmp.getName()));
			if (needNew) {
				tmp = new Volume<>(content.getVolumeName());
				result.add(tmp);
			}

			tmp.addChapter(content);
		}

		return result;
	}

}
