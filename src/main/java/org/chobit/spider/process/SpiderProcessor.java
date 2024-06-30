package org.chobit.spider.process;

import org.apache.http.HttpHost;
import org.chobit.commons.http.HttpClient;
import org.chobit.spider.except.WebPageObtainException;
import org.chobit.spider.model.Anchor;
import org.chobit.spider.model.content.Content;
import org.chobit.spider.process.sink.Sink;
import org.chobit.spider.process.src.Source;
import org.chobit.spider.process.transform.Transformer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import static org.chobit.commons.utils.StrKit.isBlank;
import static org.chobit.commons.utils.StrKit.isNotBlank;
import static org.chobit.spider.Config.proxy;

/**
 * 网页爬虫处理器
 *
 * @author robin
 */
public class SpiderProcessor<E> {


	private static final Logger logger = LoggerFactory.getLogger(SpiderProcessor.class);


	private final Source source;

	private final Transformer<E> transformer;

	private final Sink<E> sink;

	private int retry = 6;


	public SpiderProcessor(Source source, Transformer<E> transformer, Sink<E> sink) {
		this.source = source;
		this.transformer = transformer;
		this.sink = sink;
	}


	public SpiderProcessor(Source source, Transformer<E> transformer, Sink<E> sink, int retry) {
		this(source, transformer, sink);
		this.retry = retry;
	}


	public void process() {
		Charset charset = source.charset();

		Document docIndex = null;
		if (isNotBlank(source.indexUrl())) {
			docIndex = obtainHtmlDocument(source.indexUrl(), charset, source.useProxy());
		}
		List<Anchor> anchors = transformer.postAnchors(docIndex, source.baseUrl());

		List<Content<E>> contents = new LinkedList<>();
		for (Anchor anchor : anchors) {
			Document docPost = obtainHtmlDocument(anchor.getUrl(), charset, source.useProxy());
			Content<E> content = transformer.extract(docPost, anchor.getName(), anchor.getParent());
			// 检查各行是否需要删除
			content.getLines().removeIf(this::toDel);
			// 内容为空时不做写入
			if (content.getLines().isEmpty()) {
				continue;
			}
			contents.add(content);
		}

		sink.sink(contents);
	}


	private boolean toDel(E line) {
		for (String s : source.blacklist()) {
			if (String.valueOf(line).contains(s)) {
				return true;
			}
		}
		return false;
	}


	public Document obtainHtmlDocument(String url, Charset charset, boolean useProxy) {
		HttpHost proxy = null;
		if (useProxy) {
			proxy = proxy();
		}
		int count = 0;
		while (count < retry) {
			try {
				byte[] bytes = HttpClient.getForBytes(url, proxy);

				if (null == bytes) {
					throw new WebPageObtainException(url);
				}

				String content = new String(bytes, charset);
				if (isBlank(content)) {
					throw new WebPageObtainException(url);
				}

				return Jsoup.parse(content);
			} catch (RuntimeException e) {
				if (count > retry) {
					logger.error("obtain html document error, url:{}", url, e);
					throw e;
				}
			}
			count++;
		}
		return null;
	}
}
