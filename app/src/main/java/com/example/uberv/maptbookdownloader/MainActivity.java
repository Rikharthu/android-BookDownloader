package com.example.uberv.maptbookdownloader;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.uberv.maptbookdownloader.models.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements ParseCallback, MyWebViewClient.OnPageFinishedListener, MyWebChromeClient.OnProgressChangedListener {
    public static final String MAPT_URL = "https://mapt.io";
    public static final String MAPT_LOGIN_URL = "https://www.packtpub.com/mapt/login";
    public static final String BASE_URL = "https://www.packtpub.com";

    public static final String REGEX = "/mapt/book/[^\\/]*/[^\\/]*/";

    private List<HtmlPage> mPages;
    private HtmlPage mCurrentPage;

    private Handler mHandler;
    private boolean mIsLeaking = false;

    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.webpage_loading_progressbar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ButterKnife.bind(this);

        MyWebViewClient client = new MyWebViewClient();
        client.setFinishedListener(this);
        mWebView.setWebViewClient(client);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new MyJavascriptInterface(this, this), "HtmlViewer");
        MyWebChromeClient chromeClient = new MyWebChromeClient();
        mWebView.setWebChromeClient(chromeClient);
        chromeClient.setOnProgressChangedListener(this);

        mHandler = new Handler();
        mPages = new ArrayList<>();
    }

    @OnClick(R.id.leak_btn)
    void onLeak() {
        mIsLeaking = true;
        mWebView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
    }

    @OnClick(R.id.login_btn)
    void onLogin() {
        Timber.d("Starting MAPT");
        mWebView.loadUrl(MAPT_LOGIN_URL);
    }

    @OnClick(R.id.start_button)
    void onStartMapt() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("https://www.packtpub.com/mapt/book/application_development/9781787124417/4/ch04lvl1sec36/executing-tasks-in-an-executor-that-returns-a-result")
                            .build();

                    Response response = null;
                    try {
                        response = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String res = response.body().string();
                    Timber.d(res);
                } catch (Exception e) {

                }
            }
        }).start();
    }

    @Override
    public void onParseComplete(final HtmlPage page) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Timber.d("onParseComplete");
//                Toast.makeText(MainActivity.this, "Parsed", Toast.LENGTH_SHORT).show();
                page.setUrl(mWebView.getUrl());
                Timber.d("Current url: " + page.getUrl());
                Timber.d("Next url: " + page.getNextPageUrl());

                String fileName = String.format("book_%d.html", System.currentTimeMillis());
                FileUtils.writeToFile(FileUtils.createBookFile(fileName), page.toString());
                Timber.d("Saved");

                mPages.add(page);
                mCurrentPage = page;

                if (!TextUtils.isEmpty(page.getNextPageUrl().replaceAll(REGEX, ""))) {
                    Timber.d("Loading url " + page.getNextPageUrl());
                    // load next url
                    mWebView.loadUrl(BASE_URL + page.getNextPageUrl().replaceAll("%2f", "%252f"));

//                    mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Timber.d("Starting leaking a url: " + page.getNextPageUrl());
//                            onLeak();
//                        }
//                    }, 10000);

//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Timber.d("Leaking at url " + page.getNextPageUrl());
//                            try {
//                                Thread.sleep(5000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    onLeak();
//                                }
//                            });
//                        }
//                    }).start();
                } else {
                    Timber.d("Looks like it is an end of file " + page.getUrl());
                }
            }
        });
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mWebView.loadUrl(BASE_URL + page.getUrl());
//            }
//        });
    }

    @Override
    public void onPageFinished(String url) {
        Timber.d("onPageFinished: " + url);
        if (mIsLeaking) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Timber.d("Starting leaking a url: " + mCurrentPage.getNextPageUrl());
                    onLeak();
                }
            }, 20000);
        }
    }

    @Override
    public void onProgressChanged(int newProgress) {
        if (newProgress == 100) {
            mProgressBar.animate().alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }else if(mProgressBar.getVisibility()==View.GONE){
            mProgressBar.animate().alpha(1).setDuration(0).start();
            mProgressBar.setVisibility(View.VISIBLE);
        }
        mProgressBar.setProgress(newProgress);
    }
}
