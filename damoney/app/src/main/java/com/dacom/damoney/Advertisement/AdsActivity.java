package com.dacom.damoney.Advertisement;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivityAdsBinding;

public class AdsActivity extends AppCompatActivity {
    ActivityAdsBinding mBind;
    boolean bRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_ads);
        setupDemoAds();
    }

    private void setupDemoAds() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                bRunning = true;
                long nStart = System.currentTimeMillis();
                while(bRunning) {
                    final long nDiff = System.currentTimeMillis() - nStart;
                    if(nDiff >= 3000 && bRunning)
                    {
                        finish();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(AdsManager.listener != null)
                                    AdsManager.listener.onAdsFinished(0);
                            }
                        });
                        break;
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mBind.adsText.setText(String.valueOf(nDiff / 1000));
                        }
                    });

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bRunning = false;
        if(AdsManager.listener != null)
            AdsManager.listener.onAdsFinished(-1);
    }
}
