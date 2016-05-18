package com.teapopo.life.view.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;


import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;
import timber.log.Timber;

/**
 * 具有滑动返回功能的activity
 * 默认是向右滑动返回
 * 可通过getSwipeBackLayout()设置滑动的方向以及是否开启滑动返回功能
 */
public abstract class SwipeBackBaseActivity extends SwipeBackActivity {

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
