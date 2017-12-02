package com.dacom.damoney;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dacom.damoney.databinding.ActivityCouponUseBinding;
import com.squareup.picasso.Picasso;

public class CouponUseActivity extends AppCompatActivity {
    ActivityCouponUseBinding mBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_coupon_use);
        Intent intent = getIntent();
        String sTitle = intent.getStringExtra("title");
        String sSubTitle = intent.getStringExtra("subtitle");
        String sIconPath = intent.getStringExtra("iconpath");
        mBind.tvTitle.setText(sTitle);
        mBind.tvSubTitle.setText(sSubTitle);
        Picasso.with(getApplicationContext()).load(Global.BASE_URL + sIconPath).into(mBind.ivThumbnail);
        mBind.touchclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
