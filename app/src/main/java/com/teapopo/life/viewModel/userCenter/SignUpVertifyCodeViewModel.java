package com.teapopo.life.viewModel.userCenter;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.teapopo.life.BR;
import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentSignupVertifycodeBinding;
import com.teapopo.life.model.user.SignUpVertifyCodeModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.customView.CountDownTimer;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.User.SignInFragment;
import com.teapopo.life.view.fragment.User.SignUpUserInfoFragment;

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

    public SignUpVertifyCodeViewModel(Context context, FragmentSignupVertifycodeBinding binding, SignUpVertifyCodeModel signUpVertifyCodeModel){
        mContext = context;
        mBinding = binding;
        mSignUpVertifyCodeModel = signUpVertifyCodeModel;
        mSignUpVertifyCodeModel.setView(this);
    }

    public View.OnClickListener getClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.tv_get_vertifycode:
                        Timber.d("获取验证码");
                        doGetCode();
                        break;
                    case R.id.btn_signup_nextstep:
                        doVertify();
//                        doVertifySuccess();
                        break;
                }
            }
        };
    }
    //验证该手机是否被注册
    private void doVertify() {
        String phonenum = mBinding.etPhonenum.getEditText().getText().toString();
        String vertifycode = mBinding.etVerificationcode.getEditText().getText().toString();
        if(TextUtils.isEmpty(phonenum)||TextUtils.isEmpty(vertifycode)){
            CustomToast.makeText(mContext,"所填信息不能为空", Toast.LENGTH_SHORT).show();
        }else {
            //设置按钮状态为loading
            mBinding.btnSignupNextstep.setMode(ActionProcessButton.Mode.ENDLESS);
            mBinding.btnSignupNextstep.setProgress(50);
            mSignUpVertifyCodeModel.vertifyPhone(phonenum,vertifycode);
        }
    }
    private void doVertifySuccess(){
        mBinding.btnSignupNextstep.setProgress(100);

        ((SupportActivity)mContext).popTo(SignInFragment.class, false, new Runnable() {
            @Override
            public void run() {
                String phonenum = mBinding.etPhonenum.getEditText().getText().toString();
                String vertifycode = mBinding.etVerificationcode.getEditText().getText().toString();
                ((SupportActivity)mContext).start(SignUpUserInfoFragment.newInstance(phonenum,vertifycode));
            }
        });

//        if(mCountDownTimer.isRunning){
//            mCountDownTimer.cancel();
//        }
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


    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Timber.d("onRequestSuccess");
        Action action = data.action;
        if(action == Action.SignUpVertifyCodeModel_GetVertifyCode){
                doGetCodeSuccess();
            }
        else if(action == Action.SignUpVertifyCodeModel_VertifyPhone){
            if((Boolean) data.t){
                CustomToast.makeText(mContext,"该手机号已经被注册过了",Toast.LENGTH_SHORT).show();
            }else {
                //手机号码没有被注册过
                doVertifySuccess();
            }
        }
        }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        CustomToast.makeText(mContext,erroinfo,Toast.LENGTH_SHORT).show();
        mBinding.btnSignupNextstep.setProgress(0);
    }
}
