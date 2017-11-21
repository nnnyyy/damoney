package com.dacom.damoney;

import android.content.Context;
import android.content.Intent;

/**
 * Created by nnnyy on 2017-11-15.
 */

public class Global {
    //public static String BASE_URL = "http://10.0.2.2:3003";
    public static String BASE_URL = "http://52.79.205.198:3003";

    public static void OpenGacha(Context context) {
        Intent intent = new Intent(context, GetRewardActivity.class);
        context.startActivity(intent);
    }

    public static String GetGradeName(int grade) {
        switch(grade) {
            case 0: return "일반";
            case 1: return "레어";
            case 2: return "전설";
        }

        return "일반";
    }
}
