package com.teapopo.life.viewModel.commentList;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.teapopo.life.BR;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.articleinfo.ArticleInfo;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.Reply;
import com.teapopo.life.model.commentlist.CommentListModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxSubscriber;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/23.
 */
public class CommentListViewModel extends BaseRecyclerViewModel {
    private CommentListModel mModel;

    @Bindable
    public String mEditText_inputCommentHint = "发表评论";
    @Bindable
    public boolean showSoftInput = false;

    public CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Comment mReplyComment;//要回复的评论
    public void setShowSoftInput(boolean isShow){
        this.showSoftInput = isShow;
    }
    public CommentListViewModel(CommentListModel model){
        mModel = model;
        mModel.setView(this);
        tagWhenDoReplyAction();
    }

    public void getCommentList(String id,String classify){
        if(!mModel.isNoData){
            mModel.getCommentList(id,classify);
            startLoading();
        }
    }


    public void addCommentOrReply(String content,String articleId){
        if(TextUtils.isEmpty(content)){
            handleNoticeInfo("输入的内容不能为空");
        }else {
            //回复评论  or  发表评论
            if(mReplyComment!=null){
                //回复评论
                mModel.replyComment(mReplyComment.id,0,content);
            }else {
                //发表评论
                mModel.addComment(articleId,0,content);
            }
        }
    }
    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.CommentListModel_GetCommentList){
            Timber.d("获取评论列表成功");
            List<Comment> commentList = (List<Comment>) data.t;
            super.data.addAll(commentList);
            notifyPropertyChanged(BR.data);
            loading = false;
            notifyPropertyChanged(BR.loading);
        }else if(action == Action.CommentListModel_AddComment){
            Comment comment = (Comment) data.t;
            super.data.add(0,comment);
            handleNoticeInfo("发表评论成功");
            notifyPropertyChanged(BR.data);
            setSoftInputStateWhenCommentOrReply(false,false,null);
        }else if(action == Action.CommentListModel_ReplyComment){
            Timber.d("回复成功，发送通知更新界面");
            Reply reply = (Reply) data.t;
            //将回复的内容添加至对应的Comment,并标识回复的位置
            for(int i = 0;i<super.data.size();i++){
                Comment comment = (Comment) super.data.get(i);
                if(comment.id.equals(reply.commentId)){
                    comment.replyPosition = String.valueOf(i);
                    comment.replyList.add(reply);
                }
            }
            notifyPropertyChanged(BR.data);
            handleNoticeInfo("回复评论成功");
            setSoftInputStateWhenCommentOrReply(true,false,null);
        }
    }
    /**
     * 回复评论
     * 点击评论列表的某一个item的评论图标的时候，会发出一个Comment通知要回复的的是这个comment
     * 接收该事件并处理
     */
    private void tagWhenDoReplyAction() {
        Observable<Comment> observable = ComponentHolder.getAppComponent().rxbus().toObserverable(Comment.class);
        compositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<Comment>() {
                    @Override
                    public void _onNext(Comment comment) {
                        //如果不包含该comment，则代表发表的是评论
                        if(data.contains(comment)){
                            setSoftInputStateWhenCommentOrReply(true,true,comment);
                        }
                    }

                    @Override
                    public void _onError(String s) {

                    }
                }));

    }

    /**
     * 根据是否是回复评论以及是否请求成功来改变软键盘的状态
     * @param isReply 是否回复
     * @param isShow 是否显示软键盘
     * @param comment 如果是回复评论则传入要回复的评论，否则传入null
     */
    public void setSoftInputStateWhenCommentOrReply(boolean isReply,boolean isShow,Comment comment){
        Timber.d("setSoftInputStateWhenCommentOrReply");
        showSoftInput = isShow;
        this.mReplyComment = comment;
        if(isReply){
            //当comment为null时，表示回复评论成功，重置为发表评论的状态
            if(comment!=null){
                mEditText_inputCommentHint = "回复"+comment.authorInfo.nickname;
            }else {
                mEditText_inputCommentHint = "发表评论";
            }
        }else {
            mEditText_inputCommentHint = "发表评论";
        }
        notifyPropertyChanged(BR.showSoftInput);
        notifyPropertyChanged(BR.editText_inputCommentHint);
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        super.onRequestErroInfo(erroinfo);
         stopLoading();
    }
}
