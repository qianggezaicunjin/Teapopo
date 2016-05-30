package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentSigninBinding;
import com.teapopo.life.databinding.ToolbarBinding;
import com.teapopo.life.injection.component.fragment.SignInAndUpFragmentComponent;
import com.teapopo.life.injection.component.fragment.SignInFragmentComponent;
import com.teapopo.life.injection.module.fragment.SignInAndUpFragmentModule;
import com.teapopo.life.injection.module.fragment.SignInFragmentModule;
import com.teapopo.life.view.activity.SignInAndUpActivity;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.ToolBarViewModel;
import com.teapopo.life.viewModel.userCenter.SignInViewModel;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/4/18 0018.
 * 用户登陆
 */
public class SignInFragment extends SwipeBackBaseFragment {

    Toolbar mToolBar;
    @Inject
     SignInViewModel mViewModel;

    private SignInAndUpFragmentComponent mComponent;
    private FragmentSigninBinding mBinding;
    public static SignInFragment newInstances(){
        return  new SignInFragment();
    }

    @Override
    public void onCreateBinding(Bundle savedInstanceState) {
            mBinding = FragmentSigninBinding.inflate(LayoutInflater.from(_mActivity));
//        mComponent = ((SignInAndUpActivity)_mActivity).getSignInAndUpActivityComponent().signInFragmentComponent(new SignInFragmentModule(mBinding));
        mComponent = ((SignInAndUpActivity)_mActivity).getSignInAndUpActivityComponent().signInAndUpFragmentComponent(new SignInAndUpFragmentModule(mBinding));
        mComponent.inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        mToolBar = mBinding.toolbarSignin.toolbar;
        mToolBar.inflateMenu(R.menu.menu_fast_signup);
        ToolBarViewModel toolBarViewModel = new ToolBarViewModel(_mActivity);
        mBinding.toolbarSignin.setToolBarViewModel(toolBarViewModel);
//        setUpToolBar(mToolBar).setToolBarViewModel(toolBarViewModel);
    }

}
