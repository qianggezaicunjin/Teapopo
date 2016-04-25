package com.teapopo.life.model.recommendarticle;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by louiszgm on 2016/4/23.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ArticleImage {
    @JsonField(name = "post_id")
    public String articleId;
    @JsonField(name = "url")
    public List<String>  articleImageUrls;
}
