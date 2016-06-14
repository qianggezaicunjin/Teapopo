package com.teapopo.life.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Xml;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import timber.log.Timber;

public class DataUtils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
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

    /**
     *
     * @param bitmap
     * @return
     */
    public static String imgToBase64(Bitmap bitmap) {
//        Bitmap bitmap = null;
//        if (imgPath != null && imgPath.length() > 0) {
//            bitmap = readBitmap(imgPath);
//        }
//        if (bitmap == null) {
//            //bitmap not found!!
//        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

}
    private static Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }
    }
    public static void toggleSoftInput(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(view.getWindowToken(),0);
    }
    public static void closeSoftInput(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()){
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }

    }
}
