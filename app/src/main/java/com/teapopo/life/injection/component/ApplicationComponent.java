package com.teapopo.life.injection.component;

/**
 * Created by Administrator on 2016/4/7 0007.
 */

import android.app.Application;

import com.teapopo.life.data.DataManager;
import com.teapopo.life.data.remote.cookie.PersistentCookieStore;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.injection.module.ApplicationModule;
import com.teapopo.life.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    Application application();
    DataManager dataManager();
    RxBus rxbus();

}
