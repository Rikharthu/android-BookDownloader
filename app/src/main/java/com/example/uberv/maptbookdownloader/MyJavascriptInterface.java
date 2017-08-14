package com.example.uberv.maptbookdownloader;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class MyJavascriptInterface {
    private Context ctx;

    ParseCallback mParseCallback;

    public MyJavascriptInterface(Context ctx, ParseCallback parseCallback) {
        this.ctx = ctx;
        mParseCallback = parseCallback;
    }

    @JavascriptInterface
    public void showHTML(String html) {
//        new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
//                .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
//        InputStream stream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
//        MapXmlParser parser= new MapXmlParser();
//        try {
//            parser.parse(stream);
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        JsoupParser.parse(html, mParseCallback);
    }

}
