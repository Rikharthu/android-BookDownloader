package com.example.uberv.maptbookdownloader;

import com.example.uberv.maptbookdownloader.models.HtmlPage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import timber.log.Timber;

public class JsoupParser {
    public static void parse(String html, ParseCallback callback) {
        Document doc = Jsoup.parse(html);
        // Remove "Copy to clipboard" buttons as they won't function
        doc.select("button[class=copy-to-clipboard]").remove();
        // TODO select all styles
        // https://try.jsoup.org/
        // link[ng-href][rel=stylesheet]
        Elements styles = doc.select("link[ng-href][rel=stylesheet]");
        Elements sections = doc.getElementsByClass("section");
        String text = null;
        String nextSectionUrl = null;
        if (sections.size() > 0) {
            text = sections.get(0).toString();
            Timber.d("found section");
        }
        Elements buttons = doc.getElementsByAttributeValue("class", "btn btn-primary pull-right btn-lg");
        for (int i = 0; i < buttons.size(); i++) {
            Element button = buttons.get(i);
            if (button.hasText() && button.text().equalsIgnoreCase("next section")) {
                nextSectionUrl = button.attr("href");
                Attributes attrs = button.attributes();
                Timber.d("found next section url");
                break;
            }
            Timber.d("found next section url");
        }

        HtmlPage page = new HtmlPage(styles.toString(), sections.toString(), nextSectionUrl, null);

        callback.onParseComplete(page);
    }
}
