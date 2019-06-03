package com.yq.yqim.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yq.yqim.R;

import static com.yq.yqim.controller.adapter.BookAdapter.bhref;

public class webviewActivity extends AppCompatActivity {
private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView=(WebView)findViewById(R.id.webview);


        Intent intent = getIntent();
        String data = intent.getStringExtra("href");

        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        System.out.println("和岁的拉斯基的卢卡斯就打算决定"+bhref);

        webView.canGoBack();
        webView.loadUrl("https://m.dingdian.org/"+bhref);          //调用loadUrl方法为WebView加入链接
        setContentView(webView);                           //调用Activity提供的setContentView将webView显示出来
        System.out.println("https://m.dingdian.org/"+bhref);
    }
}
