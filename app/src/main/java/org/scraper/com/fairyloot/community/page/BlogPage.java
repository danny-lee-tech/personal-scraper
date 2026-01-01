package org.scraper.com.fairyloot.community.page;

import java.util.List;
import org.scraper.common.WebPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BlogPage extends WebPage {

    public BlogPage(Page page) {
        super(page);
    }

    public String content() {
        List<Locator> paragraphs = page.locator(".singleBlog-content .wysiwyg p").all();
        StringBuilder builder = new StringBuilder();
        for (Locator paragraph : paragraphs) {
            builder.append(paragraph.textContent());
            builder.append("\n");
        }

        return builder.toString().trim();
    }
}
