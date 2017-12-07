package com.dacom.damoney;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentBonusTowerBinding;
import com.dacom.damoney.databinding.TowerTemplate2Binding;
import com.dacom.damoney.databinding.TowerTemplate3Binding;
import com.dacom.damoney.databinding.TowerTemplate4Binding;
import com.dacom.damoney.databinding.TowerTemplateBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMBonusTowerFragment extends Fragment {
    FragmentBonusTowerBinding mBind;

    public BMBonusTowerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_bonus_tower, container, false);
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadList();
    }

    protected void loadList() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater == null) return;
        TowerTemplateBinding bind = DataBindingUtil.inflate(inflater, R.layout.tower_template, mBind.llTowerRoot, false);
        mBind.llTowerRoot.addView(bind.getRoot());
        bind = DataBindingUtil.inflate(inflater, R.layout.tower_template, mBind.llTowerRoot, false);
        mBind.llTowerRoot.addView(bind.getRoot());

        TowerTemplate2Binding bind2 = DataBindingUtil.inflate(inflater, R.layout.tower_template2, mBind.llTowerRoot, false);
        mBind.llTowerRoot.addView(bind2.getRoot());
        bind2 = DataBindingUtil.inflate(inflater, R.layout.tower_template2, mBind.llTowerRoot, false);
        mBind.llTowerRoot.addView(bind2.getRoot());

        TowerTemplate3Binding bind3 = DataBindingUtil.inflate(inflater, R.layout.tower_template3, mBind.llTowerRoot, false);
        mBind.llTowerRoot.addView(bind3.getRoot());
        bind3 = DataBindingUtil.inflate(inflater, R.layout.tower_template3, mBind.llTowerRoot, false);
        mBind.llTowerRoot.addView(bind3.getRoot());

        TowerTemplate4Binding bind4 = DataBindingUtil.inflate(inflater, R.layout.tower_template4, mBind.llTowerRoot, false);
        mBind.llTowerRoot.addView(bind4.getRoot());
        bind4 = DataBindingUtil.inflate(inflater, R.layout.tower_template4, mBind.llTowerRoot, false);
        mBind.llTowerRoot.addView(bind4.getRoot());
    }
}
