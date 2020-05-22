package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Main2Activity extends AppCompatActivity {

    // hello

    private WebView webView;
    ProgressBar progressBar;
    String webadres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        webView = (WebView) findViewById(R.id.webview);

        webadres = getIntent().getExtras().getString("weburl");

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(webadres);
        //progressBar.setVisibility(View.GONE);
    }
}
