package org.chobit.spider.process.sink;

import org.chobit.spider.model.PageContent;
import org.chobit.spider.model.Volume;

import java.util.LinkedList;
import java.util.List;

/**
 * 用来实现内容输出
 *
 * @author robin
 */
public interface Sink {


	/**
	 * 数据输出
	 *
	 * @param contents 内容
	 */
	void sink(List<PageContent> contents);


	/**
	 * 整理目录
	 *
	 * @param postList 内容
	 * @return 整理好的结果
	 */
	default List<Volume> tidyCatalog(List<PageContent> postList) {
		List<Volume> result = new LinkedList<>();
		Volume tmp = null;
		for (PageContent pc : postList) {
			// 首次需要
			boolean needNew = (null == tmp);
			// 切换新的卷需要
			needNew = needNew || (null != pc.getVolumeName() && !pc.getVolumeName().equals(tmp.getName()));
			if (needNew) {
				tmp = new Volume(pc.getVolumeName());
				result.add(tmp);
			}

			tmp.addChapter(pc);
		}

		return result;
	}

}
