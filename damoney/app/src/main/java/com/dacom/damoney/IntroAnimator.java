package com.dacom.damoney;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.ViewTreeObserver;

import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.drawer.SpriteSheetDrawer;
import com.daasuu.library.util.Util;

/**
 * Created by nnnyyy on 2017-11-23.
 */

public class IntroAnimator {
    Context mContext;
    FPSTextureView mTexView;
    Point ptViewRealSize;

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

                loadAnim(R.drawable.intro, 7, 5, 31);
            }
        });
    }

    protected void loadAnim(int resId, int cnt_per_row, int cnt_per_col, int frameCnt) {

        BitmapFactory.Options option = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resId, option);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap,
                (int) Util.convertPixelsToDp(w, mContext),
                (int)Util.convertPixelsToDp(h, mContext),
                true
        );

        bitmap.recycle();
        int scale = 5;
        int count_per_row = cnt_per_row;
        int count_per_col = cnt_per_col;
        int fw = w/count_per_row;
        int fh = h/count_per_col;
        final float frameWidth = Util.convertPixelsToDp(fw, mContext);
        final float frameHeight = Util.convertPixelsToDp(fh, mContext);
        SpriteSheetDrawer spriteSheetDrawer = new SpriteSheetDrawer(
                bitmapScaled,
                frameWidth,
                frameHeight, frameCnt, count_per_row)
                .frequency(2)
                .spriteLoop(false);
        DisplayObject bitmapDisplay = new DisplayObject();
        bitmapDisplay
                .with(spriteSheetDrawer)
                .tween()
                .tweenLoop(true)
                .transform((ptViewRealSize.x - frameWidth * scale)/2, (ptViewRealSize.y - frameHeight * scale)/2)
                .end();

        bitmapDisplay.getAnimParameter().scaleX = scale;
        bitmapDisplay.getAnimParameter().scaleY = scale;

        mTexView
                .addChild(bitmapDisplay)
                .tickStart();
    }
}
