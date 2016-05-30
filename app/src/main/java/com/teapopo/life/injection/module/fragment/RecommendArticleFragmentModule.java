package com.teapopo.life.injection.module.fragment;

import android.content.Context;

import com.teapopo.life.databinding.FragmentRecommendarticleBinding;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.article.categoryarticle.RecommendArticleModel;
import com.teapopo.life.viewModel.home.RecomendArticleViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm-pc on 2016/5/2.
 */
@Module
public class RecommendArticleFragmentModule {

    private FragmentRecommendarticleBinding mBinding;
    public RecommendArticleFragmentModule(FragmentRecommendarticleBinding binding){
        mBinding = binding;
    }
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
}
