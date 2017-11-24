package com.dacom.damoney;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacom.damoney.databinding.FragmentBmcommunityBinding;

import im.delight.android.webview.AdvancedWebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMCommunityFragment extends FragmentEx implements AdvancedWebView.Listener {
    FragmentBmcommunityBinding mBind;

    public BMCommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_bmcommunity, container, false);
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWebView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBind.webview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBind.webview.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBind.webview.onDestroy();
    }

    @Override
    public void onBack(Context context) {
        super.onBack(context);
        if(!mBind.webview.onBackPressed()) { return; }
        ((MainActivity)context).changeNav(R.id.act_home);
    }

    public void setupWebView() {
        mBind.webview.setListener(getActivity(), this);
        mBind.webview.loadUrl("http://gae9.com/");
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }
}
