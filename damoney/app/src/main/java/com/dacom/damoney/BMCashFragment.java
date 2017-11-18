package com.dacom.damoney;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentBmcashBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMCashFragment extends Fragment {
    FragmentBmcashBinding mBind;

    public BMCashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_bmcash, container, false);
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

        changeChildFragment(0);
    }

    public void onBtnBack(View v) {
        MainActivity context = (MainActivity)v.getContext();
        context.changeFragment(R.id.act_home);
    }

    protected void changeChildFragment(int menuid) {
        Fragment f = null;
        switch(menuid) {
            case 0:
                f = new BMCGoodsFragment();
                break;

            case 1:
                f = new BMCItemFragment();
                break;

            case 2:
                f = new BMCEBusFragment();
                break;
        }

        if(f == null) return;

        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        childFragTrans.replace(R.id.placeholder, f);
        childFragTrans.commit();
    }

    public void onMenuClick(View v) {
        String menuid = (String)v.getTag();
        changeChildFragment(Integer.parseInt(menuid));
    }
}
