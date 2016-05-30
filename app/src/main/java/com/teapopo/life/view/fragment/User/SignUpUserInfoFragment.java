package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentSignupUserinfoBinding;
import com.teapopo.life.injection.component.fragment.SignInAndUpFragmentComponent;
import com.teapopo.life.injection.module.fragment.SignInAndUpFragmentModule;
import com.teapopo.life.view.activity.SignInAndUpActivity;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.userCenter.SignUpUserInfoViewModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/19.
 */
public class SignUpUserInfoFragment extends SwipeBackBaseFragment {

    private FragmentSignupUserinfoBinding binding;
    private SignInAndUpFragmentComponent mComponent;

    @Inject
    SignUpUserInfoViewModel mViewModel;

    /**
     * 在手机获取验证码页面传进来的手机号以及验证码
     * @param phonenum
     * @param vertifycode
     * @return
     */
    public static SignUpUserInfoFragment newInstance(String phonenum,String vertifycode){
        SignUpUserInfoFragment fragment = new SignUpUserInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phonenum",phonenum);
        bundle.putString("vertifycode",vertifycode);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding(Bundle savedInstanceState) {
        Timber.d("SignUpUserInfoFragment 的onCreateBinding");
        binding = FragmentSignupUserinfoBinding.inflate(LayoutInflater.from(_mActivity));
        mComponent = ((SignInAndUpActivity)_mActivity).getSignInAndUpActivityComponent().signInAndUpFragmentComponent(new SignInAndUpFragmentModule(binding));
        mComponent.inject(this);
        Bundle  bundle = getArguments();
        mViewModel.phonenum = bundle.getString("phonenum");
        mViewModel.vertifycode = bundle.getString("vertifycode");
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding.setViewmodel(mViewModel);
        return binding.getRoot();
    }

    @Override
    public void setUpView() {

    }
}
