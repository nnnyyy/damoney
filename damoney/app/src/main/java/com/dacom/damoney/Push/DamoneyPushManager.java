package com.dacom.damoney.Push;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by nnnyy on 2017-12-01.
 */

public class DamoneyPushManager {
    public final static int FIVE_MIN = 5000;

    public static void Reserv(Context context, int waitTimeMilli) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, PushBroadcastReceiver.class);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        //알람 예약
        // 1분뒤에 AlarmOneMinuteBroadcastReceiver 호출 한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + waitTimeMilli, sender);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + waitTimeMilli, sender);
        } else {
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + waitTimeMilli, sender);
        }
    }
}
