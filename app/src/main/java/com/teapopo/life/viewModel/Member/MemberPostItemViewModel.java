package com.teapopo.life.viewModel.Member;


import android.databinding.Bindable;

import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.article.memberArticle.MemberArticleModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseViewModel;


/**
 * Created by lhq on 2016/7/20.
 */

public class MemberPostItemViewModel extends BaseViewModel {
    private MemberArticleModel mModel;

    @Bindable
    public Article memberArticle;


    public MemberPostItemViewModel(MemberArticleModel model){
        mModel=model;
        mModel.setView(this);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action=data.action;
    }
}
