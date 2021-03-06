package com.dacom.damoney;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dacom.damoney.Sign.MyPassport;
import com.dacom.damoney.databinding.ActivityGetRewardBinding;
import com.squareup.picasso.Picasso;

public class GetRewardActivity extends AppCompatActivity {
    ActivityGetRewardBinding mBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_get_reward);
        init();
    }

    public void init() {
        mBind.llResult.setVisibility(View.GONE);
        mBind.gachabox.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            open();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        /*mBind.gachabox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBind.gachabox.setOnClickListener(null);
                open();
            }
        });*/
    }

    public void open() {
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //  이 때 가챠 돌림
                final GachaBoxInfo boxinfo = new GachaBoxInfo();
                DamoneyHttpHelper.UseGachaBox(boxinfo, new DamoneyHttpHelper.MyCallbackInterface() {
                    @Override
                    public void onResult(int nRet) {
                        if(nRet != 0 ) {
                            // 실패
                            finish();
                            return;
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                mBind.tvName.setText(boxinfo.sName);
                                mBind.tvGrade.setText(Global.GetGradeName(boxinfo.nGrade));
                                Picasso.with(getApplicationContext()).load(Global.BASE_URL + boxinfo.iconpath).into(mBind.iconimage);
                                mBind.gachabox.setVisibility(View.GONE);
                                mBind.llResult.setVisibility(View.VISIBLE);
                                MyPassport.getInstance().RequestInfo(new MyPassport.RequestInfoListener() {
                                    @Override
                                    public void onResult(int nRet) {
                                        if(nRet == 0 && MyPassport.getInstance().nGachaCnt > 0) {
                                            mBind.btnClose.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    init();
                                                }
                                            });
                                        }
                                        else {
                                            mBind.btnClose.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    finish();
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBind.gachabox.startAnimation(shake);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
