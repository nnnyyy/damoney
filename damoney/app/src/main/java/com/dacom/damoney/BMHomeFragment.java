package com.dacom.damoney;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.daasuu.library.DisplayObject;
import com.daasuu.library.drawer.SpriteSheetDrawer;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.util.Util;
import com.dacom.damoney.Advertisement.AdsManager;
import com.dacom.damoney.Advertisement.AdsResultListener;
import com.dacom.damoney.databinding.FragmentBmhomeBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMHomeFragment extends Fragment implements AdsResultListener{
    FragmentBmhomeBinding mBind;
    AdsManager adsMan;
    public BMHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_bmhome, container, false);
        setupAds();
        setupButtonEvent();
        setupAnim();
        return mBind.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBind.animationTextureView.tickStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBind.animationTextureView.tickStop();
    }

    private void setupAds() {
        adsMan = new AdsManager(getContext());
        adsMan.setListener(this);
        adsMan.load();
    }

    private void setupButtonEvent() {
        mBind.btnShowAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsMan.startFullAds();
            }
        });
        mBind.btnShowAds.setEnabled(false);
        mBind.btnGoPremiumMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PremiumRewardActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    private void setupAnim() {

        mBind.animationTextureView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBind.animationTextureView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width  = mBind.animationTextureView.getMeasuredWidth();
                int height  = mBind.animationTextureView.getMeasuredHeight();

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.character_idle);
                Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,
                        (int)Util.convertDpToPixel(480f,getContext()),
                        (int)Util.convertDpToPixel(154f,getContext()),
                        false
                        );
                final float frameWidth = Util.convertDpToPixel(160f, getContext());
                final float frameHeight = Util.convertDpToPixel(154f, getContext());

                SpriteSheetDrawer spriteSheetDrawer = new SpriteSheetDrawer(
                        bitmap2,
                        frameWidth,
                        frameHeight, 3, 3)
                        .frequency(10)
                        .spriteLoop(true);

                DisplayObject bitmapDisplay = new DisplayObject();
                bitmapDisplay
                        .with(spriteSheetDrawer)
                        .tween()
                        .tweenLoop(true)
                        .transform((width - frameWidth)/2 - 50, (height - frameHeight)/2)
                        .toX(500, (width - frameWidth)/2 + 50, Ease.CUBIC_IN)
                        .toX(500, (width - frameWidth)/2 - 50, Ease.CUBIC_IN)
                        .end();
                mBind.animationTextureView
                        .addChild(bitmapDisplay)
                        .tickStart();
            }
        });


    }

    @Override
    public void onAdsLoaded() {
        mBind.btnShowAds.setEnabled(true);
    }

    @Override
    public void onAdsFinished(int nRet) {
        setupAnim();
        if(nRet == -1) {
            Toast.makeText(getContext(), "광고 리워드 받기에 실패 했습니다.", Toast.LENGTH_LONG).show();
            mBind.btnShowAds.setEnabled(false);
            adsMan.load();
        }

        if(nRet == 0) {
            Toast.makeText(getContext(), "광고 리워드를 받았습니다.", Toast.LENGTH_LONG).show();
            mBind.btnShowAds.setEnabled(false);
            adsMan.load();
        }
    }
}
