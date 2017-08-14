package com.example.uberv.maptbookdownloader;

interface ParseCallback {
    void onParseComplete(String text, String nextSectionUrl);
}
