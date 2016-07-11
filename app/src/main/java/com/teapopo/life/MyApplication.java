package com.teapopo.life;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;
import com.teapopo.life.injection.component.ApplicationComponent;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.DaggerApplicationComponent;
import com.teapopo.life.injection.module.ApplicationModule;
import com.teapopo.life.view.activity.MainActivity;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class MyApplication extends Application {
    ApplicationComponent mApplicationComponent;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
        mContext = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        ComponentHolder.setAppComponent(mApplicationComponent);
    }


    public static Context getInstance() {
        return mContext;
    }

    class MyUnCaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            // 当APP闪退时，所做的一些处理
            
            //重启app
            restartApp();
        }
    }

    private void restartApp() {
        Intent intent = new Intent(MyApplication.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.this.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
