package com.dacom.damoney;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dacom.damoney.Sign.MyPassport;
import com.dacom.damoney.databinding.CouponItemBinding;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(final CouponRecyclerAdapter.ArticleItemViewHolder holder, int position) {
        final CouponItem item =  aItemList.get(position);
        holder.mBind.setItem(item);
        Picasso.with(fragment.getContext()).load(Global.BASE_URL + item.iconPath).into(holder.mBind.ivThumbnail);
        if(MyPassport.getInstance().isExistCoupon(item.sn)) {
            holder.mBind.btnDownCoupon.setImageResource(R.drawable.btn_show_coupon);
        }
        holder.mBind.btnDownCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  쿠폰 다운로드
                //  이것도 서버에서 처리해야 하나 시간이 없으니 이렇게 일단 처리
                if(MyPassport.getInstance().isExistCoupon(item.sn)) {
                    Intent intent = new Intent(fragment.getContext(), CouponUseActivity.class);
                    intent.putExtra("title", item.title);
                    intent.putExtra("subtitle", item.sDesc);
                    intent.putExtra("iconpath", item.iconPath);
                    fragment.getContext().startActivity(intent);
                }
                else {
                    Toast.makeText(fragment.getContext(), "쿠폰을 다운로드 했습니다.", Toast.LENGTH_SHORT).show();
                    holder.mBind.btnDownCoupon.setImageResource(R.drawable.btn_show_coupon);
                    MyPassport.getInstance().saveCoupon(item.sn);
                }
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
}
