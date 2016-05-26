package com.teapopo.life.util;

import android.content.Context;
import android.net.ConnectivityManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    /**策略
     * 处理九宫格图片的显示，根据图片的张数显示不同大小
     * @param imageUrls 图片的个数
     * @return
     */
    public static List<String> dealSizeForNetImageUrl(List<String> imageUrls){
        List<String> results = new ArrayList<>();
        int size = results.size();
        if (size>3){
            for(String url:imageUrls){
                results.add(url+"_200*200");
            }
        }else if(size==2){
            for(String url:imageUrls){
                results.add(url+"_300*300");
            }
        }else {
            results = imageUrls;
        }
        return results;
    }

    /**
     * 根据图片的个数划分列数
     * @param size
     * @return
     */
    public static int dealImageShowColums(int size){
        if(size>=5){
            return 3;
        }else if(size<5&&size>1){
            return 2;
        }
        return 1;
    }
}
