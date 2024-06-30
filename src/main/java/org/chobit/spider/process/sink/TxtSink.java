package org.chobit.spider.process.sink;

import org.chobit.spider.model.Volume;
import org.chobit.spider.model.content.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.chobit.commons.constans.Symbol.NEW_LINE;
import static org.chobit.commons.utils.StrKit.isBlank;
import static org.chobit.commons.utils.StrKit.isNotBlank;


/**
 * 用于保存txt文本内容
 *
 * @author robin
 */
public class TxtSink implements Sink<String> {


	private static final Logger logger = LoggerFactory.getLogger(TxtSink.class);


	private final String path;


	public TxtSink(String path) {
		this.path = path;
	}


	@Override
	public void sink(List<Content<String>> contents) {

		List<Volume<String>> volumes = tidyCatalog(contents);

		try (FileWriter writer = new FileWriter(path)) {
			writer.write(new String(new byte[]{(byte) 0xef, (byte) 0xbb, (byte) 0xbf}));
			for (Volume<String> v : volumes) {
				writeVolume(v, writer);
			}
			writer.write(NEW_LINE + "END.");
		} catch (Exception e) {
			logger.error("Write to txt failed.", e);
		}
	}


	private void writeVolume(Volume<String> v, FileWriter writer) throws IOException {
		if (isNotBlank(v.getName())) {
			writer.write(v.getName());
			writer.write(NEW_LINE + NEW_LINE);
		}
		for (Content<String> pc : v.getChapters()) {
			if (pc.getLines().isEmpty()) {
				continue;
			}
			writer.write(NEW_LINE);
			writer.write(pc.getChapterName());
			writer.write(NEW_LINE);
			int count = 0;
			for (String line : pc.getLines()) {
				if (0 == count++ && line.equals(pc.getChapterName())) {
					continue;
				}
				if (isBlank(line)) {
					continue;
				}
				writer.write(line + NEW_LINE);
			}
			System.out.println(pc.getChapterName());
		}
	}
	// --------------------------------------------------------
}
