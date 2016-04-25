package com.teapopo.life.injection.component;


import com.teapopo.life.data.DataManager;
import com.teapopo.life.injection.module.DataManagerModule;
import com.teapopo.life.injection.scope.PerDataManager;

import dagger.Component;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
@PerDataManager
@Component(dependencies = ApplicationComponent.class,modules = DataManagerModule.class)
public interface DataManagerComponent {

    void inject(DataManager dataManager);
}
