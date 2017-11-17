package com.dacom.damoney;

import android.os.Handler;
import android.os.Looper;

import com.dacom.damoney.Sign.MyPassport;
import com.yaong.nnnyyy.nyhttphelper.HttpHelper;
import com.yaong.nnnyyy.nyhttphelper.HttpHelperListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nnnyyy on 2017-11-17.
 */

public class DamoneyHttpHelper {
    interface MyCallbackInterface {
        void onResult(int nRet);
    }

    public static void GetPremiumList(final ArrayList<PremiumItem> list, final MyCallbackInterface callback) {
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
                        newItem.type = o.getInt("type");
                        newItem.point = o.getInt("reward");
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
}
