package com.dacom.damoney.Advertisement;

import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dacom.damoney.DamoneyHttpHelper;
import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivityAdsTouchBinding;
import com.dacom.damoney.databinding.AdsTouchAreaBinding;

import java.util.ArrayList;

public class AdsTouchActivity extends AppCompatActivity {
    ActivityAdsTouchBinding mBind;
    boolean bRunning = false;
    boolean bSuccess = false;
    ArrayList<View> atvlist = new ArrayList<>();
    int curIdx = 0;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_ads_touch);
        mBind.setItem(this);
        setupDemoAds();
        start();
    }

    private void setupDemoAds() {
        float[] dx = { 0.39f, 0.037f, 0.048f, 0.035f, 0.16f };
        float[] dy = { 0.14f, 0.08f, 0.045f, 0.201f, 0.50f };
        Point[] ptHilightSize = {
                new Point(42,8),
                new Point(64,8),
                new Point(43,5),
                new Point(47,7),
                new Point(68,20 )
        };

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
            bind.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setVisibility(View.GONE);
                    ++curIdx;
                    if(curIdx < atvlist.size()) {
                        View nextView = atvlist.get(curIdx);
                        nextView.setVisibility(View.VISIBLE);
                        /*Animation repeat_scale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.repeat_scale);
                        nextView.findViewById(R.id.img).startAnimation(repeat_scale);*/
                    }
                    view.setOnClickListener(null);
                    if(CheckClickState()) {
                        bSuccess = true;
                        bRunning = false;
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
                }
            });
            mBind.rlTouchArea.addView(bind.getRoot());
            bind.getRoot().setVisibility(View.GONE);
            bind.root.getLayoutParams().width = (int)(((float)(ptHilightSize[i].x * size.x)) * 0.01);
            bind.root.getLayoutParams().height = (int)(((float)(ptHilightSize[i].y * size.y)) * 0.01);
            atvlist.add(bind.getRoot());
            CheckClickState();
        }

        Animation repeat_scale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.repeat_scale);
        View view = atvlist.get(curIdx);
        //view.findViewById(R.id.img).startAnimation(repeat_scale);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        bRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onBackPressed();
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

    protected void start() {
        bRunning = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int n = 0;
                while(bRunning && n != 15) {
                    try {
                        Thread.sleep(1000);
                        n++;
                        final int value = (int)((float)100 * (float)n / 15.0);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mBind.bar.setProgress(value);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBind.bar.setProgress(100);
                    }
                });
                if(!bSuccess) {
                    finish();
                    if(AdsManager.listener != null)
                        AdsManager.listener.onAdsFinished(-1);
                }
            }
        });
        thread.start();
    }
}
