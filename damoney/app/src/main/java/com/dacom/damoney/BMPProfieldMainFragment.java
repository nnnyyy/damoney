package com.dacom.damoney;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.dacom.damoney.AlertManager.AlertManager;
import com.dacom.damoney.Push.DamoneyPushManager;
import com.dacom.damoney.Sign.MyPassport;
import com.dacom.damoney.databinding.FragmentPprofileMainBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMPProfieldMainFragment extends Fragment {
    FragmentPprofileMainBinding mBind;
    BMProfileFragment parent;

    public BMPProfieldMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_pprofile_main, container, false);
        DamoneyPushManager.loadPushSettings(getContext());
        mBind.swtAds.setChecked(DamoneyPushManager.bEnableAdsPUsh);
        mBind.swtAds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Storage.saveBoolean(getContext(), DamoneyPushManager.PK_ADS, isChecked);
                if(isChecked) {
                    AlertManager.ShowOk(getContext(), "알림", "푸쉬 메시지가 설정 되었습니다.", "닫기", new AlertManager.OnClickListener() {
                        @Override
                        public void onClick(View v, int which) {
                            DamoneyPushManager.Reserv(getContext(), DamoneyPushManager.FIVE_MIN);
                        }
                    });
                }
            }
        });
        return mBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyPassport.getInstance().RequestInfo(new MyPassport.RequestInfoListener() {
            @Override
            public void onResult(int nRet) {
                if(nRet != 0) return;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mBind.tvNick.setText(MyPassport.getInstance().sNick);
                        mBind.tvCoin.setText(String.valueOf(MyPassport.getInstance().nPoint));
                    }
                });
            }
        });

        mBind.btnBuyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.changeChildFragment(1);
            }
        });
    }

    public void setPrarent(BMProfileFragment _parent) {
        parent = _parent;
    }
}
