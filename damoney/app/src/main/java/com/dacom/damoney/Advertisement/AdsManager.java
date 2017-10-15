package com.dacom.damoney.Advertisement;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.Serializable;

/**
 * Created by nnnyy on 2017-10-15.
 */

public class AdsManager implements Serializable{
    Context mContext;
    AdsResultListener listener;

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

    public void startFullAds() {
        Intent intent = new Intent(mContext, AdsActivity.class);
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
                Thread.sleep(3000);
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
