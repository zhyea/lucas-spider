package org.chobit.spider.process.src;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 * 请求来源信息
 *
 * @author robin
 */
public interface Source {


	/**
	 * 是否使用代理
	 *
	 * @return 是否使用代理
	 */
	default boolean useProxy() {
		return false;
	}


	/**
	 * 首页路径
	 *
	 * @return 首页路径
	 */
	String indexUrl();


	/**
	 * 编码类型
	 *
	 * @return 编码类型
	 */
	default Charset charset() {
		return StandardCharsets.UTF_8;
	}


	/**
	 * 默认根路径
	 *
	 * @return 根路径
	 */
	default String baseUrl() {
		return null;
	}


	/**
	 * 关键词黑名单，含有黑名单的词会被过滤掉
	 *
	 * @return 关键词黑名单
	 */
	default List<String> blacklist() {
		return new LinkedList<>();
	}
}
