package com.example.uberv.maptbookdownloader;

import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import timber.log.Timber;

public class MyWebViewClient extends WebViewClient {

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        Timber.d(errorResponse.getReasonPhrase() + ", CODE: " + errorResponse.getStatusCode());
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Timber.d("onPageStarted() for url " + url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Timber.d("onPageFinished() for url " + url);

    }
}
