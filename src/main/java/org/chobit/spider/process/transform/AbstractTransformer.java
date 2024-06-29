package org.chobit.spider.process.transform;

import org.chobit.commons.utils.Collections2;
import org.chobit.spider.model.Anchor;
import org.chobit.spider.model.PageContent;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

import static org.chobit.commons.utils.StrKit.isBlank;
import static org.chobit.spider.constants.Constants.*;
import static org.chobit.spider.tools.UrlHelper.buildUrl;

/**
 * @author robin
 */
public abstract class AbstractTransformer implements Transformer {


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public List<Anchor> postAnchors(Document doc, String baseUrl) {

        String urlSelector = postUrlSelector();
        Elements elements = doc.select(urlSelector);

        List<Anchor> result = new LinkedList<>();
        if (Collections2.isEmpty(elements)) {
            return result;
        }

        for (Element ele : elements) {
            String name = ele.text();
            String href = ele.attr(HREF);
            String url = buildUrl(href, baseUrl);
            result.add(new Anchor(name, url));
        }
        return result;
    }


    @Override
    public PageContent extract(Document docPost, String bakTitle, String parent) {
        Element eleTitle = isBlank(postTitleSelector()) ? null : docPost.selectFirst(postTitleSelector());
        Element eleContent = docPost.selectFirst(postContentSelector());
        String title = (null == eleTitle ? (null == bakTitle ? "" : bakTitle) : eleTitle.text());

        PageContent content = new PageContent(title);
        content.setContentHtml(null == eleContent ? "" : eleContent.html());
        content.setChapterName(title);
        content.setVolumeName(parent);

        if (null == eleContent) {
            return content;
        }

        Elements paragraphs = eleContent.select(SELECTOR_PARAGRAPH);
        for (Element ele : paragraphs) {
            content.addLine(ele.text().trim());
        }

        if (content.getLines().isEmpty()) {
            extract0(content, eleContent);
        }

        return content;
    }


    protected void extract0(PageContent content, Element ele) {
        ele.select("ul").before(REPLACER_BR).after(REPLACER_BR);
        ele.select("ol").before(REPLACER_BR).after(REPLACER_BR);
        ele.select("li").before(REPLACER_BR).after(REPLACER_BR);
        ele.select("br").before(REPLACER_BR).after(REPLACER_BR);
        ele.select("BR").before(REPLACER_BR).after(REPLACER_BR);
        ele.select("p").before(REPLACER_BR).after(REPLACER_BR);
        String all = ele.text();
        String[] arr = all.split(REPLACER_BR);
        for (String s : arr) {
            if (isBlank(s)) {
                continue;
            }
            content.addLine(s.trim());
        }
    }


}
