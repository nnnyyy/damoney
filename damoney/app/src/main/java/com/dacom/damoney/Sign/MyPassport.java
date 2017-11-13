package com.dacom.damoney.Sign;

import android.content.Context;

import com.dacom.damoney.Storage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nnnyyy on 2017-11-13.
 */

public class MyPassport {
    public static final String MP_INFO = "myinfo";
    public int nPoint = 0;
    protected Context mContext;
    protected static MyPassport obj;

    public static MyPassport getInstance() {
        if( obj == null ) {
            obj = new MyPassport();
        }

        return obj;
    }

    public void init(Context context) {
        mContext = context;
    }
    public boolean loadInfo() throws JSONException {
        if(mContext == null) return false;
        if(!Storage.have(mContext, MP_INFO)) {
            // 기본 세팅. 나중에는 어짜피 DB에서 가져온다.
            nPoint = 0;
            saveInfo();
            return true;
        }
        String json_myinfo = Storage.load(mContext, MP_INFO);
        JSONObject loadedData = new JSONObject(json_myinfo);
        nPoint = loadedData.getInt("point");
        return true;
    }

    public boolean saveInfo() throws JSONException {
        if(mContext == null) return false;
        JSONObject data = new JSONObject();
        data.put("point", nPoint);
        Storage.save(mContext, MP_INFO, data.toString());

        return true;
    }
}
