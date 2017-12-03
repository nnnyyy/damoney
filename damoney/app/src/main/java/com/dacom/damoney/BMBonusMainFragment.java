package com.dacom.damoney;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentBmbonusmainBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMBonusMainFragment extends FragmentEx {
    FragmentBmbonusmainBinding mBind;
    BonusManager bm;
    int curchildFragmentIdx = -1;
    int selectedGachaNo = -1;

    public BMBonusMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_bmbonusmain, container, false);
        mBind.setItem(this);
        mBind.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnBack(v);
            }
        });
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bm = new BonusManager();
        DamoneyHttpHelper.getBonusInfo(bm, new DamoneyHttpHelper.MyCallbackInterface() {
            @Override
            public void onResult(int nRet) {
                if(nRet == 0) {
                    changeChildFragment(0);
                }
                else
                    onBtnBack(mBind.getRoot());
            }
        });
    }

    public void setGachaNo(int no) {
        selectedGachaNo = no;
    }

    public int getGachaNo() { return selectedGachaNo; }

    protected void changeChildFragment(int menuid) {
        Fragment f = null;
        switch(menuid) {
            case 0:
                f = new BMBonusItemFragment();
                break;

            case 1:
                f = new BMBonusItemDetailFragment();
                break;
        }

        if(f == null) return;

        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        childFragTrans.replace(R.id.placeholder, f);
        childFragTrans.commit();
        curchildFragmentIdx = menuid;
    }

    public void onBtnBack(View v) {
        MainActivity context = (MainActivity)v.getContext();
        onBack(context);
    }

    @Override
    public void onBack(Context context) {
        super.onBack(context);
        if(curchildFragmentIdx == 1) {
            changeChildFragment(0);
        }
        else {
            ((MainActivity)context).changeNav(R.id.act_home);
        }
    }

    public BonusManager getBM() { return bm; }
}
