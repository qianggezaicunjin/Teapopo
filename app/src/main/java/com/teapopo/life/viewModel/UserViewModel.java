package com.teapopo.life.viewModel;


import android.databinding.BaseObservable;

import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.user.UserInfo;
import com.teapopo.life.model.user.UserInfoModel;
import com.teapopo.life.view.customView.RequestView;

import java.util.List;


/**
 * Created by Administrator on 2016/4/19 0019.
 */
public class UserViewModel extends BaseObservable  {
    private UserInfoModel mUserInfoModel;
    private UserInfo mUserInfo = new UserInfo();
    public UserViewModel (UserInfoModel userInfoModel){
        this.mUserInfoModel = userInfoModel;
//        mUserInfoModel.setView(this);
    }

    public String getNickName(){
        return mUserInfo.nickname.get();
    }

    public String getSignature(){
        return mUserInfo.signature.get();
    }

    public String getAvatarUrl(){
        return mUserInfo.avatar.get();
    }

    public String getTieZiCount(){
        return mUserInfo.posts_num.get();
    }

    public String getFocusCount(){
        return mUserInfo.subscribe_num.get();
    }

    public String getFansCount(){
        return mUserInfo.fans_num.get();
    }

}
