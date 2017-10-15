package com.dacom.damoney;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentBmcommunityBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMCommunityFragment extends Fragment {
    FragmentBmcommunityBinding mBind;

    public BMCommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_bmcommunity, container, false);
        return mBind.getRoot();
    }

}
