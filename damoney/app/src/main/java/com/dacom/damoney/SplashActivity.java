package com.dacom.damoney;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.dacom.damoney.Sign.MyPassport;
import com.dacom.damoney.Sign.SigninActivity;
import com.dacom.damoney.databinding.ActivitySplashBinding;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yaong.nnnyyy.nyhttphelper.HttpHelper;
import com.yaong.nnnyyy.nyhttphelper.HttpHelperListener;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding mBind;
    IntroAnimator animator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        MyPassport.getInstance().init(this);
        setupStatusBar();
        setupIntroAnim();

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
                    MyPassport.getInstance().loadToken(SplashActivity.this);
                    new HttpHelper().SetListener(new HttpHelperListener() {
                        @Override
                        public void onResponse(int nType, int nRet, String sResponse) {
                            if(nRet != 0) {
                                MyPassport.getInstance().deleteToken(SplashActivity.this);
                                GoSignin();
                                return;
                            }
                            try {
                                JSONObject obj = new JSONObject(sResponse);
                                Integer ret = obj.getInt("ret");
                                if(ret != 0) {
                                    MyPassport.getInstance().deleteToken(SplashActivity.this);
                                    GoSignin();
                                    return;
                                }
                                else {
                                    String newToken = obj.getString("token");
                                    MyPassport.getInstance().saveToken(SplashActivity.this, newToken);
                                    GoMain();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).Get(0, Global.BASE_URL + "/auth?token=" + MyPassport.getInstance().getToken());
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

    private void setupIntroAnim() {
        animator = new IntroAnimator(this, mBind.animview);
        animator.init();
    }

    private void GoSignin() {
        mBind.animview.removeAllChildren();
        Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void GoMain() {

        MyPassport.getInstance().RequestInfo(new MyPassport.RequestInfoListener() {
            @Override
            public void onResult(int nRet) {
                if(nRet != 0) {
                    GoSignin();
                    return;
                }

                mBind.animview.removeAllChildren();

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mBind.animview.tickStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBind.animview.tickStop();
    }
}
