package com.dacom.damoney;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentBonusTowerBinding;
import com.dacom.damoney.databinding.TowerBonusItemIconBinding;
import com.dacom.damoney.databinding.TowerTemplateBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMBonusTowerFragment extends Fragment {
    static int lastScroll = -1;
    FragmentBonusTowerBinding mBind;
    BonusManager bm;

    public BMBonusTowerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_bonus_tower, container, false);
        bm  = ((BMBonusMainFragment)getParentFragment()).getBM();
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(lastScroll == -1) {
            mBind.towerScroll.post(new Runnable() {
                @Override
                public void run() {
                    mBind.towerScroll.scrollTo(0, mBind.towerScroll.getBottom());
                    lastScroll = mBind.towerScroll.getBottom();
                }
            });
        }
        else {
            mBind.towerScroll.post(new Runnable() {
                @Override
                public void run() {
                    mBind.towerScroll.scrollTo(0, lastScroll);
                }
            });
        }

        loadList();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("BTF - onResume", "scroll value : " + lastScroll);
        mBind.towerScroll.post(new Runnable() {
            @Override
            public void run() {
                mBind.towerScroll.scrollTo(0, lastScroll);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        lastScroll = -1;
    }

    protected void loadList() {
        ArrayList<BonusItemBase> aBonusList = bm.getBonusList();
        ArrayList<Integer> mLevelSet = bm.getReqLevelSet();
        HashMap<Integer, ArrayList<BonusItemData>> map = new HashMap<>();

        Iterator<?> iter = aBonusList.iterator();
        while(iter.hasNext()){
            BonusItemBase item = (BonusItemBase)iter.next();
            if(item.type == BonusItemBase.BIType.BI_SECTION) continue;
            BonusItemData data = (BonusItemData)item;
            if(!map.containsKey(data.reqLevel)) {
                ArrayList<BonusItemData> aDataList = new ArrayList<>();
                aDataList.add(data);
                map.put(data.reqLevel, aDataList);
            }
            else {
                ArrayList<BonusItemData> aDataList = map.get(data.reqLevel);
                aDataList.add(data);
            }
        }

        ArrayList<Integer> mLevelSetClone = (ArrayList<Integer>)mLevelSet.clone();
        Collections.reverse(mLevelSetClone);
        Iterator<?> lsiter = mLevelSetClone.iterator();
        while(lsiter.hasNext()) {
            final int level = (Integer)lsiter.next();
            Log.d("--- Level ---" , "" + level);
            ArrayList<BonusItemData> aDataList = map.get(level);
            TowerTemplateBinding bind = makeTowerFloor(level, aDataList.size());
            bind.touchArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BonusManager.selectedLevel = level;
                    lastScroll = mBind.towerScroll.getScrollY();
                    Log.d("BTF - touch", "scroll value : " + mBind.towerScroll.getScrollY());
                    ((BMBonusMainFragment)getParentFragment()).changeChildFragment(1);
                }
            });
            Iterator<?> iter2 = aDataList.iterator();
            while(iter2.hasNext()) {
                BonusItemData d = (BonusItemData)iter2.next();
                addTowerItem(bind, d);
                Log.d("Data" , d.name);
            }
        }
    }

    private TowerTemplateBinding makeTowerFloor(int level, int size) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater == null) return null;
        TowerTemplateBinding bind = DataBindingUtil.inflate(inflater, R.layout.tower_template, mBind.llTowerRoot, false);
        mBind.llTowerRoot.addView(bind.getRoot());
        bind.ivFloorImage.setImageResource(R.drawable.bonus_map_floor_1);
        bind.tvLv.setText("Lv. " + level);
        return bind;
    }

    private void addTowerItem(TowerTemplateBinding parent_bind, BonusItemData d) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater == null) return;
        final TowerBonusItemIconBinding bind = DataBindingUtil.inflate(inflater, R.layout.tower_bonus_item_icon, parent_bind.llBonusItemIconList, false);
        parent_bind.llBonusItemIconList.addView(bind.getRoot());
        Picasso.with(getContext()).load(Global.BASE_URL + d.iconPath).into(bind.ivThumbnail, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                bind.ivThumbnail.setImageResource(R.drawable.icon_coin);
            }
        });
    }
}
