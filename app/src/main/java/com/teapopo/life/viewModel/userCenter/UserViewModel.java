package com.teapopo.life.viewModel.userCenter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import com.teapopo.life.BR;
import com.teapopo.life.R;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.event.LogOutEvent;
import com.teapopo.life.model.user.UserInfo;
import com.teapopo.life.model.user.UserInfoModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.util.DialogFactory;
import com.teapopo.life.util.navigator.Navigator;
import com.teapopo.life.view.activity.SignInAndUpActivity;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.viewModel.BaseViewModel;


import javax.inject.Inject;

import timber.log.Timber;


/**
 * Created by Administrator on 2016/4/19 0019.
 */
public class UserViewModel extends BaseViewModel {
    private UserInfoModel mUserInfoModel;
    @Bindable
    public UserInfo mUserInfo = new UserInfo();



    @Inject
    public UserViewModel (UserInfoModel userInfoModel){
        this.mUserInfoModel = userInfoModel;
        this.mUserInfoModel.setView(this);
        requestData();
    }

    public void requestData(){
        mUserInfoModel.getUserInfo();
    }

    public void logOut(){
        mUserInfoModel.logOut();
    }
    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.UserInfoModel_GetUserInfo){
            UserInfo userInfo = (UserInfo) data.t;
            mUserInfo = userInfo;
            notifyPropertyChanged(BR.userInfo);
        }else if(action == Action.UserInfoModel_LogOut){
            mUserInfo = null;
            notifyPropertyChanged(BR.userInfo);
        }
    }
}
