package com.teapopo.life.model.category;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.recommendarticle.ArticleAuthorInfo;

import java.util.List;

/**
 * Created by louiszgm on 2016/5/7.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class CategoryData {
    @JsonField(name = "terms")
    public List<Category> categoryList;
}
