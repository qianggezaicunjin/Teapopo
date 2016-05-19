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
import com.teapopo.life.model.user.SignUpModel;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.util.SnackbarFactory;
import com.teapopo.life.view.customView.CountDownTimer;
import com.teapopo.life.view.customView.RequestView;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/19.
 */
public class SignUpViewModel extends BaseObservable implements RequestView<String> {
    private FragmentSignupVertifycodeBinding mBinding;
    private SignUpModel mSignUpModel;
    private Context mContext;
    @Bindable
    public String leftTime;
    public SignUpViewModel(Context context,FragmentSignupVertifycodeBinding binding, SignUpModel signUpModel){
        mContext = context;
        mBinding = binding;
        mSignUpModel = signUpModel;
        mSignUpModel.setView(this);
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
            mBinding.btnSignupNextstep.setMode(ActionProcessButton.Mode.ENDLESS);
            mBinding.btnSignupNextstep.setProgress(50);
            mSignUpModel.vertifyPhone(phonenum,vertifycode);
        }
    }

    //获取验证码
    private void doGetCode() {

        CountDownTimer c = new CountDownTimer(1000*60,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                leftTime = millisUntilFinished/1000+"秒后重新获取";
                notifyPropertyChanged(BR.leftTime);
            }

            @Override
            public void onFinish() {
                leftTime = "点击重新发送";
                notifyPropertyChanged(BR.leftTime);
            }
        };
        c.start();
    }

    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(String data) {
        Timber.d(data);
        mBinding.btnSignupNextstep.setProgress(100);
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        CustomToast.makeText(mContext,erroinfo,Toast.LENGTH_SHORT).show();
    }
}
