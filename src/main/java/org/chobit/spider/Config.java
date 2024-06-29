package org.chobit.spider;

import org.apache.http.HttpHost;
import org.chobit.commons.utils.PropKit;

import static org.chobit.commons.utils.PropKit.getInt;
import static org.chobit.commons.utils.PropKit.getProp;

/**
 * 配置信息管理
 *
 * @author robin
 */
public final class Config {


	static {
		PropKit.load("/config.properties");
	}


	/**
	 * 代理地址
	 */
	public static final String PROXY_HOST = getProp("proxy.host");

	/**
	 * 代理端口号
	 */
	public static final int PROXY_PORT = getInt("proxy.port");



	public static HttpHost proxy() {
		return new HttpHost(PROXY_HOST, PROXY_PORT);
	}


	private Config() {
	}

}
