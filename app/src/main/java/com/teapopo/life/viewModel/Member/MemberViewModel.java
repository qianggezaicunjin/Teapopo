package com.teapopo.life.viewModel.Member;

import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.Member.MemberModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseViewModel;

/**
 * Created by lhq on 2016/7/26.
 */

public class MemberViewModel extends BaseViewModel {
    private MemberModel memberModel;

    @Bindable
    public AuthorInfo authorInfo;

    public MemberViewModel(MemberModel model) {
        memberModel=model;
        memberModel.setView(this);
    }

    //关注会员
    public void doFocus(String member_id){
        memberModel.focusMember(member_id);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action == Action.MemberModel_focusMember){
            authorInfo.isSubscribe = !authorInfo.isSubscribe;
            notifyPropertyChanged(BR.authorInfo);
        }
    }
}
