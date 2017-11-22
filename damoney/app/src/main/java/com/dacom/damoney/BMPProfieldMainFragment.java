package com.dacom.damoney;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentEbusBinding;
import com.dacom.damoney.databinding.FragmentPprofileMainBinding;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMPProfieldMainFragment extends Fragment {
    FragmentPprofileMainBinding mBind;
    BMProfileFragment parent;

    public BMPProfieldMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_pprofile_main, container, false);
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBind.btnBuyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.changeChildFragment(1);
            }
        });
    }

    public void setPrarent(BMProfileFragment _parent) {
        parent = _parent;
    }
}
