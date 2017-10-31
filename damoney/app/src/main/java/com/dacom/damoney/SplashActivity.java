package com.dacom.damoney;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.dacom.damoney.Sign.SigninActivity;
import com.dacom.damoney.databinding.ActivitySplashBinding;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yaong.nnnyyy.nyhttphelper.HttpHelper;
import com.yaong.nnnyyy.nyhttphelper.HttpHelperListener;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding mBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        setupStatusBar();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!Storage.have(SplashActivity.this, "AccessToken")) {
                    Log.i("TestLog", "no have token");
                    GoSignin();
                }
                else {
                    String sToken = Storage.load(SplashActivity.this, "AccessToken");
                    new HttpHelper().SetListener(new HttpHelperListener() {
                        @Override
                        public void onResponse(int nType, int nRet, String sResponse) {
                            Log.i("TestLog1", sResponse);

                            if(nRet != 0) {
                                GoSignin();
                                return;
                            }

                            Log.i("TestLog2", sResponse);

                            try {
                                JSONObject obj = new JSONObject(sResponse);
                                Integer ret = obj.getInt("ret");
                                if(ret != 0) {
                                    GoSignin();
                                    return;
                                }
                                else {
                                    GoMain();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).Get(0, "http://4seasonpension.com:3003/auth?token=" + sToken);
                }

            }
        }).start();
    }

    private void setupStatusBar() {
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

    private void GoSignin() {
        Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void GoMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
