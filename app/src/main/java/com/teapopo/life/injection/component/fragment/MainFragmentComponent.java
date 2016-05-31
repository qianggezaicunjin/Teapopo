package com.teapopo.life.injection.component.fragment;

import com.teapopo.life.injection.module.fragment.MainFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.fragment.Home.HomeLikeArticleFragment;
import com.teapopo.life.view.fragment.Home.RecommendArticleFragment;
import com.teapopo.life.view.fragment.User.UserFragment;
import com.teapopo.life.view.fragment.xinzi.XinZiFragment;

import dagger.Subcomponent;

/**
 * Created by louiszgm-pc on 2016/5/30.
 */
@PerActivity
@Subcomponent(modules = MainFragmentModule.class)
public interface MainFragmentComponent {

    void inject(RecommendArticleFragment fragment);

    void inject(UserFragment fragment);

    void inject(HomeLikeArticleFragment fragment);

    void inject(XinZiFragment fragment);
}
