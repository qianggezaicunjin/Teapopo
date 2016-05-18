package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.ToolbarBinding;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.ToolBarViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/4/18 0018.
 * 用户登陆
 */
public class SignInFragment extends SwipeBackBaseFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    public static SignInFragment newInstances(){
        return  new SignInFragment();
    }

    @Override
    public void onCreateBinding(Bundle savedInstanceState) {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setUpView() {
        mToolBar.inflateMenu(R.menu.menu_fast_signup);
        ToolBarViewModel toolBarViewModel = new ToolBarViewModel(_mActivity);
        setUpToolBar(mToolBar).setToolBarViewModel(toolBarViewModel);
    }

}
