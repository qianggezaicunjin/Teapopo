package com.teapopo.life.model.recommendarticle;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by louiszgm on 2016/4/23.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class RecommendArticle {

    public int browse_num;

    public int collect_num;

    public int comment_num;

    public String cover;

    public String excerpt;

    @JsonField(name = "id")
    public String articleId;

    public int like_num;

    public String member_id;

    public long publish_time;

    public String title;


}
