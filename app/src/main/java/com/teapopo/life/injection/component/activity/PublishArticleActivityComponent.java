package com.teapopo.life.injection.component.activity;

import com.teapopo.life.injection.component.ActivityComponent;
import com.teapopo.life.injection.component.ApplicationComponent;
import com.teapopo.life.injection.component.fragment.PublishArticleFragmentComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.activity.PublishAriticleActivityModule;
import com.teapopo.life.injection.module.fragment.PublishArticleFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.activity.PublishArticleActivity;

import dagger.Component;

/**
 * Created by louiszgm on 2016/6/12.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = {ActivityModule.class,PublishAriticleActivityModule.class})
public interface PublishArticleActivityComponent extends ActivityComponent{

    void inject(PublishArticleActivity articleActivity);
    PublishArticleFragmentComponent  publishArticleFragmentComponent(PublishArticleFragmentModule module);
}
