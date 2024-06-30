package org.chobit.spider.model.content;

/**
 * 图片信息
 *
 * @author robin
 */
public class Image {


	/**
	 * 图片标题
	 */
	private String title;

	/**
	 * 图片地址
	 */
	private String src;

	/**
	 * 图片扩展名
	 */
	private String ext;



	public Image(String title, String src) {
		this.title = title;
		this.src = src;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	@Override
	public String toString() {
		return "Image{" +
				"title='" + title + '\'' +
				", src='" + src + '\'' +
				", ext='" + ext + '\'' +
				'}';
	}
}
