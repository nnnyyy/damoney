package com.dacom.damoney;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.drawer.SpriteSheetDrawer;
import com.daasuu.library.util.Util;

/**
 * Created by nnnyyy on 2017-11-23.
 */

public class IntroAnimator {
    Context mContext;
    FPSTextureView mTexView;
    Point ptViewRealSize;
    AnimEventListener aelistener;
    Bitmap introBitmap;

    public interface AnimEventListener {
        public void onAnimationEnd();
    }

    public IntroAnimator(Context context, FPSTextureView texview) {
        mContext = context;
        mTexView = texview;
    }

    public void init() {
        mTexView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(mContext== null ) return;
                mTexView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width  = mTexView.getMeasuredWidth();
                int height  = mTexView.getMeasuredHeight();

                ptViewRealSize = new Point(width, height);
                mTexView.removeAllChildren();

                loadAnim(R.drawable.intronew, 6, 5, 27);
            }
        });
    }

    public void setListener(AnimEventListener listener) {
        aelistener = listener;
    }

    protected void loadAnim(int resId, int cnt_per_row, int cnt_per_col, int frameCnt) {
        try {
            BitmapFactory.Options option = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resId, option);
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            int count_per_row = cnt_per_row;
            int count_per_col = cnt_per_col;
            int fw = w / count_per_row;
            int fh = h / count_per_col;
            float fRatio = (float) fw / (float) fh;   //  캐릭터 프레임 종횡

            // ptViewRealSize 는 기기에 배당된 실제 TextureView 픽셀 사이즈
            // 인트로 하나당 width 가 170dp 를 차지하는게 가장 이상적
            int nRecommFrameW = (int) Util.convertDpToPixel(170, mContext);
            ;
            int nRecommFrameH = (int) ((float) nRecommFrameW * (1.0 / fRatio));
            int nRecommW = nRecommFrameW * count_per_row;
            int nRecommH = nRecommFrameH * count_per_col;

            introBitmap = Bitmap.createScaledBitmap(bitmap,
                    nRecommW,
                    nRecommH,
                    true
            );
            bitmap.recycle();
            SpriteSheetDrawer spriteSheetDrawer = new SpriteSheetDrawer(
                    introBitmap,
                    nRecommFrameW,
                    nRecommFrameH, frameCnt, count_per_row)
                    .frequency(3)
                    .spriteLoop(false);

            spriteSheetDrawer.spriteAnimationEndCallBack(new AnimCallBack() {
                @Override
                public void call() {
                    if( aelistener != null ) {
                        aelistener.onAnimationEnd();
                    }
                }
            });

            DisplayObject bitmapDisplay = new DisplayObject();
            bitmapDisplay
                    .with(spriteSheetDrawer)
                    .tween()
                    .tweenLoop(true)
                    .transform((ptViewRealSize.x - nRecommFrameW) / 2, (ptViewRealSize.y - nRecommFrameH) / 2)
                    .end();

            mTexView
                    .addChild(bitmapDisplay)
                    .tickStart();

        } catch (OutOfMemoryError err) {
            Log.e("IntroAnimator","Loading Failed : " + err);
            if( aelistener != null ) {
                aelistener.onAnimationEnd();
            }
            return;
        }
    }

    public void clear() {
        if(mTexView != null) {
            if(introBitmap != null)
                introBitmap.recycle();
            mTexView.clearAnimation();
            mTexView.removeAllChildren();
        }

    }
}
