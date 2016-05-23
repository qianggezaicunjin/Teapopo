package com.teapopo.life.injection.module.fragment;

import android.content.Context;

import com.teapopo.life.databinding.FragmentSigninBinding;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.user.SignInModel;
import com.teapopo.life.viewModel.userCenter.SignInViewModel;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/5/23.
 */
@Module
public class SignInFragmentModule {
    private FragmentSigninBinding mBinding;
    public SignInFragmentModule(FragmentSigninBinding binding){
        mBinding = binding;
    }
    @Provides
    @PerActivity
    SignInViewModel provideSignInViewModel(SignInModel model, Context context){
        return new SignInViewModel(context,model,mBinding);
    }
    @Provides
    @PerActivity
    SignInModel provideSignInModel(Context context){
        return new SignInModel(context);
    }
}
