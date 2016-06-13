package com.teapopo.life.injection.module.fragment;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.article.publisharticle.PublishArticleModel;
import com.teapopo.life.viewModel.publisharticle.PublishArticleViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/6/12.
 */
@Module
public class PublishArticleFragmentModule {

    private ViewDataBinding mBinding;
    public PublishArticleFragmentModule(ViewDataBinding binding){
        mBinding = binding;
    }

    public PublishArticleFragmentModule(){

    }
    //for PublishArticleFragment
    @PerActivity
    @Provides
    PublishArticleViewModel providePublishArticleViewModel(Context context, PublishArticleModel model){
        return new PublishArticleViewModel(context,model,mBinding);
    }
    @PerActivity
    @Provides
    PublishArticleModel providePublishArticleModel(Context context){
        return new PublishArticleModel(context);
    }
}
