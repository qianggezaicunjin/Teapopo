package com.teapopo.life.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.teapopo.life.BR;
import com.teapopo.life.R;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.article.ArticleItemModel;
import com.teapopo.life.model.article.likearticle.HomeLikeArticle;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.util.ViewUtils;
import com.teapopo.life.view.adapter.gridview.NineImageGridAdapter;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.Member.MemberFragment;

import timber.log.Timber;


/**
 * Created by louiszgm on 2016/5/27.
 */
public class ArticleItemViewModel extends BaseViewModel {
    private ArticleItemModel mModel;

    @Bindable
    public Article article;

    public AuthorInfo authorInfo;

    public ArticleItemViewModel(ArticleItemModel model){
        mModel = model;
        mModel.setView(this);
    }

    //点赞/取消点赞文章
    public void doLikeArticle() {
        String articleId = article.articleId;
        mModel.likeArticleOrNot(!article.isLike,articleId);
    }
    //关注文章
    public void doFocusAction() {
        String memberId = article.member_id;
        mModel.focusMember(memberId);
    }

    //根据标签名字显示对应文章
    public void showTagArticle(String tagname) {
    }

    public void getMemberInfo(){
        mModel.getMemberInfo(article.member_id);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.ArticleItemModel_likeArticleOrNot){
            Timber.d("点赞");
           article.isLike = !article.isLike;
           int likenum = Integer.parseInt(article.like_num);
            if(article.isLike){
                likenum+=1;
            }else {
                likenum-=1;
            }
            article.like_num = String.valueOf(likenum);
            notifyPropertyChanged(BR.article);
        }else if(action == Action.ArticleItemModel_focusMember){
            article.authorInfo.isSubscribe = !article.authorInfo.isSubscribe;
            notifyPropertyChanged(BR.article);
        }else if(action == Action.ArticleItemModel_GetMemberInfo){
            authorInfo= (AuthorInfo) data.t;
            Timber.d("会员数据");
            navToFragment(MemberFragment.newInstance(authorInfo,article.member_id));

        }
    }

}
