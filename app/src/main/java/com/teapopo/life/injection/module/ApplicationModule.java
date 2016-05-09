package com.teapopo.life.injection.module;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.teapopo.life.data.DataManager;
import com.teapopo.life.data.remote.cookie.PersistentCookieStore;
import com.teapopo.life.data.rx.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class ApplicationModule {
    protected  final Application mApplication;

    public ApplicationModule(Application application){
        mApplication=application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(){
        return new DataManager(mApplication);
    }

    @Provides
    @Singleton
    RxBus provideRxBus(){
        return RxBus.getInstance();
    }

    @Provides
    @Singleton
    PersistentCookieStore provideCookie(){
        return new PersistentCookieStore(mApplication);
    }
}
