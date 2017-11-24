package com.dacom.damoney;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dacom.damoney.databinding.ActivityCharInfoBinding;
import com.dacom.damoney.databinding.ActivityLevelUpBinding;

public class CharacterInfoActivity extends AppCompatActivity {
    ActivityCharInfoBinding mBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_char_info);
        mBind.touchclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
