package com.teapopo.life.util;


import com.teapopo.life.injection.component.ApplicationComponent;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.DaggerApplicationComponent;
import com.teapopo.life.injection.module.ApplicationModule;

import org.robolectric.RuntimeEnvironment;

import static org.mockito.Mockito.spy;

/**
 * Created by xiaochuang on 5/15/16.
 */
public class TestUtils {
    public static final ApplicationModule appModule = spy(new ApplicationModule(RuntimeEnvironment.application));

    public static void setupDagger() {
        ApplicationComponent appComponent = DaggerApplicationComponent.builder().applicationModule(appModule).build();
        ComponentHolder.setAppComponent(appComponent);
    }

}
