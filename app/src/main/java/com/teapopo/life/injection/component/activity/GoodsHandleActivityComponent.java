package com.teapopo.life.injection.component.activity;

import com.teapopo.life.injection.component.ApplicationComponent;
import com.teapopo.life.injection.component.fragment.GoodsHandleFragmentComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.activity.GoodsHandleActivityModule;
import com.teapopo.life.injection.module.fragment.GoodsHandleFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.activity.GoodsHandleActivity;

import dagger.Component;

/**
 * Created by louiszgm on 2016/6/30.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = { ActivityModule.class,GoodsHandleActivityModule.class})
public interface GoodsHandleActivityComponent {

    void inject(GoodsHandleActivity activity);

    GoodsHandleFragmentComponent goodsSettleMentFragmentComponent(GoodsHandleFragmentModule module);
}
