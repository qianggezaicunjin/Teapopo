package com.teapopo.life.injection.component.fragment;

import com.teapopo.life.injection.module.fragment.PublishArticleFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.fragment.PublishArticle.PublishArticleFragment;

import dagger.Subcomponent;

/**
 * Created by louiszgm on 2016/6/12.
 */
@Subcomponent(modules = PublishArticleFragmentModule.class)
@PerActivity
public interface PublishArticleFragmentComponent {

    void inject(PublishArticleFragment fragment);
}
