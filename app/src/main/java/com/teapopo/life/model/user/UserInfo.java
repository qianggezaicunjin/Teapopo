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

    public  ObservableField<String> avatar = new ObservableField<>();

    public String city;

    public  ObservableField<String> fans_num = new ObservableField<>();

    public String id;

    public  ObservableField<String> nickname = new ObservableField<>();

    public String points;

    public  ObservableField<String> posts_num = new ObservableField<>();

    public String province;

    public String sex;

    public  ObservableField<String> signature = new ObservableField<>();

    public String subscribe;

    public  ObservableField<String> subscribe_num = new ObservableField<>();

    public String title;
}
