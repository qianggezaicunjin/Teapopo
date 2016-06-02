package com.teapopo.life.injection.component.fragment;

import com.teapopo.life.injection.module.fragment.ArticleDetailFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.fragment.ArticleInfoFragment;

import dagger.Subcomponent;

/**
 * Created by louiszgm on 2016/6/2.
 */
@PerActivity
@Subcomponent(modules = ArticleDetailFragmentModule.class)
public interface ArticleDetailFragmentComponent {
    void inject(ArticleInfoFragment fragment);
}
