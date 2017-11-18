package com.dacom.damoney;

import android.content.DialogInterface;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dacom.damoney.AlertManager.AlertManager;
import com.dacom.damoney.Sign.MyPassport;
import com.dacom.damoney.databinding.GoodsItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nnnyyy on 2017-09-05.
 */

public class GoodsRecyclerAdapter extends RecyclerView.Adapter<GoodsRecyclerAdapter.GoodsItemViewHolder> {

    ArrayList<GoodsItem> aItemList = new ArrayList<>();
    Fragment fragment;
    int mLastPosition = -1;

    public GoodsRecyclerAdapter(Fragment f) {
        fragment = f;
        setHasStableIds(true);
    }

    @Override
    public GoodsRecyclerAdapter.GoodsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GoodsItemBinding bind = GoodsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GoodsItemViewHolder(bind.getRoot());
    }



    @Override
    public void onBindViewHolder(GoodsRecyclerAdapter.GoodsItemViewHolder holder, int position) {
        final GoodsItem item =  aItemList.get(position);
        holder.mBind.setItem(item);
        Picasso.with(fragment.getContext()).load(Global.BASE_URL + item.iconPath).into(holder.mBind.ivThumbnail);
        holder.mBind.clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertManager.ShowYesNo(fragment.getContext(), "알림", "구매 하시겠습니까?", "예", "아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == DialogInterface.BUTTON_POSITIVE)
                            BuyItem(item.sn);
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

    public void AddList(ArrayList<GoodsItem> _list) {
        aItemList.addAll(_list);
    }

    public void ClearList() {
        mLastPosition = -1;
        aItemList.clear();
    }

    public class GoodsItemViewHolder extends RecyclerView.ViewHolder {
        final GoodsItemBinding mBind;

        public GoodsItemViewHolder(View itemView) {
            super(itemView);
            mBind = DataBindingUtil.bind(itemView);
        }
    }

    @BindingAdapter({"pointRes"})
    public static void setPointText(TextView tv, int point) {
        tv.setText("" + point + "원");
    }

    public void BuyItem(int itemsn) {
        DamoneyHttpHelper.BuyItem(itemsn, new DamoneyHttpHelper.MyCallbackInterface() {
            @Override
            public void onResult(int nRet) {
                String msg = "";
                if(nRet != 0) {
                    msg = "아이템 구매에 실패 했습니다.";
                }
                else {
                    MyPassport.getInstance().RequestInfo(null);
                    msg = "아이템을 구매 했습니다.";
                }

                final String sMsgRet = msg;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(fragment.getContext(), sMsgRet, Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }
}
