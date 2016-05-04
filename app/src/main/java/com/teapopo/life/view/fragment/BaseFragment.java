package com.teapopo.life.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by louiszgm on 2016/4/19 0019.
 */
public abstract class BaseFragment extends Fragment{
   private View mContentView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        onCreateBinding(mContentView);
        setUpView();
        return mContentView;
    }
    public abstract void onCreateBinding(View contentView);
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
