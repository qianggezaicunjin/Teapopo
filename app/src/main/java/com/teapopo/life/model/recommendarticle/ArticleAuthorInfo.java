package com.teapopo.life.model.recommendarticle;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by louiszgm on 2016/4/23.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ArticleAuthorInfo {
    @JsonField(name = "avatar")
    public String avatarUrl;
    @JsonField(name = "id")
    public String authorId;

    public String nickname;
}
