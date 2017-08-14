package com.example.uberv.maptbookdownloader;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import timber.log.Timber;

public class MyWebChromeClient extends WebChromeClient {
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        Timber.d(newProgress+"...");
    }


}
