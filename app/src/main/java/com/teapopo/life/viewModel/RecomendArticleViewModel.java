package com.teapopo.life.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.teapopo.life.model.recommendarticle.ArticleAuthorInfo;
import com.teapopo.life.model.recommendarticle.RecommendArticle;
import com.teapopo.life.model.recommendarticle.RecommendData;
import com.teapopo.life.util.DataUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class RecomendArticleViewModel extends BaseObservable {
    private Context mContext;
    private RecommendArticle mPost;
    private RecommendData mData;

    public RecomendArticleViewModel(Context context, RecommendArticle post, RecommendData data) {

        this.mContext = context;
        this.mPost = post;
        this.mData = data;
    }


    public String getLikeCount(){
        return String.valueOf(mPost.like_num);
    }

    public String getCommentCount(){
        return String.valueOf(mPost.comment_num);
    }

    public String getArticleContent(){
        return  mPost.excerpt;
    }

    public String getPublishTime(){
        return DataUtils.getStrTime(String.valueOf(mPost.publish_time));
    }

    public String getUserName() {
        if (getMember() != null) {
            return getMember().nickname;
        }
        return "无名";
    }

    public ArticleAuthorInfo getMember() {
        String id = mPost.member_id;
        if (mData != null) {
            List<ArticleAuthorInfo> members = mData.articleAuthorInfos;
            for (int i = 0; i < members.size(); i++) {
                ArticleAuthorInfo member = members.get(i);
                if (member.authorId.equals(id)) {
                    return member;
                }
            }
        }
        return null;
    }

}
