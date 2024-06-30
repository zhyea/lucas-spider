package org.chobit.spider.except;

import org.chobit.spider.model.content.Image;

/**
 * 文件保存异常
 *
 * @author robin
 */
public class ImageSaveException extends RuntimeException {


	public ImageSaveException(Image img, Exception e) {
		super("Save msg failed, img url:" + img, e);
	}

}
