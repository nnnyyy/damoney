package com.dacom.damoney;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.dacom.damoney.databinding.ToastPointBinding;

import java.util.logging.Handler;

/**
 * Created by nnnyy on 2017-11-19.
 */

public class CustomToast {
    public static void PointSave(Context context, int point) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater == null) return;
        ToastPointBinding bind = DataBindingUtil.inflate(inflater, R.layout.toast_point, null, false);
        bind.tvCoin.setText(String.valueOf(point));
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(bind.getRoot());
        toast.show();
    }
}
