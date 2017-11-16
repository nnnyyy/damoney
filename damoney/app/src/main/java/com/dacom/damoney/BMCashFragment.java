package com.dacom.damoney;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
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

        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        childFragTrans.replace(R.id.placeholder, new BMCGoodsFragment());
        childFragTrans.commit();
        return mBind.getRoot();
    }

}
