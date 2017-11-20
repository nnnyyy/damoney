package com.dacom.damoney;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by nnnyyy on 2017-11-13.
 */

public class CustomStartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.ttf"))
                .addBold(Typekit.createFromAsset(this,"fonts/NanumBarunGothicBold.ttf"))
                .addCustom1(Typekit.createFromAsset(this,"fonts/NanumBarunGothicLight.ttf"))
                .addCustom2(Typekit.createFromAsset(this,"fonts/DK Canoodle.otf"));
    }
}
