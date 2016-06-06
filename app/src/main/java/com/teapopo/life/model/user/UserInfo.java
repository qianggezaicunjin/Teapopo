package com.teapopo.life.model.user;

import android.databinding.Observable;
import android.databinding.ObservableField;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.util.DataUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/11.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class UserInfo extends BaseEntity {

    public  String avatar;

    public String city;

    public  String fans_num;

    public String id;

    public String nickname;

    public String points;

    public String posts_num;

    public String province;

    public String sex;

    public  String signature;

    public String subscribe;

    public  String subscribe_num;

    public String title;

    /**
     * userinfo 序列化成 string
     *
     * @param userInfo 要序列化的userinfo
     * @return 序列化之后的string
     */
    public static String encodeUserInfo(SerializableUserInfo userInfo) {
        if (userInfo == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(userInfo);
        } catch (IOException e) {
            Timber.d( "IOException in encodeCookie", e);
            return null;
        }

        return DataUtils.byteArrayToHexString(os.toByteArray());
    }

    /**
     * 将字符串反序列化成cookies
     *
     * @param userinfoString cookies string
     * @return cookie object
     */
    public static UserInfo decodeUserInfo(String userinfoString) {
        byte[] bytes = DataUtils.hexStringToByteArray(userinfoString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        UserInfo userInfo = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            userInfo = ((SerializableUserInfo) objectInputStream.readObject()).getUserinfo_out();
        } catch (IOException e) {
            Timber.d("IOException in decodeCookie", e);
        } catch (ClassNotFoundException e) {
            Timber.d("ClassNotFoundException in decodeCookie", e);
        }

        return userInfo;
    }
}
