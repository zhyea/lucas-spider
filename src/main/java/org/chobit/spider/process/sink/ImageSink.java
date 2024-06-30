package org.chobit.spider.process.sink;

import org.apache.http.HttpHost;
import org.chobit.commons.utils.Collections2;
import org.chobit.spider.except.ImageSaveException;
import org.chobit.spider.model.content.Content;
import org.chobit.spider.model.content.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.chobit.commons.constans.Symbol.POINT;
import static org.chobit.commons.constans.Symbol.DASHED;
import static org.chobit.commons.http.HttpClient.getForBytes;
import static org.chobit.commons.utils.StrKit.isBlank;
import static org.chobit.spider.Config.proxy;


/**
 * 用于将图片下载到本地
 *
 * @author robin
 */
public class ImageSink implements Sink<Image> {


	private static final Logger logger = LoggerFactory.getLogger(ImageSink.class);

	private final String localFolder;

	private final boolean useProxy;

	public ImageSink(String localFolder) {
		this(localFolder, false);
	}


	public ImageSink(String localFolder, boolean useProxy) {
		this.localFolder = localFolder;
		this.useProxy = useProxy;
	}

	@Override
	public void sink(List<Content<Image>> contents) {
		if (Collections2.isEmpty(contents)) {
			return;
		}

		AtomicInteger seq = new AtomicInteger(0);

		for (Content<Image> content : contents) {
			this.save(content, seq);
		}
	}


	private void save(Content<Image> content, AtomicInteger seq) {

		HttpHost proxy = null;
		if (this.useProxy) {
			proxy = proxy();
		}

		for (Image img : content.getLines()) {
			String title = img.getTitle();
			if (isBlank(title)) {
				title = String.valueOf(seq.getAndIncrement());
			} else {
				title = title + DASHED + seq.getAndIncrement();
			}
			String path = localFolder + title + POINT + img.getExt();
			try (FileOutputStream writer = new FileOutputStream(path)) {
				byte[] bytes = getForBytes(img.getSrc(), proxy);
				writer.write(bytes);
			} catch (IOException e) {
				logger.error("save image failed, img:{}", img, e);
				throw new ImageSaveException(img, e);
			}
		}
	}
}
