package com.dacom.damoney;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dacom.damoney.Sign.MyPassport;
import com.dacom.damoney.databinding.ActivityCharInfoBinding;

import java.util.ArrayList;

public class CharacterInfoActivity extends AppCompatActivity {
    ActivityCharInfoBinding mBind;
    ArrayList<CharInfo> aCharacters = new ArrayList<>();
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
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_char_info);
        /*mBind.touchclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        init();
        refreshStat();
        setupButtonEvent();
    }

    protected void init() {
        aCharacters.add(new CharInfo(R.drawable.login_char, true));
        aCharacters.add(new CharInfo(R.drawable.cc_hidden_1, false));
        aCharacters.add(new CharInfo(R.drawable.cc_hidden_2, false));
        aCharacters.add(new CharInfo(R.drawable.cc_hidden_3, false));
        mBind.ivCharacter.setImageResource(aCharacters.get(selectedIndex).res_id);

        mBind.lvInfo.setText("Lv." + MyPassport.getInstance().nLevel + " " + MyPassport.getInstance().sNick);
    }

    protected void refreshStat() {
        int level = MyPassport.getInstance().nLevel;
        float bonus_cash_earn = 0.01f * level;
        float bonus_item_earn = 0.1f * level;
        float bonus_exp_earn = 0.1f * level;

        mBind.tvBonusCashEarn.setText(String.format("%.2f%%", (100.0f + bonus_cash_earn)));
        mBind.tvBonusItemEarnRate.setText(String.format("%.2f%%", (100.0f + bonus_item_earn)));
        mBind.tvBonusExpEarn.setText(String.format("%.2f%%", (100.0f + bonus_exp_earn)));
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

        mBind.btnExit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                /*AlertManager.ShowOk(CharacterInfoActivity.this, "", "캐릭터가 변경 되었습니다.", "닫기", new AlertManager.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/
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
        //mBind.btnExit.setEnabled(aCharacters.get(selectedIndex).bOpen);
    }
}
