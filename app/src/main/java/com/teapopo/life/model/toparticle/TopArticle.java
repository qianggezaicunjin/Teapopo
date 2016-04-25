package com.teapopo.life.model.toparticle;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by louiszgm on 2016/4/9 0009.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class TopArticle {
    @JsonField(name = "image")
    public String topImageUrl;

    public String title;

    @JsonField(name = "wap_link")
    public String contentUrl;
}
