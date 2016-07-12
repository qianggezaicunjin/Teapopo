package com.teapopo.life.model.address.district;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/7/12.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Area extends BaseEntity {
    String id;
    String name;

    @Override
    public String toString() {
        return name;
    }
}
