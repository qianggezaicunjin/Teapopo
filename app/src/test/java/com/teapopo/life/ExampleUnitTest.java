package com.teapopo.life;

import android.app.Application;
import android.content.Context;

import com.teapopo.life.data.DataManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboSharedPreferences;
import org.robolectric.shadows.ShadowLog;

import timber.log.Timber;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21)
public class ExampleUnitTest {

    private Application application;
    private DataManager dataManager;

    @Before
    public void setup(){
        //输出日志
        ShadowLog.stream = System.out;
        application = RuntimeEnvironment.application;
        dataManager = new DataManager(application);
    }
    @Test
    public void addition_isCorrect() throws Exception {
        RoboSharedPreferences preferences = (RoboSharedPreferences) application
                .getSharedPreferences("Cookies_Prefs", Context.MODE_PRIVATE);
        dataManager.getCategorys();
        Timber.d("preferences为:%S",preferences.getAll().toString());
    }
}