package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.teapopo.life.databinding.FragmentSignupVertifycodeBinding;
import com.teapopo.life.injection.component.fragment.SignInAndUpFragmentComponent;
import com.teapopo.life.injection.module.fragment.SignInAndUpFragmentModule;
import com.teapopo.life.view.activity.SignInAndUpActivity;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.userCenter.SignUpVertifyCodeViewModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/4/18 0018.
 * 用户注册
 */
public class SignUpVertifyCodeFragment extends SwipeBackBaseFragment {
    Toolbar mToolBar;
    private FragmentSignupVertifycodeBinding mBinding;

    private SignInAndUpFragmentComponent mComponent;

    @Inject
    SignUpVertifyCodeViewModel mViewModel;


    public static SignUpVertifyCodeFragment newInstance(){
        ;
        return new SignUpVertifyCodeFragment();
    }

    @Override
    public void onCreateBinding(Bundle savedInstanceState) {
        Timber.d("onCreateBinding");
        mBinding = FragmentSignupVertifycodeBinding.inflate(LayoutInflater.from(_mActivity));
        mComponent = ((SignInAndUpActivity)_mActivity).getSignInAndUpActivityComponent().signInAndUpFragmentComponent(new SignInAndUpFragmentModule(mBinding));
        mComponent.inject(this);
    }
    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("getContentView");
        mBinding.setSignUpViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
//        setUpToolBar(mToolBar).setToolBarViewModel(new ToolBarViewModel(_mActivity));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.d("onSaveInstanceState");
    }
}
