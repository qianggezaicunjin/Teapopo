package com.teapopo.life.viewModel.Member;


import com.teapopo.life.BR;
import com.teapopo.life.model.article.memberArticle.MemberArticle;
import com.teapopo.life.model.article.memberArticle.MemberArticleModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.List;

import timber.log.Timber;

/**
 * Created by lhq on 2016/7/20.
 */

public class PostMemberViewModel extends BaseRecyclerViewModel {
    private MemberArticleModel mModel;

    public PostMemberViewModel(MemberArticleModel model){
        mModel=model;
        mModel.setView(this);
    }

    public void getMemberArticle(){
        mModel.getArticle("45987");
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action=data.action;
        if(action == Action.MemberArticleModel_GetArticle){
            Timber.d("MemberArticleModel_GetArticle");
            List<MemberArticle> list=(List<MemberArticle>)data.t;
            super.data.addAll(list);
            notifyPropertyChanged(BR.data);
        }
    }
}
