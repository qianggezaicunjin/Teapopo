package com.teapopo.life.injection.module.fragment;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.article.categoryarticle.RecommendArticleModel;
import com.teapopo.life.model.article.categoryarticle.XinZiArticleModel;
import com.teapopo.life.model.article.likearticle.HomeLikeArticleModel;
import com.teapopo.life.model.articleinfo.ArticleInfoModel;
import com.teapopo.life.model.user.UserInfoModel;
import com.teapopo.life.viewModel.articleinfo.ArticleInfoViewModel;
import com.teapopo.life.viewModel.home.HomeLikeArticleViewModel;
import com.teapopo.life.viewModel.home.RecomendArticleViewModel;
import com.teapopo.life.viewModel.userCenter.UserViewModel;
import com.teapopo.life.viewModel.xinzi.XinZiArticleViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm-pc on 2016/5/30.
 */
@Module
public class MainFragmentModule {
    private ViewDataBinding mBinding;
    public MainFragmentModule(ViewDataBinding binding){
        mBinding = binding;
    }
    public MainFragmentModule(){

    }
    //for RecommendArticleFragment
    @Provides
    @PerActivity
    RecomendArticleViewModel provideRecommendArticleViewModel(Context context, RecommendArticleModel recommendArticleModel){
        return new RecomendArticleViewModel(context,recommendArticleModel,mBinding);
    }

    @Provides
    @PerActivity
    RecommendArticleModel provideRecommendArticleModel(Context context){
        return new RecommendArticleModel(context);
    }
    //for UserFragment
    @Provides
    @PerActivity
    UserViewModel provideUserViewModel(Context context, UserInfoModel userInfoModel){
        return new UserViewModel(context,userInfoModel);
    }
    @Provides
    @PerActivity
    UserInfoModel provideUserInfoModel(Context context){
        return new UserInfoModel(context);
    }

    //for HomeLikeArticleFragment
    @Provides
    @PerActivity
    HomeLikeArticleViewModel provideHomeLikeArticleViewModel(Context context, HomeLikeArticleModel model){
        return new HomeLikeArticleViewModel(context,model);
    }
    @Provides
    @PerActivity
    HomeLikeArticleModel provideHomeLikeArticleModel(Context context){
        return new HomeLikeArticleModel(context);
    }
    //for XinZiFragment
    @Provides
    @PerActivity
    XinZiArticleViewModel provideXinZiArticleViewModel(Context context, XinZiArticleModel model){
        return new XinZiArticleViewModel(context,model);
    }
    @Provides
    @PerActivity
    XinZiArticleModel provideXinZiArticleModel(Context context){
        return new XinZiArticleModel(context);
    }

}
