package com.teapopo.life.injection.component;


import android.content.Context;


import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;

import dagger.Component;

/**
 * Created by louiszgm on 2016/4/27.
 */
@PerActivity
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {

    Context getContext();

    void inject(RecommendArticleAdapter adapter);
}
