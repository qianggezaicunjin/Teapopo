package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.teapopo.life.databinding.FragmentSignupVertifycodeBinding;
import com.teapopo.life.injection.component.fragment.SignUpVervifyCodeFragmentComponent;
import com.teapopo.life.injection.module.fragment.SignUpVertifyCodeFragmentModule;
import com.teapopo.life.model.user.SignUpModel;
import com.teapopo.life.view.activity.SignInAndUpActivity;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.userCenter.SignUpVertifyCodeViewModel;

import javax.inject.Inject;

/**
 * Created by louiszgm on 2016/4/18 0018.
 * 用户注册
 */
public class SignUpVertifyCodeFragment extends SwipeBackBaseFragment {
    Toolbar mToolBar;
    private FragmentSignupVertifycodeBinding mBinding;
    private SignUpVervifyCodeFragmentComponent mComponent;

    @Inject
    SignUpVertifyCodeViewModel mViewModel;
    public static SignUpVertifyCodeFragment newInstance(){
        return new SignUpVertifyCodeFragment();
    }

    @Override
    public void onCreateBinding(Bundle savedInstanceState) {
        mComponent = ((SignInAndUpActivity)_mActivity).getSignInAndUpActivityComponent().signUpVervifyCodeFragmentComponent(new SignUpVertifyCodeFragmentModule(mBinding));
        mComponent.inject(this);
    }
    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSignupVertifycodeBinding.inflate(inflater);

        mBinding.setSignUpViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {

//        setUpToolBar(mToolBar).setToolBarViewModel(new ToolBarViewModel(_mActivity));
    }
}
