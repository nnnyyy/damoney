package com.dacom.damoney;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentBmadsdistBinding;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMAdsDistFragment extends FragmentEx {
    FragmentBmadsdistBinding mBind;

    public BMAdsDistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_bmadsdist, container, false);
        setupRecyclerView();
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAds();
    }

    public void onBtnBack(View v) {
        MainActivity context = (MainActivity)v.getContext();
        onBack(context);
    }

    @Override
    public void onBack(Context context) {
        super.onBack(context);
        ((MainActivity)context).changeNav(R.id.act_home);
    }

    private void setupRecyclerView() {
        mBind.rvAdList.setHasFixedSize(true);
        mBind.rvAdList.setLayoutManager(new LinearLayoutManager(mBind.getRoot().getContext()));
        mBind.rvAdList.setAdapter(new AdsDistRecyclerAdapter(this));
        mBind.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnBack(v);
            }
        });
    }

    private void setupAds() {
        AdsDistRecyclerAdapter adapter = (AdsDistRecyclerAdapter)mBind.rvAdList.getAdapter();
        adapter.ClearList();
        ArrayList<AdsDistItem> aList = new ArrayList<>();
        aList.add(new AdsDistItem(R.drawable.icon_coin, "플레이스테이션4 프로 팝니다", "승이짱 - 서울", "", 15000, true));
        aList.add(new AdsDistItem(R.drawable.icon_coin, "에스콰이어 소가죽 가방이요", "gsk2 - 경기남부", "", 15000, true));
        aList.add(new AdsDistItem(R.drawable.icon_coin, "레이번 선글라스 팝니다.", "superYG - 경기북부", "", 30000, true));
        aList.add(new AdsDistItem(R.drawable.icon_coin, "지스타 쿠폰 코드 팝니다", "nnnyyy - 부산", "", 1000, true));
        aList.add(new AdsDistItem(R.drawable.icon_coin, "LG 모니터 24인치", "부자되세요 - 인천", "", 20000, true));
        aList.add(new AdsDistItem(R.drawable.icon_coin, "탁구채 팝니다", "에이미 - 파주", "", 13000, true));
        aList.add(new AdsDistItem(R.drawable.icon_coin, "에어컨 성남 분당 용인 출장수리", "에어컨전문 - 용인", "", 1200, true));
        adapter.AddList(aList);
        adapter.notifyDataSetChanged();
    }
}
