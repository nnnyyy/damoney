package com.dacom.damoney.Advertisement;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivityAdsTypingBinding;

public class AdsTypingActivity extends AppCompatActivity {
    ActivityAdsTypingBinding mBind;
    boolean bRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_ads_typing);
        mBind.setItem(this);
        setupDemoAds();
    }

    private void setupDemoAds() {
        InputMethodManager imm = (InputMethodManager) getSystemService
                (Context.INPUT_METHOD_SERVICE);

        mBind.etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBind.etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("onEditorAction", "text : " + mBind.etInput.getText().toString());
                // CheckText();
                mBind.etInput.setText("");
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bRunning = false;
        if(AdsManager.listener != null)
            AdsManager.listener.onAdsFinished(-1);
    }
}
