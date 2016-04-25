package com.teapopo.life.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.teapopo.life.MyApplication;
import com.teapopo.life.model.event.LoginClickEvent;
import com.teapopo.life.util.sharesdk.LoginApi;
import com.teapopo.life.util.sharesdk.OnLoginListener;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.Tencent;

import java.util.HashMap;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/19 0019.
 */
public class UserViewModel extends BaseObservable{

    private Context mContext;

    private Tencent mTencent;
    public UserViewModel(Context context){
        this.mContext=context;
    }
    public View.OnClickListener onClickPhoneLogin(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("手机登陆按钮被点击");
                MyApplication.get(mContext).getComponent().rxbus().postEvent(new LoginClickEvent());
            }
        };
    }

    /**
     * 点击QQ登陆
     * @return
     */
    public View.OnClickListener onClickQQLogin(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginApi api = new LoginApi();
                //设置登陆的平台后执行登陆的方法
                api.setPlatform(QQ.NAME);
                api.setOnLoginListener(new OnLoginListener() {
                    public boolean onLogin(String platform, HashMap<String, Object> res) {
                        // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                        // 此处全部给回需要注册
                        Timber.d("QQ登录授权成功:%s", res.toString());
                        return true;
                    }

                    public boolean onRegister(UserInfo info) {
                        // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                        return true;
                    }
                });
                api.login(mContext);
            }
        };
    }


    /**
     * 点击微博登陆
     * @return
     */
    public View.OnClickListener onClickWeiBoLogin(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginApi api = new LoginApi();
                //设置登陆的平台后执行登陆的方法
                api.setPlatform(SinaWeibo.NAME);
                api.setOnLoginListener(new OnLoginListener() {
                    public boolean onLogin(String platform, HashMap<String, Object> res) {
                        // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                        // 此处全部给回需要注册
                        Timber.d("新浪微博登录授权成功:%s",res.toString());
                        return true;
                    }

                    public boolean onRegister(UserInfo info) {
                        // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                        return true;
                    }
                });
                api.login(mContext);
            }
        };
    }



}
