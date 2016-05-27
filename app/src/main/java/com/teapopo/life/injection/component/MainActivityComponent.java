package com.teapopo.life.injection.component;

import com.teapopo.life.injection.component.fragment.RecommendArticleFragmentComponent;
import com.teapopo.life.injection.component.fragment.UserFragmentComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.MainActivityModule;
import com.teapopo.life.injection.module.fragment.RecommendArticleFragmentModule;
import com.teapopo.life.injection.module.fragment.UserFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.activity.MainActivity;

import dagger.Component;

/**
 * Created by louiszgm on 2016/4/27.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = { ActivityModule.class,MainActivityModule.class})
public interface MainActivityComponent extends ActivityComponent{

    void inject(MainActivity mainActivity);

    RecommendArticleFragmentComponent recommendArticleFragment(RecommendArticleFragmentModule recommendArticleFragmentModule);

    UserFragmentComponent userFragment(UserFragmentModule userFragmentModule);
}
