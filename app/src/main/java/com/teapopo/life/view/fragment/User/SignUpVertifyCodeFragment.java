package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.teapopo.life.databinding.FragmentSignupVertifycodeBinding;
import com.teapopo.life.model.user.SignUpModel;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.userCenter.SignUpVertifyCodeViewModel;

/**
 * Created by louiszgm on 2016/4/18 0018.
 * 用户注册
 */
public class SignUpVertifyCodeFragment extends SwipeBackBaseFragment {
    Toolbar mToolBar;
    private FragmentSignupVertifycodeBinding binding;

    public static SignUpVertifyCodeFragment newInstance(){
        return new SignUpVertifyCodeFragment();
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
        binding = FragmentSignupVertifycodeBinding.inflate(inflater);
        SignUpVertifyCodeViewModel viewModel = new SignUpVertifyCodeViewModel(_mActivity,binding,new SignUpModel(_mActivity));
        binding.setSignUpViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void setUpView() {

//        setUpToolBar(mToolBar).setToolBarViewModel(new ToolBarViewModel(_mActivity));
    }
}
