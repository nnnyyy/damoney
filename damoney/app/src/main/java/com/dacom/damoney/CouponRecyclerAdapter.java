package com.dacom.damoney;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dacom.damoney.databinding.CouponItemBinding;

import java.util.ArrayList;

/**
 * Created by nnnyyy on 2017-09-05.
 */

public class CouponRecyclerAdapter extends RecyclerView.Adapter<CouponRecyclerAdapter.ArticleItemViewHolder> {

    BMCouponFragment fragment;
    ArrayList<CouponItem> aItemList = new ArrayList<>();
    int mLastPosition = -1;

    public CouponRecyclerAdapter(BMCouponFragment f) {
        fragment = f;
        setHasStableIds(true);
    }

    @Override
    public CouponRecyclerAdapter.ArticleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CouponItemBinding bind = CouponItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticleItemViewHolder(bind.getRoot());
    }



    @Override
    public void onBindViewHolder(CouponRecyclerAdapter.ArticleItemViewHolder holder, int position) {
        final CouponItem item =  aItemList.get(position);
        holder.mBind.setItem(item);
        holder.mBind.btnDownCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  쿠폰 다운로드
                fragment.showCouponUseWnd();
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return aItemList.size();
    }

    public void AddList(ArrayList<CouponItem> _list) {
        aItemList.addAll(_list);
    }

    public void ClearList() {
        mLastPosition = -1;
        aItemList.clear();
    }

    public class ArticleItemViewHolder extends RecyclerView.ViewHolder {
        final CouponItemBinding mBind;

        public ArticleItemViewHolder(View itemView) {
            super(itemView);
            mBind = DataBindingUtil.bind(itemView);
        }
    }

    @BindingAdapter({"pointRes"})
    public static void setPointText(TextView tv, int point) {
        tv.setText("" + point + "원");
    }

    @BindingAdapter({"imgRes"})
    public static void setIcon(ImageView iv, int resid) {
        iv.setImageResource(resid);
    }
}
