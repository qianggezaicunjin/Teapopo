package com.teapopo.life.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by louiszgm on 2016/5/11.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ErroInfo extends BaseEntity {
    public String errcode;

    public String errmsg;
}
