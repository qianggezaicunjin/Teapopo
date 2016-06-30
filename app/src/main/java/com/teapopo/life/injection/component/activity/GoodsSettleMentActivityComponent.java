package com.teapopo.life.injection.component.activity;

import com.teapopo.life.injection.component.ApplicationComponent;
import com.teapopo.life.injection.component.fragment.ArticleDetailFragmentComponent;
import com.teapopo.life.injection.component.fragment.GoodsSettleMentFragmentComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.activity.ArticleDetailActivityModule;
import com.teapopo.life.injection.module.activity.GoodsSettleMentActivityModule;
import com.teapopo.life.injection.module.fragment.ArticleDetailFragmentModule;
import com.teapopo.life.injection.module.fragment.GoodsSettleMentFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.activity.ArticleDetailActivity;
import com.teapopo.life.view.activity.GoodsSettleMentActivity;

import dagger.Component;

/**
 * Created by louiszgm on 2016/6/30.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = { ActivityModule.class,GoodsSettleMentActivityModule.class})
public interface GoodsSettleMentActivityComponent {

    void inject(GoodsSettleMentActivity activity);

    GoodsSettleMentFragmentComponent goodsSettleMentFragmentComponent(GoodsSettleMentFragmentModule module);
}
