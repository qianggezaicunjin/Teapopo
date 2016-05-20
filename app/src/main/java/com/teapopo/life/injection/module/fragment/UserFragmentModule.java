package com.teapopo.life.injection.module.fragment;

import android.content.Context;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.user.UserInfoModel;
import com.teapopo.life.viewModel.userCenter.UserViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/5/16.
 */
@Module
public class UserFragmentModule {

    @Provides
    @PerActivity
    UserViewModel provideUserViewModel(Context context,UserInfoModel userInfoModel){
        return new UserViewModel(context,userInfoModel);
    }
    @Provides
    @PerActivity
    UserInfoModel provideUserInfoModel(Context context){
        return new UserInfoModel(context);
    }
}
