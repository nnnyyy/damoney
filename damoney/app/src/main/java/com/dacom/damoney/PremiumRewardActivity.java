package com.dacom.damoney;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dacom.damoney.databinding.ActivityPremiumRewardBinding;

public class PremiumRewardActivity extends AppCompatActivity {
    ActivityPremiumRewardBinding mBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_premium_reward);
    }
}
