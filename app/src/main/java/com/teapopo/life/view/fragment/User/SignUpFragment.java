package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.ToolBarViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/4/18 0018.
 * 用户注册
 */
public class SignUpFragment extends SwipeBackBaseFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    public static SignUpFragment newInstance(){
        return new SignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateBinding(Bundle savedInstanceState) {

    }
    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fast_signup,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setUpView() {
        setUpToolBar(mToolBar).setToolBarViewModel(new ToolBarViewModel(_mActivity));
    }
}
