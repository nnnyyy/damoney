package com.dacom.damoney;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentBonusDetailBinding;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMBonusItemDetailFragment extends Fragment {
    FragmentBonusDetailBinding mBind;
    int selectedGachaNo = -1;

    public BMBonusItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_bonus_detail, container, false);
        setupRecyclerView();
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedGachaNo = ((BMBonusMainFragment)getParentFragment()).getGachaNo();
        BonusItemData dataInfo = BonusManager.selectedData;
        mBind.tvTitle.setText(dataInfo.name);
        mBind.tvSubTitle.setText(dataInfo.publisher);
        Picasso.with(getContext()).load(Global.BASE_URL+dataInfo.iconPath).into(mBind.ivThumbnail);
        loadCompleteList();
        loadMyList();
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager gridmanager = new GridLayoutManager(getContext(), 3);
        mBind.rvCompletelist.setLayoutManager(gridmanager);
        mBind.rvCompletelist.setHasFixedSize(true);
        mBind.rvCompletelist.setAdapter(new BonusCompleteListRecyclerAdapter(getParentFragment()));

        mBind.rvMyitemlist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mBind.rvMyitemlist.setHasFixedSize(true);
        mBind.rvMyitemlist.setAdapter(new BonusDetailMyListRecyclerAdapter(this));
    }

    protected void loadCompleteList() {
        BMBonusMainFragment f = (BMBonusMainFragment)getParentFragment();
        BonusCompleteListRecyclerAdapter adapter = (BonusCompleteListRecyclerAdapter)mBind.rvCompletelist.getAdapter();
        adapter.AddList(f.getBM().getGachaList(selectedGachaNo));
        adapter.notifyDataSetChanged();
    }

    protected void loadMyList() {
        BMBonusMainFragment f = (BMBonusMainFragment)getParentFragment();
        BonusDetailMyListRecyclerAdapter adapter = (BonusDetailMyListRecyclerAdapter)mBind.rvMyitemlist.getAdapter();
        adapter.AddList(f.getBM().getMyGachaList());
        adapter.notifyDataSetChanged();
    }
}
