package com.teapopo.life.viewModel.userCenter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentSigninBinding;
import com.teapopo.life.model.user.SignInModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.util.sharesdk.LoginApi;
import com.teapopo.life.util.sharesdk.OnLoginListener;
import com.teapopo.life.view.customView.RequestView;
import com.tencent.connect.UserInfo;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by louiszgm on 2016/5/23.
 */
public class SignInViewModel implements RequestView<ModelAction> {
    private Context mContext;
    private FragmentSigninBinding mBinding;
    private SignInModel mModel;
    public SignInViewModel(Context context, SignInModel model, ViewDataBinding binding){
        mContext = context;
        mBinding = (FragmentSigninBinding) binding;
        mModel = model;
        mModel.setView(this);
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
//                        doThirdLogin(SinaWeibo.NAME);
                        break;
                }
            }
        };
    }
    //第三方登录
    private void doThirdLogin(String platform) {
        LoginApi login_api = new LoginApi();
        login_api.setPlatform(platform);
        login_api.login(mContext);
    }

    //使用自家账号进行登录
    private void doLogin() {
        String account = mBinding.etUsername.getEditText().getText().toString();
        String passwd = mBinding.etPassword.getEditText().getText().toString();
        if(TextUtils.isEmpty(account)||TextUtils.isEmpty(passwd)){
            CustomToast.makeText(mContext,"账户名或者密码不能为空", Toast.LENGTH_SHORT).show();
        }else {
            mModel.login(false,account,passwd);
        }
    }

    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action == Action.SignInModel_Login){
            CustomToast.makeText(mContext,(String)data.t, Toast.LENGTH_SHORT).show();
            ((SupportActivity)mContext).finish();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        CustomToast.makeText(mContext,erroinfo, Toast.LENGTH_SHORT).show();
    }
}
