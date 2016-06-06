package com.teapopo.life.viewModel.userCenter;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.teapopo.life.BR;
import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentSignupVertifycodeBinding;
import com.teapopo.life.model.sharedpreferences.RxSpf_ThirdLoginSp;
import com.teapopo.life.model.user.SignUpVertifyCodeModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.customView.CountDownTimer;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.User.SignInFragment;
import com.teapopo.life.view.fragment.User.SignUpUserInfoFragment;

import cn.sharesdk.framework.ShareSDK;
import me.yokeyword.fragmentation.SupportActivity;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/19.
 */
public class SignUpVertifyCodeViewModel extends BaseObservable implements RequestView<ModelAction> {

    private FragmentSignupVertifycodeBinding mBinding;
    private SignUpVertifyCodeModel mSignUpVertifyCodeModel;
    private Context mContext;
    @Bindable
    public String leftTime;
    private CountDownTimer mCountDownTimer;
    private RxSpf_ThirdLoginSp mSpf_thirdLogin;
    private String mPhonenum;
    private String mVertifycode;


    public SignUpVertifyCodeViewModel(Context context, ViewDataBinding binding, SignUpVertifyCodeModel signUpVertifyCodeModel){
        mContext = context;
        mBinding = (FragmentSignupVertifycodeBinding) binding;
        mSignUpVertifyCodeModel = signUpVertifyCodeModel;
        mSignUpVertifyCodeModel.setView(this);
    }

    public View.OnClickListener getOClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.tv_get_vertifycode:
                        doGetCode();
                        break;
                    case R.id.btn_signup_nextstep:
                        doVertify();
                        break;
                }
            }
        };
    }

    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Timber.d("onRequestSuccess");
        Action action = data.action;
        if(action == Action.SignUpVertifyCodeModel_GetVertifyCode){
            doGetCodeSuccess();
            return;
        }
        else if(action == Action.SignUpVertifyCodeModel_VertifyPhone){
            if((Boolean) data.t){
                //如果手机号已经被注册过,则直接登录或者是绑定第三方账号
                mSpf_thirdLogin = RxSpf_ThirdLoginSp.create(mContext);
                if(mSpf_thirdLogin.platform().exists()){
                    //绑定第三方账号
                    doThirdBind();
                    return;
                }else {
                    //手机验证码登录
                    loginByVertifyCode();
                    return;
                }
            }else {
                //手机号码没有被注册过
                doNextRegist();
                return;
            }
        }
        //绑定第三方账号成功
        else if(action == Action.SignUpVertifyCodeModel_BindAccount){
            doThirdBindSuccess();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        CustomToast.makeText(mContext,erroinfo,Toast.LENGTH_SHORT).show();
        mBinding.btnSignupNextstep.setProgress(0);
    }
    //验证该手机是否被注册
    private void doVertify() {
        mPhonenum = mBinding.etPhonenum.getEditText().getText().toString();
        mVertifycode = mBinding.etVerificationcode.getEditText().getText().toString();
        if(TextUtils.isEmpty(mPhonenum)||TextUtils.isEmpty(mVertifycode)){
            CustomToast.makeText(mContext,"所填信息不能为空", Toast.LENGTH_SHORT).show();
        }else {
            //设置按钮状态为loading
            mBinding.btnSignupNextstep.setMode(ActionProcessButton.Mode.ENDLESS);
            mBinding.btnSignupNextstep.setProgress(50);
            mSignUpVertifyCodeModel.vertifyPhone(mPhonenum, mVertifycode);
        }
    }
    //如果该手机号没有被注册，进入下一个注册流程
    private void doNextRegist(){
        mBinding.btnSignupNextstep.setProgress(100);

        ((SupportActivity)mContext).popTo(SignInFragment.class, false, new Runnable() {
            @Override
            public void run() {
                final String phonenum = mBinding.etPhonenum.getEditText().getText().toString();
                final String vertifycode = mBinding.etVerificationcode.getEditText().getText().toString();
                ((SupportActivity)mContext).popTo(SignInFragment.class, false, new Runnable() {
                    @Override
                    public void run() {
                        ((SupportActivity)mContext).start(SignUpUserInfoFragment.newInstance(phonenum,vertifycode));
                    }
                });
            }
        });
    }
    //获取验证码
    private void doGetCode() {
        String phonenum = mBinding.etPhonenum.getEditText().getText().toString();
        if (TextUtils.isEmpty(phonenum)){
                CustomToast.makeText(mContext,"手机号不能为空!",Toast.LENGTH_SHORT).show();
        }else {
            mSignUpVertifyCodeModel.getVertifyCode(phonenum);
        }

    }
    //获取验证码成功，更新视图
    private void doGetCodeSuccess(){
        //重新获取验证码的倒计时
        mCountDownTimer = new CountDownTimer(1000*60,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                leftTime = millisUntilFinished/1000+"后重新获取验证码";
                Timber.d(leftTime);
                notifyPropertyChanged(BR.leftTime);
            }

            @Override
            public void onFinish() {
                leftTime = "重新获取验证码";
                notifyPropertyChanged(BR.leftTime);
            }
        }.start();
    }
    private void doThirdBindSuccess() {
        ((SupportActivity)mContext).finish();
    }


    private void doThirdBind() {
        String platform = mSpf_thirdLogin.platform().get();
        mSignUpVertifyCodeModel.bindAccount(mPhonenum,platform);
    }

    private void loginByVertifyCode() {
        mSignUpVertifyCodeModel.login(true,mPhonenum,mVertifycode);
    }
}
