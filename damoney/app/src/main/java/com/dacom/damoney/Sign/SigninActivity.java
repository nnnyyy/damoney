package com.dacom.damoney.Sign;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dacom.damoney.MainActivity;
import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivitySigninBinding;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yaong.nnnyyy.nyhttphelper.HttpHelper;
import com.yaong.nnnyyy.nyhttphelper.HttpHelperListener;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {
    ActivitySigninBinding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_signin);
        mBind.setItem(this);
        setupControllers();
    }

    private void setupControllers() {
        Window window = getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        if (android.os.Build.VERSION.SDK_INT > 19)
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorMain));
        else {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintColor(R.color.colorMain);
        }
    }

    public void onBtnLogin(View v) {
        if(!validate()) {
            return;
        }

        Map<String, Object> mParams = new HashMap<String, Object>();
        mParams.put("id", mBind.etId.getText().toString());
        mParams.put("pw", mBind.etPw.getText().toString());

        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String sResponse) {
                Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getApplicationContext().startActivity(intent);
            }
        }).Post(0, "http://10.0.2.2:3000/signin", mParams);
    }

    public void onBtnSignUp(View v) {
        Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
        SigninActivity.this.startActivity(intent);
    }

    private boolean validate() {
        String sId = mBind.etId.getText().toString();
        String sPw = mBind.etPw.getText().toString();
        if(sId.isEmpty() || sPw.isEmpty()) {
            Toast.makeText(getApplicationContext(), "아이디나 비밀번호를 입력 해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
