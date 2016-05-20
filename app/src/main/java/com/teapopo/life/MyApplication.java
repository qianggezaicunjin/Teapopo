package com.teapopo.life;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
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
        mContext = this;
        initialImageLoader();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        mApplicationComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        ComponentHolder.setAppComponent(mApplicationComponent);
    }

    private void initialImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.default_picture)
                .showImageOnFail(R.drawable.default_picture)
                .showImageOnLoading(R.drawable.default_picture)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .diskCacheSize(50 * 1024 * 1024)//
                .diskCacheFileCount(100)// 缓存一百张图片
//                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
    public static Context getInstance(){
        return mContext;
    }
//    public static MyApplication get(Context context){
//        return (MyApplication) context.getApplicationContext();
//    }
//    public ApplicationComponent getComponent() {
//        return mApplicationComponent;
//    }
//    // Needed to replace the component with a test specific one
//    public void setComponent(ApplicationComponent applicationComponent) {
//        mApplicationComponent = applicationComponent;
//    }
}
