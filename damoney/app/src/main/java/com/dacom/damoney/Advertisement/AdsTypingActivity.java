package com.dacom.damoney.Advertisement;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivityAdsTypingBinding;

public class AdsTypingActivity extends AppCompatActivity {
    ActivityAdsTypingBinding mBind;
    boolean bRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_ads_touch);
        mBind.setItem(this);
        setupDemoAds();
    }

    private void setupDemoAds() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bRunning = false;
        if(AdsManager.listener != null)
            AdsManager.listener.onAdsFinished(-1);
    }
}
