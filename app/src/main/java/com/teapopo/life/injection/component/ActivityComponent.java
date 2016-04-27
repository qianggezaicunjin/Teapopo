package com.teapopo.life.injection.component;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.scope.PerActivity;

import dagger.Component;

/**
 * Created by louiszgm on 2016/4/27.
 */
@PerActivity
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {

    Activity getActivity();
}
