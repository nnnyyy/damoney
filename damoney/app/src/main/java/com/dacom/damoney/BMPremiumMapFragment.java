package com.dacom.damoney;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentPremiummapBinding;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMPremiumMapFragment extends Fragment {
    FragmentPremiummapBinding mBind;

    public BMPremiumMapFragment() {
        // Required empty public constructor
    }

    private void setupRecyclerView() {
        mBind.rvPremiumList.setHasFixedSize(true);
        mBind.rvPremiumList.setLayoutManager(new LinearLayoutManager(mBind.getRoot().getContext()));
        mBind.rvPremiumList.setAdapter(new PremiumMapRecyclerAdapter());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_premiummap, container, false);
        setupRecyclerView();
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int[] resid = { R.drawable.premium_icon_cjone, R.drawable.premium_icon_coupang, R.drawable.premium_icon_daum };
        String[] titles = { "CJ ONE", "쿠팡", "다음"};
        int[] point = { 1100, 2000, 1500 };
        int[] types = { 0, 1, 2}; // 설치형, 가입형, 실행형
        ArrayList<PremiumItem> list = new ArrayList<>();
        for(int i = 0 ; i < 3 ; ++i) {
            PremiumItem newItem = new PremiumItem();
            newItem.iconResId = resid[i];
            newItem.title = titles[i];
            newItem.point = point[i];
            newItem.type = types[i];
            list.add(newItem);
        }

        PremiumMapRecyclerAdapter adapter = (PremiumMapRecyclerAdapter)mBind.rvPremiumList.getAdapter();
        adapter.AddList(list);
        adapter.notifyDataSetChanged();
    }
}
