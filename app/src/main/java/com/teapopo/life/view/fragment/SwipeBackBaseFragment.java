package com.teapopo.life.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.teapopo.life.R;
import com.teapopo.life.databinding.ToolbarBinding;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.fragment.MsgList.MsgListFragment;

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
        onCreateBinding();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView= getContentView(inflater,container,savedInstanceState);
        setUpView();
        return attachToSwipeBack(mContentView);
    }
    public abstract void onCreateBinding();

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


    public void setUpToolBar(Toolbar toolBar){
        toolBar.setNavigationIcon(R.drawable.icon_search);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        toolBar.inflateMenu(R.menu.menu_newmsg);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                start(MsgListFragment.newInstance());
                return true;
            }
        });

    }

    public void toastErroMsg(String msg){
        CustomToast.makeText(_mActivity,msg, Toast.LENGTH_SHORT).show();
    }
}
