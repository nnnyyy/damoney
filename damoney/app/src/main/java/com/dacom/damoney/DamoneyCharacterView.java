package com.dacom.damoney;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by nnnyy on 2017-10-15.
 */

public class DamoneyCharacterView extends SurfaceView implements SurfaceHolder.Callback {
    Context mContext;
    SurfaceHolder mHolder;
    MainThread mThread;

    public DamoneyCharacterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mThread = new MainThread();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mThread.Stop();
        boolean retry = true;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    class MainThread extends Thread {
        boolean bRunning = false;
        public void Stop() {
            bRunning = false;
        }

        @Override
        public void run() {
            Canvas canvas = null;
            bRunning = true;
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);

            while(bRunning) {
                try {
                    canvas = mHolder.lockCanvas(null);
                    if(canvas != null) {
                        synchronized (mHolder) {
                            sleep(100);
                            canvas.drawRect(0,0,100,100,paint);
                            bRunning = false;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if(canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
