package com.teapopo.life.viewModel;


import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.MyApplication;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.erroinfo.ErroInfo;
import com.teapopo.life.model.event.AddHeaderEvent;
import com.teapopo.life.model.event.DataEvent;
import com.teapopo.life.model.user.UserInfo;
import com.teapopo.life.model.user.UserInfoModel;
import com.teapopo.life.view.customView.RequestView;


import javax.inject.Inject;

import timber.log.Timber;


/**
 * Created by Administrator on 2016/4/19 0019.
 */
public class UserViewModel extends BaseObservable implements RequestView<BaseEntity> {
    private UserInfoModel mUserInfoModel;
    private UserInfo mUserInfo = new UserInfo();
    private Context mContext;

    @Inject
    public UserViewModel (Context context,UserInfoModel userInfoModel){
        this.mContext = context;
        this.mUserInfoModel = userInfoModel;
        this.mUserInfoModel.setView(this);
        mUserInfoModel.getUserInfo();
    }
    @Bindable
    public String getNickName(){
        Timber.d("getNickName");
        return  this.mUserInfo.nickname;
    }

    @Bindable
    public String getSignature(){
        return this.mUserInfo.signature;
    }
    @Bindable
    public String getAvatarUrl(){
        return this.mUserInfo.avatar;
    }
    @Bindable
    public String getTieZiCount(){
        return this.mUserInfo.posts_num;
    }
    @Bindable
    public String getFocusCount(){
        return this.mUserInfo.subscribe_num;
    }
    @Bindable
    public String getFansCount(){
        return this.mUserInfo.fans_num;
    }

    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(BaseEntity data) {
        this.mUserInfo = ((UserInfo)data);
        Timber.d("nickname为%s",this.mUserInfo.nickname);
        Timber.d("signature为%s",this.mUserInfo.signature);
        notifyPropertyChanged(BR.signature);
        notifyPropertyChanged(BR.avatarUrl);
    }

    @Override
    public void onRequestErroInfo(String errinfo) {

    }
}
