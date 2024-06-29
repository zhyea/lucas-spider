package org.chobit.spider.process.sink;

import org.chobit.spider.model.PageContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.util.List;

import static org.chobit.commons.http.HttpClient.getForBytes;


/**
 * 用于将内容下载到本地
 *
 * @author robin
 */
public class DownloadSink implements Sink {


	private static final Logger logger = LoggerFactory.getLogger(DownloadSink.class);

	private final String localFolder;

	public DownloadSink(String localFolder) {
		this.localFolder = localFolder;
	}

	@Override
	public void sink(List<PageContent> contents) {
		contents.forEach(this::save);
	}


	private void save(PageContent content) {
		String path = localFolder + content.getTitle();
		String url = content.firstLine();
		try (FileOutputStream writer = new FileOutputStream(path)) {
			byte[] bytes = getForBytes(url);
			writer.write(bytes);
		} catch (Exception e) {
			logger.error("Save file error.", e);
		}
	}
}
