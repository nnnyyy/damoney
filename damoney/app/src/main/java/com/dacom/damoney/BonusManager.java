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
    public static BonusItemData selectedData;
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

    protected ArrayList<BonusItemBase> aBonusData;
    protected HashMap<Integer, BonusItemBase> mmBonusData;
    protected HashSet<Integer> mReqLevelSet;
    protected ArrayList<Integer> aBonusLevelList;
    protected HashMap<Integer/* id */, Integer /* cnt */> mmMyList;
    protected ArrayList<Integer> aMyList;

    public BonusManager() {
        aBonusData = new ArrayList<>();
        mReqLevelSet = new HashSet<>();
        aBonusLevelList = new ArrayList<>();
        mmBonusData = new HashMap<>();
        mmMyList = new HashMap<>();
        aMyList = new ArrayList<>();
    }

    public void addSection(int reqLevel) {
        if(mReqLevelSet.contains(reqLevel)) {
            return;
        }
        mReqLevelSet.add(reqLevel);
        aBonusLevelList.add(reqLevel);
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

        if(mReqLevelSet != null) {
            mReqLevelSet.clear();
        }

        if(aBonusLevelList != null) {
            aBonusLevelList.clear();
        }

        if(mmBonusData != null) {
            mmBonusData.clear();
        }

        if(mmMyList != null) {
            mmMyList.clear();
        }

        if(aMyList != null) {
            aMyList.clear();
        }
    }

    public ArrayList<BonusItemBase> getBonusList() { return aBonusData; }
    public ArrayList<Integer> getGachaList(int no) {
        BonusItemData data = (BonusItemData)mmBonusData.get(no);
        if(data == null) return null;
        return data.aGacha;
    }

    public void addMyGacha(int sn, int itemid) {
        if(mmMyList.containsKey(itemid)) {
            mmMyList.put(itemid, mmMyList.get(itemid) + 1);
        }
        else {
            mmMyList.put(itemid, 1);
            aMyList.add(itemid);
        }
    }

    public boolean hasGacha(int itemid) {
        return mmMyList.containsKey(itemid);
    }

    public ArrayList<Integer> getMyGachaList() { return aMyList; }

    public int getCompleteRate( int bonusNo ) {
        BonusItemData data = (BonusItemData)mmBonusData.get(bonusNo);
        int nMaxSize = data.aGacha.size();
        int nCompletedCnt = 0;
        for(int i = 0 ; i < nMaxSize ; ++i) {
            int gid = data.aGacha.get(i);
            for(int j = 0 ; j < aMyList.size() ; ++j) {
                if(gid == aMyList.get(j)) {
                    nCompletedCnt++;
                    continue;
                }
            }
        }

        return (int)(((float)nCompletedCnt / nMaxSize) * 100.0f);
    }

    public ArrayList<Integer> getReqLevelSet() {
        return aBonusLevelList;
    }
}
