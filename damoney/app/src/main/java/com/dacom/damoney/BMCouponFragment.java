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
        context.changeNav(R.id.act_home);
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
        loadAds();
    }

    protected void loadAds() {
        final ArrayList<CouponItem> list = new ArrayList<>();
        DamoneyHttpHelper.GetCouponList(list, new DamoneyHttpHelper.MyCallbackInterface() {
            @Override
            public void onResult(int nRet) {
                if(nRet == 0 ) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            CouponRecyclerAdapter adapter = (CouponRecyclerAdapter)mBind.rvPremiumList.getAdapter();
                            adapter.ClearList();
                            adapter.AddList(list);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
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
