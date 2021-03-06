package com.dacom.damoney.Advertisement;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by nnnyy on 2017-10-15.
 */

public class AdsManager {
    Context mContext;
    static String mSerial;
    static AdsResultListener listener;
    int mDebugCnt = 0;

    public AdsManager(Context context) {
        mContext = context;
    }

    public boolean load() {
        if(listener == null) return false;

        new LoadingTask().execute();
        return true;
    }

    public void setListener(AdsResultListener _listener) {
        listener = _listener;
    }

    public void startFullAds(String sSerial) {
        mSerial= sSerial;
        Intent intent = null;
        switch(mDebugCnt%2) {
            case 0:
                //intent = new Intent(mContext, AdsActivity.class);
                intent = new Intent(mContext, AdsTouchActivity.class);
                break;
            case 1:
                intent = new Intent(mContext, AdsActivity.class);
                break;
        }
        mDebugCnt++;
        if(intent != null)
            mContext.startActivity(intent);
    }

    private class LoadingTask extends AsyncTask<Long, Long, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(Long... longs) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return Long.valueOf(-1);
            }
            return Long.valueOf(0);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            listener.onAdsLoaded();
        }
    }
}
