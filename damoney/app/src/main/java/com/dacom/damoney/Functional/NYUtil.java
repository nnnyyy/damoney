package com.dacom.damoney.Functional;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by nnnyyy on 2017-11-09.
 */

public class NYUtil {
    public static int dptopx(Context context, int dp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
