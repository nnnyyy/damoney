package com.dacom.damoney;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.Sign.MyPassport;
import com.dacom.damoney.databinding.FragmentPremiummapBinding;
import com.yaong.nnnyyy.nyhttphelper.HttpHelper;
import com.yaong.nnnyyy.nyhttphelper.HttpHelperListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMPremiumMapFragment extends Fragment {
    FragmentPremiummapBinding mBind;

    public BMPremiumMapFragment() {
        // Required empty public constructor
    }

    private void setupRecyclerView() {
        mBind.rvPremiumList.setHasFixedSize(true);
        mBind.rvPremiumList.setLayoutManager(new LinearLayoutManager(mBind.getRoot().getContext()));
        mBind.rvPremiumList.setAdapter(new PremiumMapRecyclerAdapter());
        mBind.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnBack(v);
            }
        });
    }

    public void onBtnBack(View v) {
        MainActivity context = (MainActivity)v.getContext();
        context.changeFragment(R.id.act_home);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_premiummap, container, false);
        setupRecyclerView();
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadAds();
    }

    protected void loadAds() {
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String s) {
                final ArrayList<PremiumItem> list = new ArrayList<>();
                try {
                    JSONObject root = new JSONObject(s);
                    JSONArray a = root.getJSONArray("list");
                    int cnt = a.length();
                    for(int i = 0 ; i < cnt ; ++i) {
                        PremiumItem newItem = new PremiumItem();
                        JSONObject o = a.getJSONObject(i);
                        newItem.title = o.getString("name");
                        newItem.sURL = o.getString("link");
                        newItem.iconResId = R.drawable.premium_icon_cjone;
                        newItem.type = o.getInt("type");
                        newItem.point = o.getInt("reward");
                        list.add(newItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        PremiumMapRecyclerAdapter adapter = (PremiumMapRecyclerAdapter)mBind.rvPremiumList.getAdapter();
                        adapter.AddList(list);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).Get(0, Global.BASE_URL + "/get/premiumlist?token=" + MyPassport.getInstance().getToken());
    }
}
