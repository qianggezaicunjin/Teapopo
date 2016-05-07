package com.teapopo.life;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.teapopo.life.injection.component.ApplicationComponent;
import com.teapopo.life.injection.component.DaggerApplicationComponent;
import com.teapopo.life.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class MyApplication extends Application {
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initialImageLoader();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        mApplicationComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initialImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
                .showImageForEmptyUri(R.drawable.dislike) //
                .showImageOnFail(R.drawable.dislike) //
                .showImageOnLoading(R.drawable.dislike)
                .cacheInMemory(true) //
                .cacheOnDisk(true) //
                .build();//
        ImageLoaderConfiguration config = new ImageLoaderConfiguration//
                .Builder(getApplicationContext())//
                .defaultDisplayImageOptions(defaultOptions)//
                .discCacheSize(50 * 1024 * 1024)//
                .discCacheFileCount(100)// 缓存一百张图片
                .writeDebugLogs()//
                .build();//
        ImageLoader.getInstance().init(config);
    }

    public static MyApplication get(Context context){
        return (MyApplication) context.getApplicationContext();
    }
    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
