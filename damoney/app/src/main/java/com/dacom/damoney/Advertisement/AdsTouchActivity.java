package com.dacom.damoney.Advertisement;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;

import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivityAdsBinding;

public class AdsTouchActivity extends AppCompatActivity {
    ActivityAdsBinding mBind;
    boolean bRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_ads);
        setupDemoAds();
    }

    private void setupDemoAds() {
        MediaController mc = new MediaController(this);
        mBind.adVideo.setMediaController(null);
        mBind.adVideo.requestFocus();
        mBind.adVideo.setVideoURI(Uri.parse("http://4seasonpension.com:3003/testads.mp4"));
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
                finish();
                if(AdsManager.listener != null)
                    AdsManager.listener.onAdsFinished(0);
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
}
