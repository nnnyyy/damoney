package com.dacom.damoney;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.dacom.damoney.databinding.ActivityHelpPageBinding;
import com.dacom.damoney.databinding.HelpPageItemBinding;

import java.util.ArrayList;

public class HelpPageActivity extends AppCompatActivity {
    ActivityHelpPageBinding mBind;
    HelpPagePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_help_page);

        init();
    }

    private void init() {
        adapter = new HelpPagePagerAdapter();
        mBind.pager.setAdapter(adapter);
        mBind.indicator.setViewPager(mBind.pager);

        setViewPagerContents();
    }

    private void setViewPagerContents() {
        ArrayList<View> alist = new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater == null) return;
        HelpPageItemBinding bind = DataBindingUtil.inflate(inflater, R.layout.help_page_item, mBind.pager, false);
        alist.add(bind.getRoot());
        bind = DataBindingUtil.inflate(inflater, R.layout.help_page_item, mBind.pager, false);
        bind.ivThumbnail.setBackgroundResource(R.drawable.helppage_2);
        alist.add(bind.getRoot());
        bind = DataBindingUtil.inflate(inflater, R.layout.help_page_item, mBind.pager, false);
        bind.ivThumbnail.setBackgroundResource(R.drawable.helppage_3);
        alist.add(bind.getRoot());
        bind = DataBindingUtil.inflate(inflater, R.layout.help_page_item, mBind.pager, false);
        bind.ivThumbnail.setBackgroundResource(R.drawable.helppage_4);
        alist.add(bind.getRoot());
        bind = DataBindingUtil.inflate(inflater, R.layout.help_page_item, mBind.pager, false);
        bind.ivThumbnail.setBackgroundResource(R.drawable.helppage_5);
        alist.add(bind.getRoot());
        bind = DataBindingUtil.inflate(inflater, R.layout.help_page_item, mBind.pager, false);
        bind.ivThumbnail.setBackgroundResource(R.drawable.helppage_6);
        alist.add(bind.getRoot());
        bind = DataBindingUtil.inflate(inflater, R.layout.help_page_item, mBind.pager, false);
        bind.ivThumbnail.setBackgroundResource(R.drawable.helppage_7);
        alist.add(bind.getRoot());
        bind = DataBindingUtil.inflate(inflater, R.layout.help_page_item, mBind.pager, false);
        bind.ivThumbnail.setBackgroundResource(R.drawable.helppage_8);
        alist.add(bind.getRoot());
        bind = DataBindingUtil.inflate(inflater, R.layout.help_page_item, mBind.pager, false);
        bind.ivThumbnail.setBackgroundResource(R.drawable.helppage_9);
        alist.add(bind.getRoot());
        bind = DataBindingUtil.inflate(inflater, R.layout.help_page_item, mBind.pager, false);
        bind.ivThumbnail.setBackgroundResource(R.drawable.helppage_10);
        alist.add(bind.getRoot());
        adapter.setList(alist);
    }
}
