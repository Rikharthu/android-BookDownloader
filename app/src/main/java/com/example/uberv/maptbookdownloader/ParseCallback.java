package com.example.uberv.maptbookdownloader;

import com.example.uberv.maptbookdownloader.models.HtmlPage;

interface ParseCallback {
    void onParseComplete(HtmlPage page);
}
