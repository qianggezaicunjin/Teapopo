package com.teapopo.life.model.comment;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/6/4.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Reply extends BaseEntity {
    public String member_id;

    public String content;

    public long add_time;//回复时间

    //新增的属性
    public AuthorInfo authorInfo;//回复人的基本信息
}
