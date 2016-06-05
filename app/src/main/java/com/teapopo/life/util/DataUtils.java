package com.teapopo.life.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import timber.log.Timber;

public class DataUtils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    /**
     * 时间戳转为月日，时分
     * @param cc_time
     * @return
     */
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        //同理也可以转为其它样式的时间格式.例如："yyyy/MM/dd HH:mm"
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);

        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;
    }

    /**
     * 根据布局文件返回属性集
     * @param context
     * @param xmlid
     * @return
     */
    public static AttributeSet getAttributeSetFromXml(Context context, int xmlid){

        XmlPullParser parser = context.getResources().getXml(xmlid);
        AttributeSet attributes = Xml.asAttributeSet(parser);
        int type;
        try{
            while ((type = parser.next()) != XmlPullParser.START_TAG &&
                    type != XmlPullParser.END_DOCUMENT) {
                // Empty
            }

            if (type != XmlPullParser.START_TAG) {
                Timber.e("the xml file is error!");
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timber.d("属性个数:"+parser.getAttributeCount());
        return attributes;
    }

    public static void toggleSoftInput(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void closeSoftInput(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()){
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }

    }
}
