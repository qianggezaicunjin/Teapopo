package com.teapopo.life.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

public class ViewUtils {

    public static float convertPixelsToDp(float px, Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    public static Drawable findDrawableById(int id,Context context){
        return context.getResources().getDrawable(id);
    }
}
