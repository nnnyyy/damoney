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
import android.widget.RelativeLayout;

import com.dacom.damoney.databinding.VerticalScrollViewBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nnnyy on 2017-12-03.
 */

public class VerticalScrollView extends RelativeLayout {
    VerticalScrollViewBinding mBind;
    ArrayList<Data> aList;
    private int itemCount = 0;

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
        final Data d1 = aList.get(0);
        final Data d2 = aList.get(1);
        final Data d3 = aList.get(2);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Picasso.with(getContext()).load(Global.BASE_URL + d1.iconURL).into(mBind.iv1);
                mBind.tvTitle1.setText(d1.sTitle);
                mBind.tvSubTitle1.setText(d1.sSubTitle);
                Picasso.with(getContext()).load(Global.BASE_URL + d2.iconURL).into(mBind.iv2);
                mBind.tvTitle2.setText(d2.sTitle);
                mBind.tvSubTitle2.setText(d2.sSubTitle);
                Picasso.with(getContext()).load(Global.BASE_URL + d3.iconURL).into(mBind.iv3);
                mBind.tvTitle3.setText(d3.sTitle);
                mBind.tvSubTitle3.setText(d3.sSubTitle); 
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
