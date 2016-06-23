package com.teapopo.life.viewModel;

import android.content.Context;
import android.databinding.Bindable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teapopo.life.BR;
import com.teapopo.life.R;

import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.CommentModel;
import com.teapopo.life.model.comment.Reply;
import com.teapopo.life.model.sharedpreferences.RxSpf_ReplyCommentSp;
import com.teapopo.life.model.sharedpreferences.RxSpf_ThirdLoginSp;
import com.teapopo.life.model.sharedpreferences.RxSpf_UserInfoSp;
import com.teapopo.life.model.user.UserInfo;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.customView.RequestView;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/4.
 */
public class CommentItemViewModel extends BaseViewModel {

    private  CommentModel mModel;

    @Bindable
    public Comment comment;

    public void setComment(Comment comment){
        this.comment = comment;
    }
    public CommentItemViewModel(CommentModel model){
        mModel = model;
        mModel.setView(this);
    }


    public void doLikeComment(){
        mModel.clickLikeCommentOrNot("posts",comment.id);
    }

    public void doReplyComment(boolean isLogined) {
        if(isLogined){
            //发送回复评论的事件通知
            ComponentHolder.getAppComponent().rxbus().post(comment);
        }else {
            handleNoticeInfo("请先登录");
        }

    }


    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.CommentModel_ClickLikeComment){
            comment.is_like = !comment.is_like;
            int like_num = Integer.parseInt(comment.like_num)+1;
            comment.like_num = String.valueOf(like_num);
            notifyPropertyChanged(BR.comment);
        }
    }


}
