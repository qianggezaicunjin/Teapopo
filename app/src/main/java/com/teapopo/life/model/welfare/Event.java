package com.teapopo.life.model.welfare;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/6/22.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Event extends BaseEntity {

    public String id;

    public String intro;
    @JsonField(name = "jingle")
    public String adWords;

    public String name;
    @JsonField(name = "wap_banner")
    public String bannerImg;
    @JsonField(name = "wap_cover")
    public String coverImg;

    public long start_time;

    public long end_time;

    public String min_price;
}
