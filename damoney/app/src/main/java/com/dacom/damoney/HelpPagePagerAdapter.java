package com.dacom.damoney;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by nnnyy on 2017-12-13.
 */

public class HelpPagePagerAdapter extends PagerAdapter {
    ArrayList<View> aList = new ArrayList<>();

    @Override
    public int getCount() {
        return aList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = aList.get(position);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    public void setList(ArrayList<View> _list) {
        aList = _list;
        notifyDataSetChanged();
    }
}
