package com.teapopo.life.model.user;

import android.databinding.Observable;
import android.databinding.ObservableField;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

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
}
