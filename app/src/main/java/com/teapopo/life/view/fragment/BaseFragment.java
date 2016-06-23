package com.teapopo.life.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.ToolbarBinding;
import com.teapopo.life.view.fragment.MsgList.MsgListFragment;
import com.teapopo.life.viewModel.ToolBarViewModel;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/4/19 0019.
 */
public abstract class BaseFragment extends SupportFragment{
   private View mContentView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");
        onCreateBinding();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView");
        //缓存fragment的内容
        if(mContentView == null){
            mContentView= getContentView(inflater,container,savedInstanceState);
        }
        ViewGroup parent= (ViewGroup) container.getParent();
        if(parent!=null){
            parent.removeView(mContentView);
        }
        setUpView();
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

    public void setUpToolBar(final Toolbar toolBar){
        toolBar.setNavigationIcon(R.drawable.icon_search);
        //打开搜索界面
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //打开新消息的界面
        toolBar.inflateMenu(R.menu.menu_newmsg);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                start(MsgListFragment.newInstance());
                return true;
            }
        });

    }

}
