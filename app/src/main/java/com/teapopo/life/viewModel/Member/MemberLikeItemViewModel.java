package com.teapopo.life.viewModel.Member;


import android.databinding.Bindable;

import com.teapopo.life.model.memberLikes.MemberLike;
import com.teapopo.life.model.memberLikes.MemberLikesModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseViewModel;


/**
 * Created by lhq on 2016/7/20.
 */

public class MemberLikeItemViewModel extends BaseViewModel {
    private MemberLikesModel mModel;

    @Bindable
    public MemberLike memberLike;


    public MemberLikeItemViewModel(MemberLikesModel model){
        mModel=model;
        mModel.setView(this);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action=data.action;
    }
}
