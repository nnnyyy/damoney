package com.dacom.damoney;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentPbuylistBinding;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMBonusItemFragment extends Fragment {
    FragmentPbuylistBinding mBind;

    public BMBonusItemFragment() {
        // Required empty public constructor
    }

    private void setupRecyclerView() {
        mBind.goodsList.setHasFixedSize(true);
        mBind.goodsList.setLayoutManager(new LinearLayoutManager(mBind.getRoot().getContext()));
        mBind.goodsList.setAdapter(new BonusItemListRecyclerAdapter(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_pbuylist, container, false);
        setupRecyclerView();
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadList();
    }

    protected void loadList() {
        final ArrayList<BonusItemBase> list = new ArrayList<>();
        BonusItemSection section = new BonusItemSection();
        section.level = 1;
        list.add(section);

        BonusItemData data = new BonusItemData();
        list.add(data);

        section = new BonusItemSection();
        section.level = 4;
        list.add(section);

        data = new BonusItemData();
        list.add(data);

        data = new BonusItemData();
        list.add(data);

        BonusItemListRecyclerAdapter adapter = (BonusItemListRecyclerAdapter)mBind.goodsList.getAdapter();
        adapter.AddList(list);
        adapter.notifyDataSetChanged();

        /*DamoneyHttpHelper.GetBuyList(list, new DamoneyHttpHelper.MyCallbackInterface() {
            @Override
            public void onResult(int nRet) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });*/
    }
}
