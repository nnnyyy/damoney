package com.dacom.damoney;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentCouponBinding;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMCouponFragment extends Fragment {
    FragmentCouponBinding mBind;

    public BMCouponFragment() {
        // Required empty public constructor
    }

    private void setupRecyclerView() {
        mBind.rvPremiumList.setHasFixedSize(true);
        mBind.rvPremiumList.setLayoutManager(new LinearLayoutManager(mBind.getRoot().getContext()));
        mBind.rvPremiumList.setAdapter(new CouponRecyclerAdapter(this));
        mBind.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnBack(v);
            }
        });

        mBind.descWnd.setVisibility(View.GONE);
    }

    public void onBtnBack(View v) {
        MainActivity context = (MainActivity)v.getContext();
        context.changeFragment(R.id.act_home);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_coupon, container, false);
        setupRecyclerView();
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int[] resid = { R.drawable.premium_icon_cjone, R.drawable.premium_icon_coupang, R.drawable.premium_icon_daum };
        String[] titles = { "탐앤탐스", "롯데리아", "도미노피자" };
        String[] desc = { "무료 사이즈 업그레이드", "단품 주문 시, 세트 제공", "콜라 1.25L 제공"};
        int[] point = { 50, 30, 100 };
        ArrayList<CouponItem> list = new ArrayList<>();
        for(int i = 0 ; i < 3 ; ++i) {
            CouponItem newItem = new CouponItem();
            newItem.iconResId = resid[i];
            newItem.title = titles[i];
            newItem.desc = desc[i];
            newItem.point = point[i];
            newItem.sURL = "http://naver.com";
            list.add(newItem);
        }

        CouponRecyclerAdapter adapter = (CouponRecyclerAdapter)mBind.rvPremiumList.getAdapter();
        adapter.AddList(list);
        adapter.notifyDataSetChanged();
    }

    void showCouponUseWnd(/*CouponInfo info*/){
        mBind.descWnd.setVisibility(View.VISIBLE);
        mBind.wndClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBind.descWnd.setVisibility(View.GONE);
            }
        });
    }
}
