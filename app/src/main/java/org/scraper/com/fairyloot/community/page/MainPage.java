package org.scraper.com.fairyloot.community.page;

import org.scraper.common.WebPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MainPage extends WebPage {

    public MainPage(Page page) {
        super(page);
    }

    public BookAnnouncementsPage clickBookAnnouncements() throws Exception {
        page.locator(".site-headerCategoriesBar span.block").filter(new Locator.FilterOptions().setHasText(" Book Announcements ")).hover();

        String url = page.locator(".site-headerCategoriesBar a.block").filter(new Locator.FilterOptions().setHasText(" Book Announcements ")).getAttribute("href"); // Opens a new tab
        Page newPage = page.context().newPage();
        newPage.navigate(url);
        return new BookAnnouncementsPage(newPage);
    }
}
