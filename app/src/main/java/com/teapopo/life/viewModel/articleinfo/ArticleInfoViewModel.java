package com.teapopo.life.viewModel.articleinfo;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;

import com.teapopo.life.BR;
import com.teapopo.life.databinding.FragmentArticleinfoBinding;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.articleinfo.ArticleInfo;
import com.teapopo.life.model.articleinfo.ArticleInfoModel;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.Reply;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.ArticleInfoFragment;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/1.
 */
public class ArticleInfoViewModel extends BaseObservable implements RequestView<ModelAction> {
    private ArticleInfoModel mModel;
    private ArticleInfoFragment mView;
    @Bindable
    public ArticleInfo articleInfo =new ArticleInfo();

    public ArticleInfoViewModel(Fragment view, ArticleInfoModel model){
        mView = (ArticleInfoFragment) view;
        mModel = model;
        mModel.setView(this);

    }



    public void requestData(String articleId){
        mModel.getArticleInfo(articleId);
    }

    /**
     * 回复评论
     * @param commentId
     * @param type 类型 0是posts 1是goods
     * @param content
     */
    public void replyComment(String commentId,int type,String content){
        mModel.replyComment(commentId,type,content);
    }

    /**
     * 添加评论
     * @param articleId
     * @param type 类型 0是posts 1是goods
     * @param content
     */
    public void addComment(String articleId,int type,String content){
        mModel.addComment(articleId,0,content);
    }



    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.ArticleInfoModel_GetInfo){
            this.articleInfo = (ArticleInfo) data.t;
            Timber.d("评论的个数为:%d",articleInfo.commentList.size());
          mView.refreshView(articleInfo);
            notifyPropertyChanged(BR.articleInfo);
        }else if(action == Action.ArticleInfoModel_AddComment){
            Comment comment = (Comment) data.t;
           mView.refreshAddComment(comment);
        }else if(action == Action.ArticleInfoModel_ReplyComment){
            Timber.d("回复成功，发送通知更新界面");
            Reply reply = (Reply) data.t;
            ComponentHolder.getAppComponent().rxbus().post(reply);
            mView.refreshWhenReplyDone();
        }
    }
    @Override
    public void onRequestErroInfo(String erroinfo) {

    }
}
