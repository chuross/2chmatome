package com.chuross.rssmatome.application.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.chuross.rssmatome.R;
import com.chuross.rssmatome.infrastructure.android.query.AQuery;

public class EntryDetailActivity extends Activity {
    public static String EXTRA_KEY_URL = "url";

    private WebView web;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);
        String url = getIntent().getStringExtra(EXTRA_KEY_URL);
        if(url == null) {
            Toast.makeText(this, "この記事は見ることができません。", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        AQuery query = getAQuery();
        web = query.id(R.id.web).getWebView();
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                return false;
            }
        });
        web.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if(web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
