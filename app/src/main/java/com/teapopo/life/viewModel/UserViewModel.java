package com.teapopo.life.viewModel;


import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.MyApplication;
import com.teapopo.life.data.remote.NetWorkService;
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

    //以下是个人信息的数据项
    @Bindable
    public String nickName;
    @Bindable
    public String signature;
    @Bindable
    public String avatarUrl;
    @Bindable
    public String postCount;
    @Bindable
    public String focusCount;
    @Bindable
    public String fansCount;
    @Inject
    public UserViewModel (Context context,UserInfoModel userInfoModel){
        this.mContext = context;
        this.mUserInfoModel = userInfoModel;
        this.mUserInfoModel.setView(this);
        mUserInfoModel.getUserInfo();
    }
    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(BaseEntity data) {
        UserInfo userInfo = ((UserInfo)data);
        this.nickName = userInfo.nickname;
        this.signature = userInfo.signature;
        this.avatarUrl = NetWorkService.IMAGE_ENDPOINT+userInfo.avatar+NetWorkService.IMAGE_EXT;
        this.focusCount = userInfo.subscribe_num;
        this.postCount = userInfo.posts_num;
        this.fansCount = userInfo.fans_num;
        notifyPropertyChanged(BR.nickName);
        notifyPropertyChanged(BR.signature);
        notifyPropertyChanged(BR.avatarUrl);
        notifyPropertyChanged(BR.focusCount);
        notifyPropertyChanged(BR.postCount);
        notifyPropertyChanged(BR.fansCount);
    }

    @Override
    public void onRequestErroInfo(String errinfo) {

    }
}
