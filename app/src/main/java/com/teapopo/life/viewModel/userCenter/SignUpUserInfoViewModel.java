package com.teapopo.life.viewModel.userCenter;

import android.content.Context;
import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentSignupUserinfoBinding;
import com.teapopo.life.model.PostKeyValue;
import com.teapopo.life.model.sharedpreferences.RxSpf_ThirdLogin;
import com.teapopo.life.model.user.SignUpUserInfoModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.customView.RequestView;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import me.yokeyword.fragmentation.SupportActivity;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/21.
 */
public class SignUpUserInfoViewModel extends BaseObservable implements RequestView<ModelAction> {
    private FragmentSignupUserinfoBinding mBinding;
    private Context mContext;
    private SignUpUserInfoModel mSignUpUserInfoModel;

    public String phonenum;
    public String vertifycode;
    public SignUpUserInfoViewModel(Context context,FragmentSignupUserinfoBinding binding,SignUpUserInfoModel model){
        mBinding = binding;
        mContext = context;
        mSignUpUserInfoModel = model;
        mSignUpUserInfoModel.setView(this);
    }

    public View.OnClickListener getClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_signup_userinfo:
                        doRegist();
                        break;
                }
            }
        };
    }
    //用户注册
    private void doRegist() {
        String nickname = mBinding.etNickname.getEditText().getText().toString();
        String paswd = mBinding.etLoginpassword.getEditText().getText().toString();
        String sure_paswd = mBinding.etSurepassword.getEditText().getText().toString();
        if(TextUtils.isEmpty(nickname)||TextUtils.isEmpty(paswd)||TextUtils.isEmpty(sure_paswd)){
            CustomToast.makeText(mContext,"输入的内容不能为空", Toast.LENGTH_SHORT).show();
        }else {
            //构建Post请求需要的参数
            PostKeyValue param1 = new PostKeyValue("nickname",nickname);
            PostKeyValue param2 = new PostKeyValue("password",paswd);
            PostKeyValue param3 = new PostKeyValue("confirm",sure_paswd);
            PostKeyValue param4 = new PostKeyValue("phone",phonenum);
            PostKeyValue param5 = new PostKeyValue("verify",vertifycode);
            List<PostKeyValue> params = new ArrayList<>();
            params.add(param1);
            params.add(param2);
            params.add(param3);
            params.add(param4);
            params.add(param5);
            RxSpf_ThirdLogin rxSpf_thirdLogin = RxSpf_ThirdLogin.create(mContext);
            //如果是第三方,添加两个参数
            if(rxSpf_thirdLogin.platform().exists()){
                String platform = rxSpf_thirdLogin.platform().get();
                String openid = ShareSDK.getPlatform(platform).getDb().getUserId();
                PostKeyValue param6 = new PostKeyValue("classify",platform.toLowerCase());
                PostKeyValue param7 = new PostKeyValue("openid",openid);
                params.add(param6);
                params.add(param7);
            }
            mSignUpUserInfoModel.regist(params);
        }
    }

    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.SignUpUserInfoModel_Regist){
            ((SupportActivity)mContext).finish();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        CustomToast.makeText(mContext,erroinfo,Toast.LENGTH_SHORT).show();
    }
}
