package com.teapopo.life.injection.module;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.viewModel.RecomendArticleViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm-pc on 2016/5/2.
 */
@Module
public class RecommendArticleFragmentModule {

    @Provides
    @PerActivity
    RecomendArticleViewModel provideRecommendArticleViewModel(){
        return new RecomendArticleViewModel();
    }
}
