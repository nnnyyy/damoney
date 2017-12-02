package com.dacom.damoney;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by nnnyyy on 2017-11-24.
 */

public class BonusManager {
    public static HashMap<Integer, GachaBoxInfo> mmGachaList = new HashMap<>();
    public static void loadGachaList(JSONArray aList) throws JSONException {
        int len = aList.length();
        for(int i = 0 ; i < len ; ++i) {
            GachaBoxInfo info = new GachaBoxInfo();
            JSONObject obj = aList.getJSONObject(i);
            int id = obj.getInt("id");
            info.sName = obj.getString("name");
            info.iconpath = obj.getString("iconpath");
            info.nLevel = obj.getInt("level");
            info.nGrade = obj.getInt("grade");
            mmGachaList.put(id, info);
        }
    }

    public static GachaBoxInfo getGacha(int id) {
        return mmGachaList.get(id);
    }

    public BonusManager() {
        aBonusData = new ArrayList<>();
        mReqLevelSet = new HashSet<>();
        mmBonusData = new HashMap<>();
    }

    protected ArrayList<BonusItemBase> aBonusData;
    protected HashMap<Integer, BonusItemBase> mmBonusData;
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
        mmBonusData.put(no, newData);
    }

    public void clear() {
        if(aBonusData != null) {
            aBonusData.clear();
        }
    }

    public ArrayList<BonusItemBase> getBonusList() { return aBonusData; }
    public ArrayList<Integer> getGachaList(int no) {
        BonusItemData data = (BonusItemData)mmBonusData.get(no);
        if(data == null) return null;
        return data.aGacha;
    }

    public void addMyGacha(int sn, int itemid) {
    }
}
