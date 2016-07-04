package com.teapopo.life;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;
import com.teapopo.life.injection.component.ApplicationComponent;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.DaggerApplicationComponent;
import com.teapopo.life.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class MyApplication extends Application {
    ApplicationComponent mApplicationComponent;
    private static  Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
        mContext = this;
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        mApplicationComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        ComponentHolder.setAppComponent(mApplicationComponent);
    }


    public static Context getInstance(){
        return mContext;
    }

}
