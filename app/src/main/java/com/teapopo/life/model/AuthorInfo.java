package com.teapopo.life.model;

import android.databinding.BindingConversion;
import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.util.DataUtils;

/**
 * Created by louiszgm on 2016/6/2.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class AuthorInfo extends BaseEntity implements Parcelable{
    public String avatar;

    public String getAvatarUrl(){
        return NetWorkService.IMAGE_ENDPOINT+avatar+"_100x100"+NetWorkService.IMAGE_EXT;
    }
    public String nickname;

    public String fans_num;
    public String getFans_num(){
        return "粉丝数:"+fans_num;
    }

    public String subscribe_num;
    public String getSubscribe_num(){
        return "关注数:"+subscribe_num+"   "+"粉丝数:"+fans_num;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar);
        dest.writeString(nickname);
        dest.writeString(fans_num);
        dest.writeString(signature);
        dest.writeString(subscribe_num);
        dest.writeByte((byte)(isSubscribe ?1:0));
    }

    protected AuthorInfo(Parcel in) {
        avatar = in.readString();
        nickname = in.readString();
        fans_num = in.readString();
        signature = in.readString();
        subscribe_num=in.readString();
        isSubscribe =in.readByte()!=0;
    }
    public AuthorInfo(){

    }
    public static final Parcelable.Creator<AuthorInfo> CREATOR = new Parcelable.Creator<AuthorInfo>() {
        public AuthorInfo createFromParcel(Parcel source) {
            return new AuthorInfo(source);
        }

        public AuthorInfo[] newArray(int size) {
            return new AuthorInfo[size];
        }
    };
}
