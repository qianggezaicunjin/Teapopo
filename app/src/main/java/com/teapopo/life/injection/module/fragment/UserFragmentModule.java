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
    private Context mContext;

    public UserFragmentModule(Context context){
        this.mContext = context;
    }

    @Provides
    @PerActivity
    UserViewModel provideUserViewModel(){
        return new UserViewModel(mContext,new UserInfoModel(mContext));
    }
}
