package com.teapopo.life.view.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.teapopo.life.MyApplication;
import com.teapopo.life.injection.component.ApplicationComponent;
import com.teapopo.life.view.customView.swipeback.SwipeBackActivity;
import com.teapopo.life.view.customView.swipeback.SwipeBackLayout;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public abstract class SwipeBackBaseActivity extends SwipeBackActivity {
    public ApplicationComponent getAppComponent(){
        return MyApplication.get(this).getComponent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置actionBar不显示原有的title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        onCreateBinding();
    }

    /**
     * 初始化数据绑定以及依赖注入
     */
    public abstract void onCreateBinding();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                FragmentManager fm=getFragmentManager();
                if(fm.getBackStackEntryCount()>0){
                    fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("Activity Destroy");
    }
}
