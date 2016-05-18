package com.teapopo.life.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.repacked.antlr.v4.Tool;
import com.teapopo.life.databinding.ToolbarBinding;

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by louiszgm on 2016/5/18.
 */
public abstract class SwipeBackBaseFragment extends SwipeBackFragment {

    private View mContentView;
    private Toolbar mToolBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateBinding(savedInstanceState);
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
//        setUpToolBar(mToolBar);
        setUpView();
        return attachToSwipeBack(mContentView);
    }
    public abstract void onCreateBinding(Bundle savedInstanceState);

    public ToolbarBinding setUpToolBar(Toolbar toolBar){
        if(toolBar!=null){
            ToolbarBinding binding = ToolbarBinding.bind(toolBar);
            return binding;
        }
        return null;
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
