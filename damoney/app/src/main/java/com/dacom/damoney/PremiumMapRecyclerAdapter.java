package com.dacom.damoney;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dacom.damoney.databinding.PremiumItemBinding;

import java.util.ArrayList;

/**
 * Created by nnnyyy on 2017-09-05.
 */

public class PremiumMapRecyclerAdapter extends RecyclerView.Adapter<PremiumMapRecyclerAdapter.ArticleItemViewHolder> {

    ArrayList<PremiumItem> aItemList = new ArrayList<>();
    int mLastPosition = -1;

    public PremiumMapRecyclerAdapter() {
        setHasStableIds(true);
    }

    @Override
    public PremiumMapRecyclerAdapter.ArticleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PremiumItemBinding bind = PremiumItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticleItemViewHolder(bind.getRoot());
    }



    @Override
    public void onBindViewHolder(PremiumMapRecyclerAdapter.ArticleItemViewHolder holder, int position) {
        final PremiumItem item =  aItemList.get(position);
        holder.mBind.setItem(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return aItemList.size();
    }

    public void AddList(ArrayList<PremiumItem> _list) {
        aItemList.addAll(_list);
    }

    public void ClearList() {
        mLastPosition = -1;
        aItemList.clear();
    }

    public class ArticleItemViewHolder extends RecyclerView.ViewHolder {
        final PremiumItemBinding mBind;

        public ArticleItemViewHolder(View itemView) {
            super(itemView);
            mBind = DataBindingUtil.bind(itemView);
        }
    }

    @BindingAdapter({"pointRes"})
    public static void setPointText(TextView tv, int point) {
        tv.setText("" + point + "원");
    }

    @BindingAdapter({"typeRes"})
    public static void setTypeText(TextView tv, int type) {
        String t = null;
        switch (type) {
            case 0: t = "설치형"; break;
            case 1: t = "가입형"; break;
            case 2: t = "실행형"; break;
            default:
                t = "NoData";
        }
        tv.setText(t);
    }

    @BindingAdapter({"imgRes"})
    public static void setIcon(ImageView iv, int resid) {
        iv.setImageResource(resid);
    }
}
