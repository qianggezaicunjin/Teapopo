package com.teapopo.life.model.comment;

import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louiszgm on 2016/6/4.
 */
@com.bluelinelabs.logansquare.annotation.JsonObject(fieldDetectionPolicy = com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Comment extends BaseEntity {
    public String member_id;

    public String id;

    public String content;

    public long add_time;

    public String like_num = "0";

    public boolean is_like;

    //新增的属性
    public AuthorInfo authorInfo;

   public List<Reply> replyList = new ArrayList<>();//回复列表

    public String replyPosition;//用来标识回复的评论，是在评论列表的哪个位置
}
