package com.teapopo.life.model.recommendarticle;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.recommendarticle.ArticleAuthorInfo;
import com.teapopo.life.model.recommendarticle.ArticleImage;
import com.teapopo.life.model.recommendarticle.ArticleTag;
import com.teapopo.life.model.recommendarticle.RecommendArticle;

import java.util.List;

/**
 * Created by louiszgm on 2016/4/23.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class RecommendData {
    @JsonField(name = "posts")
    public List<RecommendArticle> recommendArticles;

    @JsonField(name = "images")
    public List<ArticleImage> articleImages;

    @JsonField(name = "tags")
    public List<ArticleTag> articleTags;

    @JsonField(name = "members")
    public List<ArticleAuthorInfo> articleAuthorInfos;
}
