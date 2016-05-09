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
   Context mContext;
    public DataManagerModule(Context context){
        this.mContext = context;
    }

    @Provides
    @PerDataManager
    NetWorkService provideNetWorkService(){
        return new RetrofitHelper(mContext).netWorkService();
    }

    @Provides
    @PerDataManager
    Scheduler provideSubscribeScheduler(){
        return Schedulers.io();
    }
}
