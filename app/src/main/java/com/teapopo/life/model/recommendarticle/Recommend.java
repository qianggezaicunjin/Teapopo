package com.teapopo.life.model.recommendarticle;


import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Recommend {

    public RecommendData data;

    public int page;

    public int pages;

}
