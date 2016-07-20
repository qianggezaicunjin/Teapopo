package com.teapopo.life.injection.module.fragment;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;

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


    public PublishArticleFragmentModule(){

    }
    //for PublishArticleFragment
    @PerActivity
    @Provides
    PublishArticleViewModel providePublishArticleViewModel(PublishArticleModel model){
        return new PublishArticleViewModel(model);
    }
    @PerActivity
    @Provides
    PublishArticleModel providePublishArticleModel(Context context){
        return new PublishArticleModel(context);
    }
}
