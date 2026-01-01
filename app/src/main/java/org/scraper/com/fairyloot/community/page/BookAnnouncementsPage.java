package org.scraper.com.fairyloot.community.page;

import java.util.List;
import org.scraper.common.WebPage;

import com.google.common.collect.Lists;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BookAnnouncementsPage extends WebPage {

    public BookAnnouncementsPage(Page page) {
        super(page);
    }

    public class BlogPost {
        private String title;
        private String date;
        private String url;

        private BlogPost(String title, String date, String url) {
            this.title = title;
            this.date = date;
            this.url = url;
        }

        public String title() {
            return title;
        }

        public String date() {
            return date;
        }

        public BlogPage navigate() throws Exception {
            Page newPage = page.context().newPage();
            newPage.navigate(url);
            return new BlogPage(newPage);
        }
    }

    public BlogPost featuredBlogPost() {
        String title = page.locator(".global-featuredBlogPost h3").textContent().trim();
        String date = page.locator(".global-featuredBlogPost time").textContent().trim();
        String url = page.locator(".global-featuredBlogPost a").first().getAttribute("href");

        return new BlogPost(title, date, url);
    }

    public List<BlogPost> archivedBlogPosts() {
        List<BlogPost> blogPosts = Lists.newArrayList();
        List<Locator> locators = page.locator(".blogArchive-posts article.global-newsCardMedium").all();
        for(Locator locator : locators) {
            String title = locator.locator("h3 > a").textContent().trim();
            String date = locator.locator("time").textContent();
            String url = locator.locator("h3 > a").getAttribute("href");
            blogPosts.add(new BlogPost(title, date, url));
        }

        return blogPosts;
    }
}
