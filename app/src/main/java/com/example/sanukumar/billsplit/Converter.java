package com.example.sanukumar.billsplit;

import android.content.Context;

/**
 * Created by sanukumar on 12/07/18.
 */

public class Converter {

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
