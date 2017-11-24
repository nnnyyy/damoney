package com.dacom.damoney.Sign;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.dacom.damoney.AlertManager.AlertManager;
import com.dacom.damoney.CreateCharacterActivity;
import com.dacom.damoney.R;
import com.dacom.damoney.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    private static final String Passwrod_PATTERN = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{8,16}$";
    ActivitySignupBinding mBind;
    boolean isAuthed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        mBind.setItem(this);
        setupViewItems();
    }

    private void setupViewItems() {
        mBind.etId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    Toast.makeText(getApplicationContext(), "Name Focus Changed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBind.btnGetAuthnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인증 서버에 요청 날림.
                mBind.btnVerifyAuthnumber.setEnabled(true);
                mBind.btnGetAuthnumber.setEnabled(false);
                mBind.etPhoneAuth2.setEnabled(true);
                mBind.etPhoneAuth2.setText("443322");
            }
        });

        mBind.btnVerifyAuthnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBind.etPhoneAuth2.getText().toString().isEmpty()) return;

                mBind.circleBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                // 확인 받아서 맞으면 인증 확인 완료 플래그 올림.
                mBind.circleBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                AlertManager.ShowOk(SignupActivity.this, "알림", "인증이 완료 되었습니다.", "닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBind.etPhoneAuth.setEnabled(false);
                        mBind.etPhoneAuth2.setEnabled(false);
                        mBind.btnVerifyAuthnumber.setEnabled(false);
                        mBind.btnGetAuthnumber.setEnabled(false);
                        isAuthed = true;
                    }
                });

                /*Map<String, Object> mParams = new HashMap<String, Object>();
                mParams.put("id", "test");
                mParams.put("pw", "testpw");
                mParams.put("nick", "nnnyyy");
                new HttpHelper().SetListener(new HttpHelperListener() {
                    @Override
                    public void onResponse(int nType, int nRet, String sResponse) {
                        Log.i("Test Log", sResponse);
                        if(nRet == 0) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    mBind.circleBar.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    AlertManager.ShowOk(SignupActivity.this, "알림", "인증이 완료 되었습니다.", "닫기", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mBind.etPhoneAuth.setEnabled(false);
                                            mBind.etPhoneAuth2.setEnabled(false);
                                            mBind.btnVerifyAuthnumber.setEnabled(false);
                                            mBind.btnGetAuthnumber.setEnabled(false);
                                            isAuthed = true;
                                        }
                                    });
                                }
                            });
                        }
                        else {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    mBind.circleBar.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            });
                        }
                    }
                }).Post(0, "http://10.0.2.2:3000/signup", mParams);*/
            }
        });

        //  초기 비활성 버튼들
        mBind.etPhoneAuth2.setEnabled(false);
        mBind.btnVerifyAuthnumber.setEnabled(false);

        mBind.circleBar.setVisibility(View.GONE);
    }

    private boolean validate() {
        String sId = mBind.etId.getText().toString();
        String sPw = mBind.etPw.getText().toString();
        String sPwre = mBind.etPwre.getText().toString();
        mBind.ethId.setText("");
        mBind.ethPw.setText("");
        mBind.ethPwre.setText("");

        if(sId.isEmpty() || sId.length() < 4 || sId.length() > 10) {
            mBind.ethId.setText("4~10 Characters");
            return false;
        }

        if(sPw.isEmpty() || sPw.length() < 6 || sPw.length() > 10 ) {
            mBind.ethPw.setText("6~10 Characters");
            return false;
        }
        if(!sPw.matches(Passwrod_PATTERN)) {
            mBind.ethPw.setText("character and number please");
            return false;
        }

        if(sPwre.compareTo(sPw) != 0) {
            mBind.ethPwre.setText("not match pw");
            return false;
        }

        if(!isAuthed) {
            AlertManager.ShowOk(SignupActivity.this, "알림", "휴대폰 인증이 필수입니다", "닫기", null);
            return false;
        }

        if(!mBind.cbTerms1.isChecked() || !mBind.cbTerms2.isChecked()) {
            AlertManager.ShowOk(SignupActivity.this, "알림", "약관에 동의가 필요합니다", "닫기", null);
            return false;
        }

        return true;
    }

    public void onSignUp(View v) {
        if(!validate()) {
            Toast.makeText(getApplicationContext(), "요구 조건에 부합하지 않아 가입을 진행할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        String sId = mBind.etId.getText().toString();
        String sPw = mBind.etPw.getText().toString();
        Intent intent = new Intent(SignupActivity.this, CreateCharacterActivity.class);
        intent.putExtra("id", sId);
        intent.putExtra("pw", sPw);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }
}
