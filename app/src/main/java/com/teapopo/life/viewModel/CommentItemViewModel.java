package com.teapopo.life.viewModel;

import android.content.Context;
import android.view.View;

import com.teapopo.life.R;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.CommentModel;
import com.teapopo.life.util.DataUtils;
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
        ComponentHolder.getAppComponent().rxbus().post(comment);
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
