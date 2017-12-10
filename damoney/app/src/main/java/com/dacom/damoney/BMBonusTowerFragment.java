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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMBonusTowerFragment extends Fragment {
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

        loadList();
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

        Iterator<?> lsiter = mLevelSet.iterator();
        while(lsiter.hasNext()) {
            int level = (Integer)lsiter.next();
            Log.d("--- Level ---" , "" + level);
            ArrayList<BonusItemData> aDataList = map.get(level);
            TowerTemplateBinding bind = makeTowerFloor(level, aDataList.size());
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
        return bind;
    }

    private void addTowerItem(TowerTemplateBinding parent_bind, BonusItemData d) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater == null) return;
        TowerBonusItemIconBinding bind = DataBindingUtil.inflate(inflater, R.layout.tower_bonus_item_icon, parent_bind.llBonusItemIconList, false);
        parent_bind.llBonusItemIconList.addView(bind.getRoot());
        Picasso.with(getContext()).load(Global.BASE_URL + d.iconPath).into(bind.ivThumbnail);
    }
}
