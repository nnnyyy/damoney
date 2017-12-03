package com.dacom.damoney;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dacom.damoney.databinding.VerticalScrollViewBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nnnyy on 2017-12-03.
 */

public class VerticalScrollView extends RelativeLayout {
    VerticalScrollViewBinding mBind;
    ArrayList<Data> aList;
    private int itemCount = 0;
    private int curIdx = 0;
    private boolean bRunning = false;
    Thread t = null;

    public VerticalScrollView(Context context) {
        super(context);
        init(context);
    }

    public VerticalScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VerticalScrollView, 0, 0);
        itemCount = a.getInteger(R.styleable.VerticalScrollView_itemCount, 3);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        mBind = DataBindingUtil.inflate(inflater, R.layout.vertical_scroll_view, this, true);

        aList = new ArrayList<>();
    }

    public void add(String iconURL, String sTitle, String sSubTitle) {
        Data d = new Data(iconURL, sTitle, sSubTitle);
        aList.add(d);
    }

    public void start() {
        if(aList.size() == 0) return;
        if(t != null) {
            stop();
        }

        bRunning = true;

        t = new Thread(new Runnable() {
            @Override
            public void run() {
                long time = 0;
                while(bRunning) {
                    if(System.currentTimeMillis() - time >= 6000) {
                        for(int i = 0 ; i < 3 ; ++i) {
                            if(curIdx >= aList.size()) {
                                curIdx = 0;
                            }
                            Data d =aList.get(curIdx);
                            set(i, d);
                            curIdx++;
                        }
                        time = System.currentTimeMillis();
                    }
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();
    }

    public void stop() {
        if(t == null) return;

        bRunning = false;
        try {
            t.join();
            t = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void set(int idx, final Data d) {
        TextView tvTitle = null;
        TextView tvSubTitle = null;
        ImageView iv = null;

        switch(idx) {
            case 0:
                tvTitle = mBind.tvTitle1;
                tvSubTitle = mBind.tvSubTitle1;
                iv = mBind.iv1;
                break;
            case 1:
                tvTitle = mBind.tvTitle2;
                tvSubTitle = mBind.tvSubTitle2;
                iv = mBind.iv2;
                break;
            case 2:
                tvTitle = mBind.tvTitle3;
                tvSubTitle = mBind.tvSubTitle3;
                iv = mBind.iv3;
                break;
            default:
                tvTitle = mBind.tvTitle1;
                tvSubTitle = mBind.tvSubTitle1;
                iv = mBind.iv1;
                break;
        }

        final ImageView finalIv = iv;
        final TextView finalTvTitle = tvTitle;
        final TextView finalTvSubTitle = tvSubTitle;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Picasso.with(getContext()).load(Global.BASE_URL + d.iconURL).into(finalIv, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        finalIv.setImageResource(R.drawable.icon_coin);
                    }
                });
                finalTvTitle.setText(d.sTitle);
                finalTvSubTitle.setText(d.sSubTitle);
            }
        });
    }

    public static class Data {
        public Data(String _u, String _t, String _s) { iconURL = _u; sTitle = _t; sSubTitle = _s;}
        public String iconURL;
        public String sTitle;
        public String sSubTitle;
    }
}
