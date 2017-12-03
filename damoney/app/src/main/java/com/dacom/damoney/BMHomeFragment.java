package com.dacom.damoney;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dacom.damoney.Advertisement.AdsManager;
import com.dacom.damoney.Advertisement.AdsResultListener;
import com.dacom.damoney.Sign.MyPassport;
import com.dacom.damoney.databinding.FragmentBmhomeBinding;

import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMHomeFragment extends Fragment implements AdsResultListener{
    FragmentBmhomeBinding mBind;
    AdsManager adsMan;
    boolean bDirectShowAds = false;
    public BMHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_bmhome, container, false);
        setupAds();
        setupButtonEvent();
        setupAnim();
        refreshInfo();

        final BonusManager bm = new BonusManager();
        DamoneyHttpHelper.getBonusInfo(bm, new DamoneyHttpHelper.MyCallbackInterface() {
            @Override
            public void onResult(int nRet) {
                if(nRet == 0) {
                    ArrayList<BonusItemBase> a = bm.getBonusList();
                    for(int i = 0 ; i < a.size() ; ++i) {
                        BonusItemBase item = a.get(i);
                        if(item.type == BonusItemBase.BIType.BI_SECTION) continue;
                        BonusItemData data = (BonusItemData)item;
                        mBind.vsv.add(data.iconPath, data.name, data.publisher);
                    }

                    mBind.vsv.start();
                }
            }
        });

        return mBind.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        MyPassport.getInstance().RequestInfo(new MyPassport.RequestInfoListener() {
            @Override
            public void onResult(int nRet) {
                if(nRet == 0) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            refreshInfo();
                        }
                    });
                }
            }
        });
        mBind.animationTextureView.tickStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBind.animationTextureView.tickStop();
    }

    private void setupAds() {
        adsMan = new AdsManager(getContext());
        adsMan.setListener(this);
        adsMan.load();
    }

    private void setupButtonEvent() {
        mBind.btnShowAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final StringBuilder buf = new StringBuilder();
                DamoneyHttpHelper.GetAd(buf, new DamoneyHttpHelper.MyCallbackInterface() {
                    @Override
                    public void onResult(int nRet) {
                        if(nRet == 0) {
                            String sSerial = buf.toString();
                            adsMan.startFullAds(sSerial);
                        }
                    }
                });
            }
        });
        mBind.btnShowAds.setEnabled(false);
        mBind.btnGoPremiumMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainAct = (MainActivity)getActivity();
                mainAct.changeFragment(R.id.btn_go_premium_map);
            }
        });

        mBind.btnCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainAct = (MainActivity)getActivity();
                mainAct.changeFragment(R.id.btn_coupon);
            }
        });

        mBind.bonusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainAct = (MainActivity)getActivity();
                mainAct.changeFragment(R.id.bonus_item);
            }
        });

        mBind.animationTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoCharacterInfo();
            }
        });
        mBind.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoCharacterInfo();
            }
        });

        mBind.btnAdsDistribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainAct = (MainActivity)getActivity();
                mainAct.changeFragment(R.id.btn_ads_distribute);
            }
        });
    }

    private void setupAnim() {
        CharacterAnimator.getInstance().init(getContext(), mBind.animationTextureView);
    }

    private void refreshInfo() {
        mBind.tvLevel.setText("Lv." + MyPassport.getInstance().nLevel);
        mBind.tvName.setText(MyPassport.getInstance().sNick);
        mBind.expbar.setMax(MyPassport.getInstance().nExpMax);
        mBind.expbar.setProgress(MyPassport.getInstance().nCurExp);
        String sNextLevel = "다음 레벨업까지 " + (MyPassport.getInstance().nExpMax - MyPassport.getInstance().nCurExp) + "xp 남음";
        mBind.expLevelup.setText(sNextLevel);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumIntegerDigits(7); //최대수 지정
        String sVal = nf.format(MyPassport.getInstance().nPoint);
        mBind.tvPoint.setText(sVal);

        CharacterAnimator.getInstance().changeAnim(CharacterAnimator.CharacterState.CS_IDLE);
        if(MyPassport.getInstance().bLevelup) {
            MyPassport.getInstance().bLevelup = false;
            Intent intent = new Intent(getContext(), LevelUpActivity.class);
            getContext().startActivity(intent);
        }
    }

    @Override
    public void onAdsLoaded() {
        mBind.btnShowAds.setEnabled(true);
        if(bDirectShowAds) {
            bDirectShowAds = false;
            final StringBuilder buf = new StringBuilder();
            DamoneyHttpHelper.GetAd(buf, new DamoneyHttpHelper.MyCallbackInterface() {
                @Override
                public void onResult(int nRet) {
                    if(nRet == 0) {
                        String sSerial = buf.toString();
                        adsMan.startFullAds(sSerial);
                    }
                }
            });
        }
    }

    @Override
    public void onAdsFinished(int nRet) {
        setupAnim();
        if(nRet == -1) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "광고 리워드 받기에 실패 했습니다.", Toast.LENGTH_LONG).show();
                    mBind.btnShowAds.setEnabled(false);
                    adsMan.load();
                }
            });
        }

        if(nRet == 0) {
            Global.OpenGacha(getContext());
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    refreshInfo();
                    mBind.btnShowAds.setEnabled(false);
                    adsMan.load();
                }
            });
        }
    }

    public void setViewAdsByNoti() {
        bDirectShowAds = true;
    }

    private void GoCharacterInfo() {
        Intent intent = new Intent(getContext(), CharacterInfoActivity.class);
        getContext().startActivity(intent);
    }
}
