package com.teapopo.life.model.address.district;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louiszgm on 2016/7/12.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Province extends BaseEntity {
    public String id;
    public String name;
    @JsonField(name = "child")
    public ArrayList<City> cityList;


    @Override
    public String toString() {
        return name;
    }
}
