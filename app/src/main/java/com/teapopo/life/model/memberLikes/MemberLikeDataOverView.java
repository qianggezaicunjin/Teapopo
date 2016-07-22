package com.teapopo.life.model.memberLikes;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/7/22.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class MemberLikeDataOverView extends BaseEntity {

    public String cover;

    public String id;

    public String title;
}
