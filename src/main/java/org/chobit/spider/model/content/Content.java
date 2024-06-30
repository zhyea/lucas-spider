package org.chobit.spider.model.content;

import java.util.LinkedList;
import java.util.List;

/**
 * 网页内容
 *
 * @author robin
 */
public class Content<E> {


	/**
	 * 标题名
	 */
	private String title;


	/**
	 * 卷名
	 */
	private String volumeName;


	/**
	 * 章节名
	 */
	private String chapterName;


	/**
	 * 内容
	 */
	private final List<E> lines = new LinkedList<>();


	/**
	 * 内容原始网页
	 */
	private String contentHtml;


	public Content() {
	}


	public Content(String title) {
		this.title = title;
	}


	public void addLine(E line) {
		this.lines.add(line);
	}


	public E firstLine() {
		if (lines.isEmpty()) {
			return null;
		}
		return lines.get(0);
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getVolumeName() {
		return volumeName;
	}


	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}


	public String getChapterName() {
		return chapterName;
	}


	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}


	public List<E> getLines() {
		return lines;
	}


	public String getContentHtml() {
		return contentHtml;
	}


	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

}
