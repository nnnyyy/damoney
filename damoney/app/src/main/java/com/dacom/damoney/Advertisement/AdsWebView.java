package com.dacom.damoney.Advertisement;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivityAdsWebViewBinding;

public class AdsWebView extends AppCompatActivity {
    ActivityAdsWebViewBinding mBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_ads_web_view);

        String url = getIntent().getStringExtra("url");
        Log.d("AdsWebView", "url : " + url);
        mBind.webview.loadUrl(url, true);
    }
}
