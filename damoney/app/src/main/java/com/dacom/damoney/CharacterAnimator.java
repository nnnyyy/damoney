package com.dacom.damoney;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.drawer.SpriteSheetDrawer;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nnnyyy on 2017-11-16.
 */

public class CharacterAnimator {

    public static CharacterAnimator getInstance() {
        if(obj == null) {
            obj = new CharacterAnimator();
        }

        return obj;
    }

    private static CharacterAnimator obj;

    class AnimStateObj {
        Bitmap scaledBitmap;       //  애니메이션 비트맵
        SpriteSheetDrawer drawer;   //  애니메이션 플레이어
        DisplayObject dio;           //  디스플레이 오브젝트
        Point ptSize;               //  비트맵 사이즈
        Point ptFrameSize;         //  프레임 당 사이즈
    }

    enum CharacterState {
        CS_IDLE,
        CS_EAT,
        CS_QUESTION,
        CS_GREAT,
    };

    Context mContext;
    FPSTextureView textureView;
    Point ptViewRealSize;
    boolean bLoad = false;
    Map<CharacterState, AnimStateObj> mAnimMan = new HashMap<>();

    private CharacterAnimator() {
    }

    public void init(Context context, FPSTextureView texview) {

        mContext = context;
        textureView = texview;

        if(bLoad)  {
            getTextureView().removeAllChildren();
            getTextureView()
                    .addChild(mAnimMan.get(CharacterState.CS_EAT).dio)
                    .tickStart();
            return;
        }

        textureView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(mContext== null ) return;
                getTextureView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width  = getTextureView().getMeasuredWidth();
                int height  = getTextureView().getMeasuredHeight();

                ptViewRealSize = new Point(width, height);
                getTextureView().removeAllChildren();

                loadAnims();
                bLoad = true;

                getTextureView().removeAllChildren();
                getTextureView()
                        .addChild(mAnimMan.get(CharacterState.CS_QUESTION).dio)
                        .tickStart();
            }
        });
    }

    protected FPSTextureView getTextureView() { return textureView; }

    protected void loadAnims() {
        loadAnim(CharacterState.CS_QUESTION, R.drawable.c0_question, 5, 5, 22);
        loadAnim(CharacterState.CS_IDLE, R.drawable.c0_idle, 4, 3, 12);
        loadAnim(CharacterState.CS_EAT, R.drawable.c0_eat, 5, 4, 20);
        loadAnim(CharacterState.CS_GREAT, R.drawable.c0_great, 4, 3, 12);
    }

    protected void loadAnim(CharacterState state, int resId, int cnt_per_row, int cnt_per_col, int frameCnt) {
        if(mAnimMan.containsKey(state)) return;

        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resId, option);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap,
                (int)Util.convertPixelsToDp(w, mContext),
                (int)Util.convertPixelsToDp(h, mContext),
                false
                );
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
                .frequency(4)
                .spriteLoop(true);
        DisplayObject bitmapDisplay = new DisplayObject();
        bitmapDisplay
                .with(spriteSheetDrawer)
                .tween()
                .tweenLoop(true)
                .transform((ptViewRealSize.x - frameWidth)/2, (ptViewRealSize.y - frameHeight)/2)
                .end();

        AnimStateObj aso = new AnimStateObj();
        aso.drawer = spriteSheetDrawer;
        aso.dio = bitmapDisplay;
        mAnimMan.put(state, aso);
    }
}
