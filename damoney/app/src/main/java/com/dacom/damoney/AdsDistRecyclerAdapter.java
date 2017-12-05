package com.dacom.damoney;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dacom.damoney.databinding.AdsDistItemBinding;

import java.util.ArrayList;

/**
 * Created by nnnyyy on 2017-09-05.
 */

public class AdsDistRecyclerAdapter extends RecyclerView.Adapter<AdsDistRecyclerAdapter.ArticleItemViewHolder> {

    ArrayList<AdsDistItem> aItemList = new ArrayList<>();
    Fragment fragment;
    int mLastPosition = -1;

    public AdsDistRecyclerAdapter(Fragment f) {
        fragment = f;
        setHasStableIds(true);
    }

    @Override
    public AdsDistRecyclerAdapter.ArticleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdsDistItemBinding bind = AdsDistItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticleItemViewHolder(bind.getRoot());
    }



    @Override
    public void onBindViewHolder(AdsDistRecyclerAdapter.ArticleItemViewHolder holder, int position) {
        final AdsDistItem item =  aItemList.get(position);
        holder.mBind.setItem(item);
        holder.mBind.ivThumbnail.setImageResource(item.ResId);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return aItemList.size();
    }

    public void AddList(ArrayList<AdsDistItem> _list) {
        aItemList.addAll(_list);
    }

    public void ClearList() {
        mLastPosition = -1;
        aItemList.clear();
    }

    public class ArticleItemViewHolder extends RecyclerView.ViewHolder {
        final AdsDistItemBinding mBind;

        public ArticleItemViewHolder(View itemView) {
            super(itemView);
            mBind = DataBindingUtil.bind(itemView);
        }
    }

    @BindingAdapter({"priceRes"})
    public static void setPointText(TextView tv, int price) {
        tv.setText("" + price + "원");
    }
}
