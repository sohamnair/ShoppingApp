package com.morningessetial.morningessentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.morningessetial.sohammorningessentials.R;

public class WebViewPage extends AppCompatActivity {

    WebView webView;
    Intent i;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_page);

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());

        i = getIntent();
        s = i.getStringExtra("type");

        if (s.equals("about")){
            webView.loadUrl("http://mtrac.in/MorningEssentials/aboutus.html");
        }
        else if (s.equals("privacy")){
            webView.loadUrl("http://mtrac.in/MorningEssentials/privacypolicy.html");
        }


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}
