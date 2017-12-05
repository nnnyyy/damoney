package com.dacom.damoney.Advertisement;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;

import com.dacom.damoney.DamoneyHttpHelper;
import com.dacom.damoney.Global;
import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivityAdsBinding;

public class AdsActivity extends AppCompatActivity {
    ActivityAdsBinding mBind;
    boolean bRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_ads);
        mBind.setItem(this);
        setupDemoAds();
    }

    private void setupDemoAds() {
        mBind.quizLayout.setVisibility(View.GONE);
        MediaController mc = new MediaController(this);
        mBind.adVideo.setMediaController(null);
        mBind.adVideo.requestFocus();
        mBind.adVideo.setVideoURI(Uri.parse(Global.BASE_URL + "/testads.mp4"));
        mBind.adVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mBind.adVideo.seekTo(0);
                mBind.adVideo.start();
            }
        });

        mBind.adVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // 퀴즈 시작
                mBind.quizLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bRunning = false;
        if(AdsManager.listener != null)
            AdsManager.listener.onAdsFinished(-1);
    }

    public void onBtnSel1(View v) {
        finish();
        DamoneyHttpHelper.ViewMainAd(AdsManager.mSerial, new DamoneyHttpHelper.MyCallbackInterface() {
            @Override
            public void onResult(int nRet) {
                finish();
                if(nRet == 0 && AdsManager.listener != null)
                    AdsManager.listener.onAdsFinished(0);
                else {
                    AdsManager.listener.onAdsFinished(-1);
                }
            }
        });
    }

    public void onBtnSel2(View v) {

    }

    public void onBtnSel3(View v) {

    }

    public void onBtnSel4(View v) {

    }
}
