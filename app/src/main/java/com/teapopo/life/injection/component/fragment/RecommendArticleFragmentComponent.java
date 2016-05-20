package com.teapopo.life.injection.component.fragment;

import com.teapopo.life.injection.module.fragment.RecommendArticleFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.fragment.Home.RecommendArticleFragment;

import dagger.Subcomponent;

/**
 * Created by louiszgm on 2016/4/28.
 */
@PerActivity
@Subcomponent(modules = RecommendArticleFragmentModule.class)
public interface RecommendArticleFragmentComponent {

    void inject(RecommendArticleFragment recommendArticleFragment);
}
