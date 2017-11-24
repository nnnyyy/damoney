package com.dacom.damoney;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dacom.damoney.Sign.SigninActivity;
import com.dacom.damoney.databinding.ActivityCreateCharacterBinding;

public class CreateCharacterActivity extends AppCompatActivity {
    ActivityCreateCharacterBinding mBind;
    String id;
    String pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_create_character);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        pw = intent.getStringExtra("pw");

        mBind.btnStartApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSignup();
            }
        });
    }

    protected void requestSignup() {
        String sCharacterName = mBind.etCharName.getText().toString();
        if(sCharacterName.isEmpty() || sCharacterName == "") {
            CustomToast.Alert(getApplicationContext(), "캐릭터 이름이 잘못 되었습니다.");
            return;
        }
        DamoneyHttpHelper.Signup(id, pw, sCharacterName, new DamoneyHttpHelper.MyCallbackInterface() {
            @Override
            public void onResult(int nRet) {
                if(nRet == 0) {
                    Intent intent = new Intent(CreateCharacterActivity.this, SigninActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    getApplicationContext().startActivity(intent);

                }
            }
        });
    }
}
