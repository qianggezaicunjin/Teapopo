package com.teapopo.life.viewModel;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.teapopo.life.R;

import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.CommentModel;
import com.teapopo.life.model.sharedpreferences.RxSpf_ReplyCommentSp;
import com.teapopo.life.model.sharedpreferences.RxSpf_ThirdLoginSp;
import com.teapopo.life.model.sharedpreferences.RxSpf_UserInfoSp;
import com.teapopo.life.model.user.UserInfo;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.customView.RequestView;

/**
 * Created by louiszgm on 2016/6/4.
 */
public class CommentItemViewModel extends BaseEntity implements RequestView {

    private  CommentModel mModel;
    private Context mContext;

    public Comment comment;

    public CommentItemViewModel(Context context, CommentModel model){
        mContext = context;
        mModel = model;
        mModel.setView(this);
    }


    public View.OnClickListener getClickListener(){
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.img_replycomment:
                        doReplyComment();
                        break;
                }
            }
        };
    }

    private void doReplyComment() {
        if(RxSpf_UserInfoSp.create(mContext).userInfo().exists()){
            String encodeString = RxSpf_UserInfoSp.create(mContext).userInfo().get();
            //将ReplyComment缓存到SP，
            RxSpf_ReplyCommentSp rxSpf_replyCommentSp = RxSpf_ReplyCommentSp.create(mContext);
            rxSpf_replyCommentSp.edit()
                    .commentId()
                    .put(comment.id)
                    .replyname()
                    .put(UserInfo.decodeUserInfo(encodeString).nickname)
                    .commit();
            //发送回复评论的事件通知
            ComponentHolder.getAppComponent().rxbus().post(comment);
        }else {
            CustomToast.makeText(mContext,"请先登录", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(Object data) {

    }

    @Override
    public void onRequestErroInfo(String erroinfo) {

    }
}
