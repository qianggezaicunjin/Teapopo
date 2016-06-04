package com.teapopo.life.viewModel;

import android.content.Context;

import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.CommentModel;

/**
 * Created by louiszgm on 2016/6/4.
 */
public class CommentItemViewModel extends BaseEntity {

    public Comment comment;
    public CommentItemViewModel(Context context, CommentModel model){

    }
}
