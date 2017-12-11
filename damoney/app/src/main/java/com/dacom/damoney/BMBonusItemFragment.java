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
        mBind.goodsList.setAdapter(new BonusItemListRecyclerAdapter(getParentFragment()));
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
        BMBonusMainFragment f = (BMBonusMainFragment)getParentFragment();
        ArrayList<BonusItemBase> aDataList = f.getBM().makeBonusListPerLevel().get(BonusManager.selectedLevel);
        BonusItemListRecyclerAdapter adapter = (BonusItemListRecyclerAdapter)mBind.goodsList.getAdapter();
        adapter.AddList(aDataList);
        adapter.notifyDataSetChanged();
    }
}
