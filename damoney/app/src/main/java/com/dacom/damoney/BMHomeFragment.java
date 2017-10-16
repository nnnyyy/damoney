package com.dacom.damoney;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dacom.damoney.Advertisement.AdsManager;
import com.dacom.damoney.Advertisement.AdsResultListener;
import com.dacom.damoney.databinding.FragmentBmhomeBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMHomeFragment extends Fragment implements AdsResultListener{
    FragmentBmhomeBinding mBind;
    AdsManager adsMan;
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
        return mBind.getRoot();
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
                adsMan.startFullAds();
            }
        });
        mBind.btnShowAds.setEnabled(false);
        mBind.btnGoPremiumMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PremiumRewardActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onAdsLoaded() {
        mBind.btnShowAds.setEnabled(true);
    }

    @Override
    public void onAdsFinished(int nRet) {
        if(nRet == -1) {
            Toast.makeText(getContext(), "광고 리워드 받기에 실패 했습니다.", Toast.LENGTH_LONG).show();
            mBind.btnShowAds.setEnabled(false);
            adsMan.load();
        }

        if(nRet == 0) {
            Toast.makeText(getContext(), "광고 리워드를 받았습니다.", Toast.LENGTH_LONG).show();
            mBind.btnShowAds.setEnabled(false);
            adsMan.load();
        }
    }
}
