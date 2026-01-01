package org.scraper.common;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.google.common.io.Files;
import com.microsoft.playwright.Page;

public abstract class WebPage {
    protected Page page;

    public WebPage(Page page) {
        this.page = page;
    }

    public String title() {
        return page.title();
    }

    public String url() {
        return page.url();
    }

    public void screenshot() throws IOException {
        File file = new File(this.getClass().getName() + ".png");
        Files.write(Objects.requireNonNull(page.screenshot()), file);
    }

    public void close() {
        // In App.main, before you do anything:
        page.context().onClose(ctx -> {
            System.out.println("!!! WARNING: The BrowserContext just closed! !!!");
        });
        page.close();
    }
}