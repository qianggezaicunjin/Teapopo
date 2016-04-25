package com.teapopo.life.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by louiszgm on 2016/4/19 0019.
 * app欢迎页面
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(MainActivity.getStartIntent(SplashActivity.this));
        SplashActivity.this.finish();
        overridePendingTransition(0, 0);
    }
}
