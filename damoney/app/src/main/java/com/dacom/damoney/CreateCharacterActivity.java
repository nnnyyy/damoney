package com.dacom.damoney;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dacom.damoney.Sign.SigninActivity;
import com.dacom.damoney.databinding.ActivityCreateCharacterBinding;

import java.util.ArrayList;

public class CreateCharacterActivity extends AppCompatActivity {
    ActivityCreateCharacterBinding mBind;
    String id;
    String pw;

    ArrayList<CharacterInfoActivity.CharInfo> aCharacters = new ArrayList<>();
    int selectedIndex = 0;

    public static class CharInfo {
        public CharInfo(int _resid, boolean _open) {
            res_id = _resid;
            bOpen = _open;
        }

        public int res_id;
        public boolean bOpen;
    }

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

        init();
        setupButtonEvent();
    }

    protected void init() {
        aCharacters.add(new CharacterInfoActivity.CharInfo(R.drawable.login_char, true));
        aCharacters.add(new CharacterInfoActivity.CharInfo(R.drawable.cc_hidden_1, false));
        aCharacters.add(new CharacterInfoActivity.CharInfo(R.drawable.cc_hidden_2, false));
        aCharacters.add(new CharacterInfoActivity.CharInfo(R.drawable.cc_hidden_3, false));
        mBind.ivCharacter.setImageResource(aCharacters.get(selectedIndex).res_id);
    }

    protected void setupButtonEvent() {
        mBind.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCharacterImage(true);
            }
        });

        mBind.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCharacterImage(false);
            }
        });
    }

    protected void moveCharacterImage(boolean isLeft) {
        if( isLeft ) {
            selectedIndex--;
            if(selectedIndex < 0 ) selectedIndex = aCharacters.size() - 1;
        }
        else {
            selectedIndex++;
            if(selectedIndex >= aCharacters.size() ) selectedIndex = 0;
        }

        mBind.ivCharacter.setImageResource(aCharacters.get(selectedIndex).res_id);
        mBind.btnStartApp.setEnabled(aCharacters.get(selectedIndex).bOpen);
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
