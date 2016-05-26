package com.teapopo.life.model.Tag;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/5/7.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Tag extends BaseEntity {

    public String count;
    @JsonField(name = "cover")
    public String coverImage;

    public String id;

    public String name;
}
