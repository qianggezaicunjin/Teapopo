package com.teapopo.life.viewModel.Member;

import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.memberLikes.MemberLike;
import com.teapopo.life.model.memberLikes.MemberLikesModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by lhq on 2016/7/26.
 */

public class LikeMemberViewModel extends BaseRecyclerViewModel {
    public MemberLikesModel memberLikesModel;

    @Bindable
    public List<BaseEntity> memberLikes=new ArrayList<>();

    public LikeMemberViewModel(MemberLikesModel memberLikesModel){
        this.memberLikesModel=memberLikesModel;
        memberLikesModel.setView(this);
    }

    //获取会员喜欢文章信息
    public void getLikeMember(String member_id){
        memberLikesModel.getMemberLikes(member_id);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action == Action.MemberLikesModel_GetMemberLikes){
            List<MemberLike> likeList= (List<MemberLike>) data.t;
            memberLikes.addAll(likeList);
            notifyPropertyChanged(BR.memberLikes);
        }
    }
}
