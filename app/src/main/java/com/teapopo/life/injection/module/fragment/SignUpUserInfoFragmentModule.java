package com.teapopo.life.injection.module.fragment;

import android.content.Context;

import com.teapopo.life.databinding.FragmentSignupUserinfoBinding;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.user.SignUpUserInfoModel;
import com.teapopo.life.viewModel.userCenter.SignUpUserInfoViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/5/21.
 */
@Module
public class SignUpUserInfoFragmentModule {
    private FragmentSignupUserinfoBinding mBinding;
    public SignUpUserInfoFragmentModule(FragmentSignupUserinfoBinding binding){
        this.mBinding = binding;
    }
    @Provides
    @PerActivity
    SignUpUserInfoViewModel provideSignUpUserInfoViewModel(Context context,SignUpUserInfoModel model){
        return new SignUpUserInfoViewModel(context,mBinding,model);
    }
    @Provides
    @PerActivity
    SignUpUserInfoModel provideSignUpUserInfoModel(Context context){
        return  new SignUpUserInfoModel(context);
    }
}
