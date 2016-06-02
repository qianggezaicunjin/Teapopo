package com.teapopo.life.injection.component.activity;

import com.teapopo.life.injection.component.ActivityComponent;
import com.teapopo.life.injection.component.ApplicationComponent;
import com.teapopo.life.injection.component.fragment.MainFragmentComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.activity.MainActivityModule;
import com.teapopo.life.injection.module.fragment.MainFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.activity.MainActivity;

import dagger.Component;

/**
 * Created by louiszgm on 2016/4/27.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = { ActivityModule.class,MainActivityModule.class})
public interface MainActivityComponent extends ActivityComponent {

    void inject(MainActivity mainActivity);

    MainFragmentComponent mainFragmentComponent(MainFragmentModule module);

}
