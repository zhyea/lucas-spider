package org.chobit.spider.process.transform;

import org.chobit.spider.model.Anchor;
import org.chobit.spider.model.content.Content;
import org.chobit.spider.model.content.Image;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

import static org.chobit.commons.constans.Symbol.EMPTY;
import static org.chobit.commons.constans.Symbol.POINT;
import static org.chobit.commons.utils.StrKit.isNotBlank;
import static org.chobit.spider.constants.Constants.*;
import static org.chobit.spider.tools.UrlHelper.buildUrl;

/**
 * 图片信息解析
 *
 * @author robin
 */
public abstract class AbstractImageTransformer implements Transformer<Image> {


	@Override
	public List<Anchor> postAnchors(Document docIndex, String baseUrl) {
		Elements elements = docIndex.select(postUrlSelector());
		List<Anchor> result = new LinkedList<>();
		for (Element ele : elements) {
			String href = ele.attr(HREF);
			result.add(new Anchor(FLAG_IMG, buildUrl(href, baseUrl)));
		}
		return result;
	}


	@Override
	public Content<Image> extract(Document docPost, String title, String parent) {
		String imgTitle = EMPTY;
		if (isNotBlank(postTitleSelector())) {
			Elements eleImgTitle = docPost.select(postTitleSelector());
			if (!eleImgTitle.isEmpty()) {
				imgTitle = eleImgTitle.first().text();
			}
		}

		Elements imgEleList = docPost.select(postContentSelector());

		Content<Image> content = new Content<>(imgTitle);

		for (Element ele : imgEleList) {
			String imgUrl = ele.attr(FLAG_IMG_SRC);

			Image img = new Image(imgTitle, imgUrl);

			int idxLastPoint = imgUrl.lastIndexOf(POINT);
			String imgExt = imgUrl.substring(idxLastPoint);
			img.setExt(imgExt);

			content.addLine(img);
		}


		return content;
	}
}
