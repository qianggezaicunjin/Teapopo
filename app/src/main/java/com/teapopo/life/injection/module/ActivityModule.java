package com.teapopo.life.injection.module;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.teapopo.life.injection.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/4/27.
 */
@Module
public class ActivityModule {
    private final Activity activity;
    public ActivityModule(Activity activity){
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity(){
        return activity;
    }
}
