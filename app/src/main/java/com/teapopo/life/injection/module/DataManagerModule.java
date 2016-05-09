package com.teapopo.life.injection.module;


import android.content.Context;

import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.data.remote.RetrofitHelper;
import com.teapopo.life.data.remote.cookie.PersistentCookieStore;
import com.teapopo.life.injection.scope.PerDataManager;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Provide dependencies to the DataManager, mainly Helper classes and Retrofit services.
 */
@Module
public class DataManagerModule {
   PersistentCookieStore mPersistentCookieStore;
    public DataManagerModule(PersistentCookieStore persistentCookieStore){
        this.mPersistentCookieStore = persistentCookieStore;
    }

    @Provides
    @PerDataManager
    NetWorkService provideNetWorkService(){
        return new RetrofitHelper(mPersistentCookieStore).netWorkService();
    }

    @Provides
    @PerDataManager
    Scheduler provideSubscribeScheduler(){
        return Schedulers.io();
    }
}
