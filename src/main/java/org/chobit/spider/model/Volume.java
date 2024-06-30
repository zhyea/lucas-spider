package org.chobit.spider.model;

import org.chobit.spider.model.content.Content;

import java.util.LinkedList;
import java.util.List;

/**
 * 分卷信息
 *
 * @author robin
 */
public class Volume<E> {


	/**
	 * 卷名
	 */
	private String name;


	/**
	 * 章节列表
	 */
	private final List<Content<E>> chapters = new LinkedList<>();


	public Volume() {
	}

	public Volume(String name) {
		this.name = name;
	}

	public void addChapter(Content<E> chapter) {
		this.chapters.add(chapter);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Content<E>> getChapters() {
		return chapters;
	}

}
