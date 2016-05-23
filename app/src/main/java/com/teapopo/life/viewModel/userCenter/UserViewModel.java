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
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.util.DialogFactory;
import com.teapopo.life.util.navigator.Navigator;
import com.teapopo.life.view.activity.SignInAndUpActivity;
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
    public String nickName = mUserInfo.nickname;
    @Bindable
    public String signature = mUserInfo.signature;
    @Bindable
    public String avatarUrl = mUserInfo.avatar;
    @Bindable
    public String postCount = mUserInfo.posts_num;
    @Bindable
    public String focusCount = mUserInfo.subscribe_num;
    @Bindable
    public String fansCount = mUserInfo.fans_num;
    @Inject
    public UserViewModel (Context context,UserInfoModel userInfoModel){
        this.mContext = context;
        this.mUserInfoModel = userInfoModel;
        this.mUserInfoModel.setView(this);
        requestData();
    }

    public void requestData(){
        mUserInfoModel.getUserInfo();
    }
    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(BaseEntity data) {
        UserInfo userInfo = ((UserInfo)data);
        mUserInfo = userInfo;
        this.nickName = mUserInfo.nickname;
        this.signature = mUserInfo.signature;
        this.avatarUrl = mUserInfo.avatar;
        this.focusCount = mUserInfo.subscribe_num;
        this.postCount = mUserInfo.posts_num;
        this.fansCount = mUserInfo.fans_num;
        notifyPropertyChanged(BR.nickName);
        notifyPropertyChanged(BR.signature);
        notifyPropertyChanged(BR.avatarUrl);
        notifyPropertyChanged(BR.focusCount);
        notifyPropertyChanged(BR.postCount);
        notifyPropertyChanged(BR.fansCount);
    }

    @Override
    public void onRequestErroInfo(String errinfo) {
        CustomToast.makeText(mContext,errinfo, Toast.LENGTH_SHORT).show();
        this.nickName = null;
        this.signature = null;
        this.avatarUrl = null;
        this.focusCount = null;
        this.postCount = null;
        this.fansCount = null;
        notifyPropertyChanged(BR.nickName);
        notifyPropertyChanged(BR.signature);
        notifyPropertyChanged(BR.avatarUrl);
        notifyPropertyChanged(BR.focusCount);
        notifyPropertyChanged(BR.postCount);
        notifyPropertyChanged(BR.fansCount);
    }
    //以下是对应view的点击事件的处理
    public View.OnClickListener getOnClickListener(){
         return  new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 switch (v.getId()){
                     case R.id.circleiv_userinfo_icon:
                         Timber.d("点击了用户头像");
                         Navigator.getInstance().start(mContext, SignInAndUpActivity.class);
                         break;
                     case R.id.btn_userinfo_exit:
                         Timber.d("点击了注销按钮");
                         doExit();
                         break;
                     case R.id.btn_userinfo_setting:
                         Timber.d("点击了设置按钮");
                         break;
                 }
             }
         };
    }
    //处理用户的注销
    private void doExit() {
        DialogFactory.createSureOrNotDialog(mContext, "确定退出吗?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case AlertDialog.BUTTON_POSITIVE:
                            //发送用户注销的事件
                            ComponentHolder.getAppComponent().rxbus().post(new LogOutEvent());
                            requestData();
                            break;
                    }
            }
        }).show();
    }
}
