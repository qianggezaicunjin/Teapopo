package com.teapopo.life.injection.module.fragment;

import android.content.Context;

import com.teapopo.life.databinding.FragmentSignupVertifycodeBinding;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.user.SignUpModel;
import com.teapopo.life.viewModel.userCenter.SignUpVertifyCodeViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/5/20.
 */
@Module
public class SignUpVertifyCodeFragmentModule {
    private FragmentSignupVertifycodeBinding mBinding;

    public SignUpVertifyCodeFragmentModule(FragmentSignupVertifycodeBinding binding){
        mBinding = binding;
    }
    @PerActivity
    @Provides
    SignUpVertifyCodeViewModel provideSignUpVertifyCodeViewModel(Context context, SignUpModel model){
        return new SignUpVertifyCodeViewModel(context,mBinding,model);
    }
    @PerActivity
    @Provides
    SignUpModel provideSignUpModel(Context context){
        return new SignUpModel(context);
    }
}
