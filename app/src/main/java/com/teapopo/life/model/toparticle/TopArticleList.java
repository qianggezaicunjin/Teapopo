package com.teapopo.life.model.toparticle;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by louiszgm-pc on 2016/5/22.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class TopArticleList {
   public TopArticleData data;
}
