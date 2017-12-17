package com.dacom.damoney;


import android.content.Intent;
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

import com.dacom.damoney.AlertManager.AlertManager;
import com.dacom.damoney.databinding.FragmentGoodsBinding;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMCGoodsFragment extends Fragment {
    FragmentGoodsBinding mBind;

    public BMCGoodsFragment() {
        // Required empty public constructor
    }

    private void setupRecyclerView() {
        mBind.goodsList.setHasFixedSize(true);
        mBind.goodsList.setLayoutManager(new LinearLayoutManager(mBind.getRoot().getContext()));
        mBind.goodsList.setAdapter(new GoodsRecyclerAdapter(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_goods, container, false);
        setupRecyclerView();
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadAds();
    }

    protected void loadAds() {
        final ArrayList<GoodsItem> list = new ArrayList<>();
        DamoneyHttpHelper.GetItemList(0, list, new DamoneyHttpHelper.MyCallbackInterface() {
            @Override
            public void onResult(int nRet) {
                if(nRet != 0) {
                    AlertManager.ShowOk(getContext(), "알림", "네트워크 연결을 확인 해 주세요", "닫기", new AlertManager.OnClickListener() {
                        @Override
                        public void onClick(View v, int which) {
                            Intent intent = new Intent(getContext(), SplashActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                        }
                    });
                    return;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        GoodsRecyclerAdapter adapter = (GoodsRecyclerAdapter)mBind.goodsList.getAdapter();
                        adapter.AddList(list);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
