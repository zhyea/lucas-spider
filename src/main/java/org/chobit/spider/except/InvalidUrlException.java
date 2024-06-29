package org.chobit.spider.except;

/**
 * 无效链接异常
 *
 * @author robin
 */
public class InvalidUrlException extends RuntimeException {

	public InvalidUrlException(String url) {
		super("url [" + url + "] is invalid.");
	}
}
