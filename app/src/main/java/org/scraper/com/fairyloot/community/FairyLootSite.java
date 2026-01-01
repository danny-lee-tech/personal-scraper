package org.scraper.com.fairyloot.community;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.lang.AutoCloseable;

import org.scraper.com.fairyloot.community.page.MainPage;

public class FairyLootSite implements AutoCloseable {
    private static String URL = "https://community.fairyloot.com";

    private Playwright playwright;
    private Browser browser;

    private BrowserContext context;

    public FairyLootSite() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(100));
        context = browser.newContext();
    }

    public MainPage navigate() {
        Page page = context.newPage();
        page.navigate(URL);

        return new MainPage(page);
    }

    @Override
    public void close() {
        if (playwright != null) {
            playwright.close();
        }
    }
}
