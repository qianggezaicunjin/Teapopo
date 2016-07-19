package com.teapopo.life.model.articleinfo;

import android.databinding.BindingConversion;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.util.DataUtils;

import java.util.ArrayList;
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

    public long publish_time;

    @BindingConversion
    public static String getTime(long time){
        return DataUtils.getStrTime(String.valueOf(time));
    }
    public String share_num;

    public String title;

    //以下是增加的属性
    public List<String> articleImageUrls = new ArrayList<>();

    public AuthorInfo authorInfo;//这篇文章的作者信息

    public boolean isLike;//是否喜欢这篇文章

    public List<String> tags = new ArrayList<>(); //标签

    public List<AuthorInfo> fans = new ArrayList<>();//喜欢这篇文章的会员信息

    public List<BaseEntity> commentList = new ArrayList<>();//评论列表
}
