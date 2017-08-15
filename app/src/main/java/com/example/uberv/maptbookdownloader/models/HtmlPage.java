package com.example.uberv.maptbookdownloader.models;

public class HtmlPage {

    private String mStyles;
    private String mText;
    public String mUrl;
    public String mNextPageUrl;

    public HtmlPage() {
    }

    public HtmlPage(String styles, String text, String nextPageUrl, String url) {
        mStyles = styles;
        mText = text;
        mUrl = url;
        mNextPageUrl = nextPageUrl;
    }

    public String getStyles() {
        return mStyles;
    }

    public void setStyles(String styles) {
        mStyles = styles;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getNextPageUrl() {
        return mNextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        mNextPageUrl = nextPageUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(mStyles);
        sb.append("\n");
        sb.append(mText);
        sb.append("</html>");
        return sb.toString();
    }
}
