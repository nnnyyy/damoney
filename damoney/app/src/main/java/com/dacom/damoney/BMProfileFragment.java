package com.dacom.damoney;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dacom.damoney.databinding.FragmentBmprofileBinding;

import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMProfileFragment extends Fragment {
    FragmentBmprofileBinding mBind;

    public BMProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_bmprofile, container, false);
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeChildFragment(0);
    }

    public void onBtnBack(View v) {
        MainActivity context = (MainActivity)v.getContext();
        context.changeNav(R.id.act_home);
    }

    public void onBtnReturn(View v) {
        changeChildFragment(0);
    }

    protected void changeChildFragment(int menuid) {
        Fragment f = null;
        switch(menuid) {
            case 0:
                f = new BMPProfieldMainFragment();
                ((BMPProfieldMainFragment)f).setPrarent(this);
                mBind.btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBtnBack(v);
                    }
                });
                break;

            case 1:
                f = new BMCEBusFragment();
                mBind.btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBtnReturn(v);
                    }
                });
                break;
        }

        if(f == null) return;

        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        childFragTrans.replace(R.id.placeholder, f);
        childFragTrans.commit();
    }
}
