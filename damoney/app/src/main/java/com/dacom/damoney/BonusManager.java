package com.dacom.damoney;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by nnnyyy on 2017-11-24.
 */

public class BonusManager {
    public BonusManager() {
        aBonusData = new ArrayList<>();
        mReqLevelSet = new HashSet<>();
    }

    protected ArrayList<BonusItemBase> aBonusData;
    protected HashSet<Integer> mReqLevelSet;

    public void addSection(int reqLevel) {
        if(mReqLevelSet.contains(reqLevel)) {
            return;
        }
        mReqLevelSet.add(reqLevel);
        BonusItemSection newSection = new BonusItemSection();
        newSection.level = reqLevel;
        aBonusData.add(newSection);
    }

    public void add(int no, String pub, String name, int reqLevel, String ipath, ArrayList<Integer> _list) {
        BonusItemData newData = new BonusItemData();
        newData.no = no;
        newData.publisher = pub;
        newData.name = name;
        newData.iconPath = ipath;
        newData.aGacha = _list;
        newData.reqLevel = reqLevel;
        aBonusData.add(newData);
    }

    public void clear() {
        if(aBonusData != null) {
            aBonusData.clear();
        }
    }

    public ArrayList<BonusItemBase> getBonusList() { return aBonusData; }

    public void addMyGacha(int sn, int itemid) {
    }
}
