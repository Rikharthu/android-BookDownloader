package com.example.uberv.maptbookdownloader;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import timber.log.Timber;

public class MyWebChromeClient extends WebChromeClient {

    private OnProgressChangedListener mOnProgressChangedListener;

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        Timber.d(newProgress+"...");
        if(mOnProgressChangedListener!=null){
            mOnProgressChangedListener.onProgressChanged(newProgress);
        }
    }

    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) {
        mOnProgressChangedListener = onProgressChangedListener;
    }

    public interface OnProgressChangedListener{
        void onProgressChanged(int newProgress);
    }

}
