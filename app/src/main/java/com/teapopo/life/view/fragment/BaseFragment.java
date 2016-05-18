package com.teapopo.life.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by louiszgm on 2016/4/19 0019.
 */
public abstract class BaseFragment extends SupportFragment{
   private View mContentView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateBinding();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mContentView == null){
            mContentView= getContentView(inflater,container,savedInstanceState);
        }
        ViewGroup parent= (ViewGroup) container.getParent();
        if(parent!=null){
            parent.removeView(mContentView);
        }
        setUpView();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        return mContentView;
    }
    public abstract void onCreateBinding();
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置fragment的内容视图
     * @param inflater
     * @return
     */
    public abstract View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化需要用的
     */
    public abstract void setUpView();
}
