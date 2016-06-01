package com.teapopo.life.model.articleinfo;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

import java.util.List;

/**
 * Created by louiszgm on 2016/6/1.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ArticleInfo extends BaseEntity {

    public String browse_num;//浏览数

    public String collect_num;

    public String comment_num;

    public String content;

    public String like_num;

    public String member_id;

    public String publish_time;

    public String share_num;

    public String title;

    //以下是增加的属性
    List<String> articleImageUrls;


}
