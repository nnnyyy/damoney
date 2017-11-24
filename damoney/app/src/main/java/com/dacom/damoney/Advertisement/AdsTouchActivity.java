package com.dacom.damoney.Advertisement;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dacom.damoney.DamoneyHttpHelper;
import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivityAdsTouchBinding;
import com.dacom.damoney.databinding.AdsTouchAreaBinding;
import com.dacom.damoney.databinding.BonusSectionBinding;

import java.util.ArrayList;

public class AdsTouchActivity extends AppCompatActivity {
    ActivityAdsTouchBinding mBind;
    boolean bRunning = false;
    ArrayList<View> atvlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_ads_touch);
        mBind.setItem(this);
        setupDemoAds();
    }

    private void setupDemoAds() {
        float[] dx = { 0.504f, 0.41f, 0.07f, 0.07f, 0.26f };
        float[] dy = { 0.10f, 0.15f, 0.215f, 0.056f, 0.52f };

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        for(int i = 0 ; i < 5 ; ++i) {
            AdsTouchAreaBinding bind = AdsTouchAreaBinding.inflate(LayoutInflater.from(getApplicationContext()), mBind.rlTouchArea, false);
            int rx = (int)(float)(dx[i] * size.x);
            int ry = (int)(float)(dy[i] * size.y);
            Log.d("MyLog", "rx : " + rx + ", ry : " + ry);
            bind.getRoot().setX(rx);
            bind.getRoot().setY(ry);
            //bind.getRoot().setBackgroundColor(Color.WHITE);
            bind.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setVisibility(View.GONE);
                    view.setOnClickListener(null);
                    if(CheckClickState()) {
                        DamoneyHttpHelper.ViewMainAd(AdsManager.mSerial, new DamoneyHttpHelper.MyCallbackInterface() {
                            @Override
                            public void onResult(int nRet) {
                                finish();
                                if(nRet == 0 && AdsManager.listener != null)
                                    AdsManager.listener.onAdsFinished(0);
                            }
                        });
                    }
                }
            });
            mBind.rlTouchArea.addView(bind.getRoot());
            atvlist.add(bind.getRoot());
            CheckClickState();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bRunning = false;
        if(AdsManager.listener != null)
            AdsManager.listener.onAdsFinished(-1);
    }

    protected boolean CheckClickState() {
        long nFailedCnt = 0;
        for(View tv : atvlist) {
            if( tv.getVisibility() == View.VISIBLE ) {
                nFailedCnt++;
            }
        }
        mBind.cnt.setText("" + (atvlist.size() - nFailedCnt) + "/" + atvlist.size());
        return nFailedCnt == 0;
    }
}
