package com.dacom.damoney.Sign;

import android.content.Context;

import com.dacom.damoney.Storage;
import com.yaong.nnnyyy.nyhttphelper.HttpHelper;
import com.yaong.nnnyyy.nyhttphelper.HttpHelperListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nnnyyy on 2017-11-13.
 */

public class MyPassport {
    public static final String TOKEN_KEY = "AccessToken";
    public int nPoint = 0;
    public int nGachaCnt = 0;
    protected String sToken;
    protected Context mContext;
    protected static MyPassport obj;

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
        }).Get(0, "http://4seasonpension.com:3003/getinfo?token=" + getToken());
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
}
