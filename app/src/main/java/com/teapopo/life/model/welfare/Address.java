package com.teapopo.life.model.welfare;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/7/1.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Address extends BaseEntity {
    public String address;

    public String area_id;

    public String area_info;

    public String city_id;

    public String id;

    public boolean is_default;

    public String phone;

    public String province_id;

    public String truename;

    public String zipcode;

    //新增的属性
    public boolean isSelected;

    public String getBaseInfo(){
        return truename+" "+phone;
    }


}
