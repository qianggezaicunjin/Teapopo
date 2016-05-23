package com.teapopo.life.viewModel.userCenter;

import android.content.Context;
import android.view.View;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentSigninBinding;
import com.teapopo.life.model.user.SignInModel;
import com.teapopo.life.util.sharesdk.LoginApi;
import com.teapopo.life.util.sharesdk.OnLoginListener;
import com.tencent.connect.UserInfo;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by louiszgm on 2016/5/23.
 */
public class SignInViewModel {
    private Context mContext;
    private FragmentSigninBinding mBinding;
    private SignInModel mModel;
    public SignInViewModel(Context context, SignInModel model, FragmentSigninBinding binding){
        mContext = context;
        mBinding = binding;
        mModel = model;
    }


    public View.OnClickListener getOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_login:
                        doLogin();
                        break;
                    case R.id.btn_login_qq:
                        doThirdLogin(QQ.NAME);
                        break;
                    case R.id.btn_login_weibo:
                        doThirdLogin(SinaWeibo.NAME);
                        break;
                }
            }
        };
    }
    //第三方登录
    private void doThirdLogin(String platform) {
        LoginApi login_api = new LoginApi();
        login_api.setPlatform(platform);
        //第三方登录
        login_api.setOnLoginListener(new OnLoginListener() {
            @Override
            public boolean onLogin(String platform, HashMap<String, Object> res) {
                return true;
            }

            @Override
            public boolean onRegister(UserInfo info) {
                return false;
            }
        });
        login_api.login(mContext);
    }

    private void doLogin() {
    }
}
