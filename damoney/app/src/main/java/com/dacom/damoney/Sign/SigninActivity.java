package com.dacom.damoney.Sign;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dacom.damoney.Global;
import com.dacom.damoney.MainActivity;
import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivitySigninBinding;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yaong.nnnyyy.nyhttphelper.HttpHelper;
import com.yaong.nnnyyy.nyhttphelper.HttpHelperListener;

import org.json.JSONException;
import org.json.JSONObject;

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
                try {
                    Log.i("onResponse", sResponse);
                    JSONObject obj = new JSONObject(sResponse);
                    Integer jresult = obj.getInt("ret");
                    if(jresult != 0) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SigninActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    String sTokenString = obj.getString("access_token");
                    Log.i("onResponse", sTokenString);
                    MyPassport.getInstance().saveToken(SigninActivity.this, sTokenString);

                    MyPassport.getInstance().RequestInfo(new MyPassport.RequestInfoListener() {
                        @Override
                        public void onResult(int nRet) {
                            if(nRet != 0) {
                                finish();
                                return;
                            }

                            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).Post(0, Global.BASE_URL + "/signin", mParams);
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
