package com.teapopo.life.model.sharedpreferences;

import me.yokeyword.rxapi.Spf;

/**
 * Created by louiszgm on 2016/6/6.
 * 用来缓存回复评论的内容
 *
 * 用途:在发表评论的时候，用来判断是发表评论还是回复评论
 *
 */
@Spf
public class ReplyCommentSp {
    String commentId;//回复的评论id

    String replyname;//回复人的nickname

    String content;//回复的内容
}
