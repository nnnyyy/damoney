package com.dacom.damoney;

import com.dacom.damoney.Sign.MyPassport;
import com.yaong.nnnyyy.nyhttphelper.HttpHelper;
import com.yaong.nnnyyy.nyhttphelper.HttpHelperListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nnnyyy on 2017-11-17.
 */

public class DamoneyHttpHelper {
    public interface MyCallbackInterface {
        void onResult(int nRet);
    }

    public static void  GetPremiumList(final ArrayList<PremiumItem> list, final MyCallbackInterface callback) {
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String s) {
                try {
                    JSONObject root = new JSONObject(s);
                    JSONArray a = root.getJSONArray("list");
                    int cnt = a.length();
                    for(int i = 0 ; i < cnt ; ++i) {
                        PremiumItem newItem = new PremiumItem();
                        JSONObject o = a.getJSONObject(i);
                        newItem.sn = o.getInt("sn");
                        newItem.title = o.getString("name");
                        newItem.sURL = o.getString("link");
                        newItem.iconResId = R.drawable.premium_icon_cjone;
                        newItem.iconPath = o.getString("iconpath");
                        newItem.type = o.getInt("type");
                        newItem.point = o.getInt("reward");
                        String sId = o.getString("id");
                        newItem.isUsed = sId == "null" ? false : true;
                        list.add(newItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(callback != null)
                        callback.onResult(-1);
                    return;
                }

                if(callback != null)
                    callback.onResult(0);
            }
        }).Get(0, Global.BASE_URL + "/get/premiumlist?token=" + MyPassport.getInstance().getToken());
    }

    public static void GetCouponList(final ArrayList<CouponItem> list, final MyCallbackInterface callback) {
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String s) {
                try {
                    JSONObject root = new JSONObject(s);
                    JSONArray a = root.getJSONArray("list");
                    int cnt = a.length();
                    for(int i = 0 ; i < cnt ; ++i) {
                        CouponItem newItem = new CouponItem();
                        JSONObject o = a.getJSONObject(i);
                        newItem.sn = o.getInt("sn");
                        newItem.title = o.getString("name");
                        newItem.sURL = o.getString("link");
                        newItem.iconResId = R.drawable.premium_icon_cjone;
                        newItem.iconPath = o.getString("iconpath");
                        newItem.type = o.getInt("type");
                        newItem.point = o.getInt("reward");
                        newItem.sDesc =  o.getString("desc");
                        list.add(newItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(callback != null)
                        callback.onResult(-1);
                    return;
                }

                if(callback != null)
                    callback.onResult(0);
            }
        }).Get(0, Global.BASE_URL + "/get/couponlist?token=" + MyPassport.getInstance().getToken());
    }

    public static void GetItemList(int type, final ArrayList<GoodsItem> list, final MyCallbackInterface callback) {
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String s) {
                try {
                    JSONObject root = new JSONObject(s);
                    JSONArray a = root.getJSONArray("list");
                    int cnt = a.length();
                    for(int i = 0 ; i < cnt ; ++i) {
                        GoodsItem newItem = new GoodsItem();
                        JSONObject o = a.getJSONObject(i);
                        newItem.sn = o.getInt("sn");
                        newItem.title = o.getString("name");
                        newItem.publisher = o.getString("publisher");
                        newItem.iconResId = R.drawable.premium_icon_coupang;
                        newItem.iconPath = o.getString("iconpath");
                        newItem.point = o.getInt("price");
                        list.add(newItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(callback != null) {
                        callback.onResult(-99);
                    }
                }

                if(callback != null) {
                    callback.onResult(0);
                }
            }
        }).Get(0, Global.BASE_URL + "/get/itemlist?type="+type+"&token=" + MyPassport.getInstance().getToken());
    }

    public static void GetBuyList(final ArrayList<GoodsItem> list, final MyCallbackInterface callback) {
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String s) {
                try {
                    JSONObject root = new JSONObject(s);
                    JSONArray a = root.getJSONArray("list");
                    int cnt = a.length();
                    for(int i = 0 ; i < cnt ; ++i) {
                        GoodsItem newItem = new GoodsItem();
                        JSONObject o = a.getJSONObject(i);
                        newItem.sn = o.getInt("sn");
                        newItem.title = o.getString("name");
                        newItem.publisher = o.getString("publisher");
                        newItem.iconResId = R.drawable.premium_icon_coupang;
                        newItem.iconPath = o.getString("iconpath");
                        newItem.point = o.getInt("price");
                        list.add(newItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(callback != null) {
                        callback.onResult(-99);
                    }
                }

                if(callback != null) {
                    callback.onResult(0);
                }
            }
        }).Get(0, Global.BASE_URL + "/get/buylist?token=" + MyPassport.getInstance().getToken());
    }

    public static void ViewAd(int sn, final MyCallbackInterface callback) {
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String s) {
                if(nRet != 0) {
                    if(callback != null)
                        callback.onResult(-99);
                    return;
                }
                int nJSONRet = 0;
                try {
                    JSONObject root = new JSONObject(s);
                    nJSONRet = root.getInt("ret");
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(callback != null)
                        callback.onResult(-1);
                    return;
                }

                if(callback != null)
                    callback.onResult(nJSONRet);
            }
        }).Get(0, Global.BASE_URL + "/viewad?sn=" + sn + "&token=" + MyPassport.getInstance().getToken());
    }

    public static void BuyItem(int itemsn, final MyCallbackInterface callback) {
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String s) {
                if(nRet != 0) {
                    if(callback != null)
                        callback.onResult(-99);
                    return;
                }
                int nJSONRet = 0;
                try {
                    JSONObject root = new JSONObject(s);
                    nJSONRet = root.getInt("ret");
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(callback != null)
                        callback.onResult(-1);
                    return;
                }

                if(callback != null)
                    callback.onResult(nJSONRet);
            }
        }).Get(0, Global.BASE_URL + "/buy?itemsn=" + itemsn + "&token=" + MyPassport.getInstance().getToken());
    }

    public static void UseGachaBox(final GachaBoxInfo boxinfo, final MyCallbackInterface callback) {
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String s) {
                if(nRet != 0) {
                    if(callback != null)
                        callback.onResult(-99);
                    return;
                }
                int nJSONRet = 0;
                try {
                    JSONObject root = new JSONObject(s);
                    nJSONRet = root.getInt("ret");
                    if(nJSONRet == 0 ){
                        JSONObject gachainfo = root.getJSONObject("gachainfo");
                        boxinfo.sName = gachainfo.getString("name");
                        boxinfo.iconpath = gachainfo.getString("iconpath");
                        boxinfo.nLevel = gachainfo.getInt("level");
                        boxinfo.nGrade = gachainfo.getInt("grade");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(callback != null)
                        callback.onResult(-1);
                    return;
                }

                if(callback != null)
                    callback.onResult(nJSONRet);
            }
        }).Get(0, Global.BASE_URL + "/useGacha?token=" + MyPassport.getInstance().getToken());
    }

    public static void GetAd(final StringBuilder buf, final MyCallbackInterface callback) {
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String s) {
                if (nRet != 0) {
                    if (callback != null)
                        callback.onResult(-99);
                    return;
                }
                int nJSONRet = 0;
                String sSerial = "";
                try {
                    JSONObject root = new JSONObject(s);
                    nJSONRet = root.getInt("ret");
                    if (nJSONRet == 0) {
                        buf.append(root.getString("serial"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callback != null)
                        callback.onResult(-1);
                    return;
                }

                if (callback != null)
                    callback.onResult(nJSONRet);
            }
        }).Get(0, Global.BASE_URL + "/getmainad?token=" + MyPassport.getInstance().getToken());
    }

    public static void ViewMainAd(final String sSerial, final MyCallbackInterface callback) {
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String s) {
                if (nRet != 0) {
                    if (callback != null)
                        callback.onResult(-99);
                    return;
                }
                int nJSONRet = 0;
                try {
                    JSONObject root = new JSONObject(s);
                    nJSONRet = root.getInt("ret");
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callback != null)
                        callback.onResult(-1);
                    return;
                }

                if (callback != null)
                    callback.onResult(nJSONRet);
            }
        }).Get(0, Global.BASE_URL + "/viewmainad?sn="+sSerial+"&token=" + MyPassport.getInstance().getToken());
    }

    public static void getBonusInfo(final BonusManager man, final MyCallbackInterface callback) {
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String s) {
                if (nRet != 0) {
                    if (callback != null)
                        callback.onResult(-99);
                    return;
                }
                int nJSONRet = 0;
                try {
                    JSONObject root = new JSONObject(s);
                    nJSONRet = root.getInt("ret");
                    if(nJSONRet != 0) {
                        callback.onResult(nJSONRet);
                        return;
                    }

                    man.clear();

                    JSONArray aBonusInfo = root.getJSONArray("bonusinfo");
                    int bilen = aBonusInfo.length();
                    for(int i = 0 ; i < bilen ; ++i) {
                        JSONObject bitem = aBonusInfo.getJSONObject(i);
                        int no = bitem.getInt("no");
                        String pub = bitem.getString("pub");
                        String name = bitem.getString("name");
                        int reqLevel = bitem.getInt("reqLevel");
                        String ipath = bitem.getString("iconpath");
                        JSONArray reqGachaList = bitem.getJSONArray("reqGachaList");
                        String link = bitem.getString("link");
                        ArrayList<Integer> aList = new ArrayList<>();
                        for(int i2 = 0 ; i2 < reqGachaList.length() ; ++i2) {
                            aList.add(reqGachaList.getInt(i2));
                        }
                        man.addSection(reqLevel);
                        man.add(no, pub, name, reqLevel, ipath, aList, link);
                    }

                    JSONArray aMyList = root.getJSONArray("mygachalist");
                    ArrayList<Integer> aMyGachaList = new ArrayList<>();
                    int mllen = aMyList.length();
                    for(int i = 0 ; i < mllen ; ++i) {
                        JSONObject myitem = aMyList.getJSONObject(i);
                        int sn = myitem.getInt("sn");
                        int itemid = myitem.getInt("itemid");
                        man.addMyGacha(sn, itemid);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callback != null)
                        callback.onResult(-1);
                    return;
                }

                if (callback != null)
                    callback.onResult(nJSONRet);
            }
        }).Get(0, Global.BASE_URL + "/get/bonusinfo?token=" + MyPassport.getInstance().getToken());
    }

    public static void Signup(String id, String pw, String nick , final MyCallbackInterface callback) {
        Map<String, Object> mParams = new HashMap<String, Object>();
        mParams.put("id", id);
        mParams.put("pw", pw);
        mParams.put("nick", nick);
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String sResponse) {
                callback.onResult(nRet);
            }
        }).Post(0, Global.BASE_URL + "/signup", mParams);
    }
}
