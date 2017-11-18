package com.dacom.damoney;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dacom.damoney.Advertisement.AdsWebView;
import com.dacom.damoney.Sign.MyPassport;
import com.dacom.damoney.databinding.PremiumItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nnnyyy on 2017-09-05.
 */

public class PremiumMapRecyclerAdapter extends RecyclerView.Adapter<PremiumMapRecyclerAdapter.ArticleItemViewHolder> {

    ArrayList<PremiumItem> aItemList = new ArrayList<>();
    BMPremiumMapFragment fragment;
    int mLastPosition = -1;

    public PremiumMapRecyclerAdapter(BMPremiumMapFragment f) {
        fragment = f;
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
        Picasso.with(fragment.getContext()).load(Global.BASE_URL + item.iconPath).into(holder.mBind.ivThumbnail);
        holder.mBind.clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AdsWebView.class);
                intent.putExtra("url", item.sURL);
                v.getContext().startActivity(intent);
                DamoneyHttpHelper.ViewAd(item.sn, new DamoneyHttpHelper.MyCallbackInterface() {
                    @Override
                    public void onResult(int nRet) {
                        if(nRet == 0) {
                            MyPassport.getInstance().RequestInfo(new MyPassport.RequestInfoListener() {
                                @Override
                                public void onResult(int nRet) {

                                }
                            });
                            fragment.loadAds();
                        }
                    }
                });
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
