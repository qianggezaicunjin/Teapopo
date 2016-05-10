package com.teapopo.life.view.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.teapopo.life.MyApplication;
import com.teapopo.life.injection.component.ApplicationComponent;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public ApplicationComponent getAppComponent(){
        return MyApplication.get(this).getComponent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature()
        onCreateBinding();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
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
}
