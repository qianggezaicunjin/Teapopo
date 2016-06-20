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
import com.teapopo.life.viewModel.BaseRecyclerViewModel;
import com.teapopo.life.viewModel.BaseViewModel;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/1.
 */
public class ArticleInfoViewModel extends BaseViewModel  {
    private ArticleInfoModel mModel;

    @Bindable
    public ArticleInfo articleInfo =new ArticleInfo();

    @Bindable
    public String mEditText_inputCommentHint = "发表评论";

    public void setArticleInfo(ArticleInfo articleInfo){
        this.articleInfo = articleInfo;
    }
    public ArticleInfoViewModel(ArticleInfoModel model){
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
        mModel.addComment(articleId,type,content);
    }


    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.ArticleInfoModel_GetInfo){
            this.articleInfo = (ArticleInfo) data.t;
            notifyPropertyChanged(BR.articleInfo);
        }else if(action == Action.ArticleInfoModel_AddComment){
            Timber.d("添加评论成功 ");
            Comment comment = (Comment) data.t;
           articleInfo.commentList.add(0,comment);
            notifyPropertyChanged(BR.articleInfo);
            notifyPropertyChanged(BR.editText_inputCommentHint);
        }else if(action == Action.ArticleInfoModel_ReplyComment){
            Timber.d("回复成功，发送通知更新界面");
            Reply reply = (Reply) data.t;
            ComponentHolder.getAppComponent().rxbus().post(reply);
            notifyPropertyChanged(BR.editText_inputCommentHint);
        }
    }
}
