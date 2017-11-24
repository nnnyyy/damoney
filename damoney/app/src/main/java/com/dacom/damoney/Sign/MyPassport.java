package com.dacom.damoney.Sign;

import android.content.Context;

import com.dacom.damoney.Global;
import com.dacom.damoney.Storage;
import com.yaong.nnnyyy.nyhttphelper.HttpHelper;
import com.yaong.nnnyyy.nyhttphelper.HttpHelperListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by nnnyyy on 2017-11-13.
 */

public class MyPassport {
    public static final String TOKEN_KEY = "AccessToken";
    public static final String COUPON_KEY = "MyCouponList";
    public int nPoint = 0;
    public int nGachaCnt = 0;
    public int nLevel = 0;
    public int nExpMax = 0;
    public int nCurExp = 0;
    public boolean bLevelup = false;
    protected String sToken;
    protected Context mContext;
    protected static MyPassport obj;
    protected static HashSet<Integer> mCoupon = new HashSet<>();

    public interface RequestInfoListener {
        void onResult(int nRet);
    }

    public static MyPassport getInstance() {
        if( obj == null ) {
            obj = new MyPassport();
        }

        return obj;
    }

    public void init(Context context) {
        mContext = context;
    }

    public void RequestInfo(final RequestInfoListener _listener) {
        loadCoupon();
        new HttpHelper().SetListener(new HttpHelperListener() {
            @Override
            public void onResponse(int nType, int nRet, String sResponse) {
                if(nRet != 0) {
                    if(_listener != null) {
                        _listener.onResult(nRet);
                    }
                    return;
                }

                JSONObject obj = null;
                try {
                    obj = new JSONObject(sResponse);
                    Integer ret = obj.getInt("ret");
                    if(ret != 0) {
                        if(_listener != null) {
                            _listener.onResult(nRet);
                        }
                        return;
                    }

                    nPoint = obj.getInt("point");
                    nGachaCnt = obj.getInt("gacha");
                    int level = obj.getInt("level");
                    if(nLevel != 0 && nLevel < level) {
                        bLevelup = true;
                    }
                    nLevel = level;
                    nExpMax = obj.getInt("expMax");
                    nCurExp = obj.getInt("curExp");

                    if(_listener != null) {
                        _listener.onResult(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(_listener != null) {
                        _listener.onResult(-1);
                    }
                    return;
                }
            }
        }).Get(0, Global.BASE_URL + "/getinfo?token=" + getToken());
    }

    public String loadToken(Context context) {
        String s = Storage.load(context, TOKEN_KEY);
        sToken = s;
        return s;
    }

    public void saveToken(Context context, String sTokenString) {
        Storage.save(context, TOKEN_KEY, sTokenString);
        loadToken(context);
    }
    public void deleteToken(Context context) {
        Storage.delete(context, TOKEN_KEY);
    }
    public String getToken() { return sToken; }

    public void saveCoupon(int sn) {
        mCoupon.add(sn);
        Iterator<Integer> iter = mCoupon.iterator();
        JSONArray arr = new JSONArray();
        while(iter.hasNext()) {
            Integer couponSN = iter.next();
            arr.put(couponSN);
        }
        Storage.save(mContext, COUPON_KEY ,arr.toString());
    }

    public void loadCoupon() {
        String sJsonList = Storage.load(mContext, COUPON_KEY);
        if(sJsonList == "") return;
        try {
            JSONArray arr = new JSONArray(sJsonList);
            for(int i = 0 ; i < arr.length() ; ++i) {
                mCoupon.add(arr.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isExistCoupon(int sn) {
        return mCoupon.contains(sn);
    }
}
