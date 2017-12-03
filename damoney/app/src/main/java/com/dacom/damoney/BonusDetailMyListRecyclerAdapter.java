package com.dacom.damoney;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.BonusDetailMineBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nnnyyy on 2017-09-05.
 */

public class BonusDetailMyListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Integer> aItemList = new ArrayList<>();
    Fragment fragment;
    int mLastPosition = -1;

    public BonusDetailMyListRecyclerAdapter(Fragment f) {
        fragment = f;
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BonusDetailMineBinding bind = BonusDetailMineBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BonusItemCompleteHolder(bind.getRoot());
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BonusItemCompleteHolder sholder = (BonusItemCompleteHolder)holder;
        int itemid = aItemList.get(position);
        final GachaBoxInfo info = BonusManager.getGacha(itemid);
        Picasso.with(sholder.mBind.getRoot().getContext()).load(Global.BASE_URL+info.iconpath).into(sholder.mBind.ivThumbnail);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return aItemList.size();
    }

    public void AddList(ArrayList<Integer> _list) {
        aItemList.addAll(_list);
    }

    public void ClearList() {
        mLastPosition = -1;
        aItemList.clear();
    }

    public class BonusItemCompleteHolder extends RecyclerView.ViewHolder {
        final BonusDetailMineBinding mBind;

        public BonusItemCompleteHolder(View itemView) {
            super(itemView);
            mBind = DataBindingUtil.bind(itemView);
        }
    }
}
