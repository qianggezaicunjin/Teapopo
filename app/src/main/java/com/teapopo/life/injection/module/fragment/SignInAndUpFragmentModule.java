package com.teapopo.life.injection.module.fragment;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.user.SignInModel;
import com.teapopo.life.model.user.SignUpUserInfoModel;
import com.teapopo.life.model.user.SignUpVertifyCodeModel;
import com.teapopo.life.viewModel.userCenter.SignInViewModel;
import com.teapopo.life.viewModel.userCenter.SignUpUserInfoViewModel;
import com.teapopo.life.viewModel.userCenter.SignUpVertifyCodeViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/5/30.
 */
@Module
public class SignInAndUpFragmentModule {
    ViewDataBinding mBinding;
    public SignInAndUpFragmentModule(ViewDataBinding binding){
        this.mBinding = binding;
    }

    //for SignInFragment
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
    //for SignUpUserInfoFragment
    @Provides
    @PerActivity
    SignUpUserInfoViewModel provideSignUpUserInfoViewModel(Context context, SignUpUserInfoModel model){
        return new SignUpUserInfoViewModel(context,mBinding,model);
    }
    @Provides
    @PerActivity
    SignUpUserInfoModel provideSignUpUserInfoModel(Context context){
        return  new SignUpUserInfoModel(context);
    }
    //for SignUpVertifyCodeFragment
    @PerActivity
    @Provides
    SignUpVertifyCodeViewModel provideSignUpVertifyCodeViewModel(Context context, SignUpVertifyCodeModel model){
        return new SignUpVertifyCodeViewModel(context,mBinding,model);
    }
    @PerActivity
    @Provides
    SignUpVertifyCodeModel provideSignUpModel(Context context){
        return new SignUpVertifyCodeModel(context);
    }
}
