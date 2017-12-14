package com.dacom.damoney.AlertManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dacom.damoney.R;

/**
 * Created by nnnyyy on 2017-05-25.
 */

public class AlertManager {
    public interface OnClickListener {
        public void onClick(View v, int which);
    }
    public static void ShowOk(Context context , String sTitle, String sMsg, String sOkText, final AlertManager.OnClickListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_format_1);
        TextView tvMsg = (TextView)dialog.findViewById(R.id.tvMsg);
        tvMsg.setText(sMsg);
        Button btnOk = (Button)dialog.findViewById(R.id.tvOkBtn);
        btnOk.setText(sOkText);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if( listener != null ) {
                    listener.onClick(v, 1);
                }
            }
        });
        dialog.show();

        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater == null) return;
        AlertFormat1Binding bind = DataBindingUtil.inflate(inflater, R.layout.alert_format_1, null, false);
        builder.setTitle(sTitle);
        bind.tvMsg.setText(sMsg);
        builder.setPositiveButton(sOkText, listener);
        builder.setCancelable(false);
        builder.setView(bind.getRoot());
        AlertDialog alert = builder.create();
        alert.show();*/
    }

    public static void ShowYesNo(Context context , String sTitle, String sMsg, String sOkText, String sNoText, final AlertManager.OnClickListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_format_yesno);
        TextView tvMsg = (TextView)dialog.findViewById(R.id.tvMsg);
        tvMsg.setText(sMsg);
        Button btnOk = (Button)dialog.findViewById(R.id.tvOkBtn);
        btnOk.setText(sOkText);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(listener !=null)
                    listener.onClick(v, 1);
            }
        });

        Button btnNo = (Button)dialog.findViewById(R.id.tvNoBtn);
        btnNo.setText(sNoText);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(listener !=null)
                    listener.onClick(v, 0);
            }
        });

        dialog.show();
    }
}
