package com.teapopo.life.model;

import android.databinding.BindingConversion;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.util.DataUtils;

/**
 * Created by louiszgm on 2016/6/2.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class AuthorInfo extends BaseEntity {
    public String avatar;

    public String getAvatarUrl(){
        return NetWorkService.IMAGE_ENDPOINT+avatar+"_100x100"+NetWorkService.IMAGE_EXT;
    }
    public String nickname;

    public String fans_num;
    public String getFans_num(){
        return "粉丝数:"+fans_num;
    }
    public String signature;
    @JsonField(name = "subscribe")
    public boolean isSubscribe;

    @BindingConversion
    public static String getSubscribeState(boolean isSubscribe){
        if(isSubscribe){
            return "取消关注";
        }else {
            return "关注";
        }
    }
}
