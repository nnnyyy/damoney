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

    public static class AnimInfo {
        public AnimInfo(int _ri, int _colcnt, int _rowcnt, int _framecnt) {
            resId = _ri;
            colCnt = _colcnt;
            rowCnt = _rowcnt;
            frameCnt = _framecnt;
        }
        public int resId;
        public int colCnt;
        public int rowCnt;
        public int frameCnt;
    }

    enum CharacterState {
        CS_NONE,
        CS_IDLE,
        CS_EAT,
        CS_QUESTION,
        CS_GREAT,
    };

    Context mContext;
    FPSTextureView textureView = null;
    Point ptViewRealSize;
    DisplayObject currentObject;
    boolean bLoad = false;
    CharacterState reservState = CharacterState.CS_NONE;
    CharacterState curState = CharacterState.CS_NONE;
    Map<CharacterState, AnimInfo> mAnimMan = new HashMap<>();

    private CharacterAnimator() {
    }

    public void init(Context context, FPSTextureView texview) {

        mContext = context;
        textureView = texview;

        if(bLoad)  {
            changeAnim(CharacterState.CS_IDLE);
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

                if(reservState != CharacterState.CS_NONE)
                    changeAnim(reservState);
            }
        });
    }

    protected FPSTextureView getTextureView() { return textureView; }

    protected void loadAnims() {
        //loadAnim(CharacterState.CS_QUESTION, R.drawable.c0_question, 5, 5, 22);
        loadAnim(CharacterState.CS_IDLE, R.drawable.c0_idle, 4, 3, 12);
        //loadAnim(CharacterState.CS_EAT, R.drawable.c0_eat, 5, 4, 20);
        //loadAnim(CharacterState.CS_GREAT, R.drawable.c0_great, 4, 3, 12);
    }

    protected void loadAnim(CharacterState state, int resId, int cnt_per_row, int cnt_per_col, int frameCnt) {
        if(mAnimMan.containsKey(state)) return;
        mAnimMan.put(state, new AnimInfo(resId, cnt_per_row, cnt_per_col, frameCnt));
    }

    public void changeAnim(CharacterState state) {
        if( curState == state ) {
            getTextureView().removeAllChildren();
            getTextureView()
                    .addChild(currentObject)
                    .tickStart();
            return;
        }
        if(currentObject != null) {
            getTextureView().tickStop();
            getTextureView().removeChild(currentObject);
        }

        if(!bLoad) {
            reservState = state;
            return;
        }

        AnimInfo info = mAnimMan.get(state);
        if(info == null) return;

        BitmapFactory.Options option = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), info.resId, option);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int count_per_row = info.colCnt;
        int count_per_col = info.rowCnt;
        int fw = w/count_per_row;
        int fh = h/count_per_col;
        float fRatio = (float)fw / (float)fh;   //  캐릭터 프레임 종횡

        // ptViewRealSize 는 기기에 배당된 실제 TextureView 픽셀 사이즈
        // 캐릭터 하나당 width 가 1/4를 차지하는게 가장 이상적
        int ptd = (int) Util.convertPixelsToDp(ptViewRealSize.x, mContext);
        int nRecommFrameW = (int)Util.convertDpToPixel(ptd / 3, mContext);;
        int nRecommFrameH = (int)((float)nRecommFrameW * ( 1.0 / fRatio));
        int nRecommW = nRecommFrameW * count_per_row;
        int nRecommH = nRecommFrameH * count_per_col;

        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap,
                nRecommW,
                nRecommH,
                true
        );
        bitmap.recycle();
        SpriteSheetDrawer spriteSheetDrawer = new SpriteSheetDrawer(
                bitmapScaled,
                nRecommFrameW,
                nRecommFrameH, info.frameCnt, count_per_row)
                .frequency(2)
                .spriteLoop(true);
        DisplayObject bitmapDisplay = new DisplayObject();
        bitmapDisplay
                .with(spriteSheetDrawer)
                .tween()
                .tweenLoop(true)
                .transform((ptViewRealSize.x - nRecommFrameW )/2, (ptViewRealSize.y - nRecommFrameH ) - Util.convertDpToPixel(16, mContext))
                .end();

        currentObject = bitmapDisplay;
        curState = state;

        getTextureView()
                .addChild(currentObject)
                .tickStart();
    }

    public void clear() {
        bLoad = false;
        if(textureView != null) {
            textureView.clearAnimation();
            textureView.removeAllChildren();
        }
        mAnimMan.clear();
        currentObject = null;
        curState = CharacterState.CS_NONE;
    }
}
