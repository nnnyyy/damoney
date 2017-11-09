package com.dacom.damoney.Advertisement;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;

import com.dacom.damoney.Functional.NYUtil;
import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivityAdsBinding;
import com.dacom.damoney.databinding.ActivityAdsTouchBinding;

import java.util.ArrayList;

public class AdsTouchActivity extends AppCompatActivity {
    ActivityAdsTouchBinding mBind;
    boolean bRunning = false;
    ArrayList<TextView> atvlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_ads_touch);
        mBind.setItem(this);
        setupDemoAds();
    }

    private void setupDemoAds() {
        int[] dx = { 100, 100, 100, 100, 100 };
        int[] dy = { 100, 130, 160, 190, 220 };
        for(int i = 0 ; i < 5 ; ++i) {
            TextView tv = new TextView(this);
            tv.setText("Touch " + i);
            tv.setX(NYUtil.dptopx(this, dx[i]));
            tv.setY(NYUtil.dptopx(this, dy[i]));
            tv.setBackgroundColor(Color.WHITE);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setVisibility(View.GONE);
                    view.setOnClickListener(null);
                    if(CheckClickState()) {
                        finish();
                        if(AdsManager.listener != null)
                            AdsManager.listener.onAdsFinished(0);
                    }
                }
            });
            mBind.rlTouchArea.addView(tv);
            atvlist.add(tv);
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
        for(TextView tv : atvlist) {
            if( tv.getVisibility() == View.VISIBLE ) return false;
        }
        return true;
    }
}
