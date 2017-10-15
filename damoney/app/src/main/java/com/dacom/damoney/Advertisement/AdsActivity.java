package com.dacom.damoney.Advertisement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dacom.damoney.R;

public class AdsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
    }

    private void setupDemoAds() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(-1);
    }
}
