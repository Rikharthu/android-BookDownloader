package com.example.uberv.maptbookdownloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import timber.log.Timber;

public class JsoupParser {
    public static void parse(String html, ParseCallback callback) {
        Document doc = Jsoup.parse(html);
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
        callback.onParseComplete(text, nextSectionUrl);
    }
}
