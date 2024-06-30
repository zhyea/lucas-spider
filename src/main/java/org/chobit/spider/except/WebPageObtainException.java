package org.chobit.spider.except;

/**
 * 读取html失败
 *
 * @author robin
 */
public class WebPageObtainException extends RuntimeException {


	public WebPageObtainException(String url) {
		super("Obtain web page failed, url:" + url);
	}


}
