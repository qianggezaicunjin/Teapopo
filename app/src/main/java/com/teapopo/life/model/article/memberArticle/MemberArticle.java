package com.teapopo.life.model.article.memberArticle;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.article.Article;

/**
 * Created by louiszgm on 2016/7/20.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class MemberArticle extends Article {
}
