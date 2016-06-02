package com.teapopo.life.injection.module.fragment;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.articleinfo.ArticleInfoModel;
import com.teapopo.life.viewModel.articleinfo.ArticleInfoViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/6/2.
 */
@Module
public class ArticleDetailFragmentModule {
    private ViewDataBinding mBinding;
    public ArticleDetailFragmentModule(ViewDataBinding binding){
        mBinding = binding;
    }
    //for ArticleInfoFragment
    @Provides
    @PerActivity
    ArticleInfoViewModel provideArticleInfoViewModel(Context context, ArticleInfoModel model){
        return new ArticleInfoViewModel(context,model,mBinding);
    }
    @Provides
    @PerActivity
    ArticleInfoModel provideArticleInfoModel(Context context){
        return  new ArticleInfoModel(context);
    }
}
