package com.dacom.damoney;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dacom.damoney.databinding.BonusDataBinding;
import com.dacom.damoney.databinding.BonusSectionBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nnnyyy on 2017-09-05.
 */

public class BonusItemListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<BonusItemBase> aItemList = new ArrayList<>();
    Fragment fragment;
    int mLastPosition = -1;

    public BonusItemListRecyclerAdapter(Fragment f) {
        fragment = f;
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            // Section
            BonusSectionBinding bind = BonusSectionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new BonusItemSectionHolder(bind.getRoot());
        }
        else {
            BonusDataBinding bind = BonusDataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new BonusItemDataHolder(bind.getRoot());
        }
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(aItemList.get(position).type == BonusItemBase.BIType.BI_SECTION) {
            bindSectionHolder((BonusItemSectionHolder)holder, (BonusItemSection)aItemList.get(position));
        }
        else {
            bindDataHolder((BonusItemDataHolder)holder, (BonusItemData)aItemList.get(position));
        }
    }

    private void bindSectionHolder(BonusItemSectionHolder holder, BonusItemSection item) {
        holder.mBind.tvTitle.setText("요구레벨: " + item.level);
    }

    private void bindDataHolder(BonusItemDataHolder holder, BonusItemData item) {
        holder.mBind.setItem(item);
        Picasso.with(fragment.getContext()).load(Global.BASE_URL + item.iconPath).into(holder.mBind.ivThumbnail);
    }

    @Override
    public int getItemViewType(int position) {
        return aItemList.get(position).type == BonusItemBase.BIType.BI_SECTION ? 0 : 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return aItemList.size();
    }

    public void AddList(ArrayList<BonusItemBase> _list) {
        aItemList.addAll(_list);
    }

    public void ClearList() {
        mLastPosition = -1;
        aItemList.clear();
    }

    public class BonusItemSectionHolder extends RecyclerView.ViewHolder {
        final BonusSectionBinding mBind;

        public BonusItemSectionHolder(View itemView) {
            super(itemView);
            mBind = DataBindingUtil.bind(itemView);
        }
    }

    public class BonusItemDataHolder extends RecyclerView.ViewHolder {
        final BonusDataBinding mBind;

        public BonusItemDataHolder(View itemView) {
            super(itemView);
            mBind = DataBindingUtil.bind(itemView);
        }
    }

    @BindingAdapter({"pointRes"})
    public static void setPointText(TextView tv, int point) {
        tv.setText("" + point);
    }
}
