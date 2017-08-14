package com.example.uberv.maptbookdownloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements ParseCallback {
    public static final String MAPT_URL = "https://mapt.io";
    public static final String MAPT_LOGIN_URL = "https://www.packtpub.com/mapt/login";
    public static final String BASE_URL = "https://www.packtpub.com";

    @BindView(R.id.webview)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        WebViewClient client = new MyWebViewClient();
        mWebView.setWebViewClient(client);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new MyJavascriptInterface(this, this), "HtmlViewer");
        mWebView.setWebChromeClient(new MyWebChromeClient());
    }

    @OnClick(R.id.leak_btn)
    void onLeak() {
        mWebView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
    }

    @OnClick(R.id.start_button)
    void onStartMapt() {
        Timber.d("Starting MAPT");
        mWebView.loadUrl(MAPT_LOGIN_URL);
    }

    @Override
    public void onParseComplete(String text, final String nextSectionUrl) {
        Timber.d("Parsed");
        Toast.makeText(this, "Parsed", Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(BASE_URL+nextSectionUrl);
            }
        });
    }
}
