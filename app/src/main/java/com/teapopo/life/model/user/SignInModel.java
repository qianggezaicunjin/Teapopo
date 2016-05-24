package com.teapopo.life.model.user;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.erroinfo.ErroInfo;
import com.teapopo.life.model.PostKeyValue;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/11.
 */
public class SignInModel extends BaseModel {
    public SignInModel(Context context) {
        super(context);
    }

    /**
     *
     * @param isLoginByVertifyCode 是否使用验证码登录
     * @param account 手机号/账户名/邮箱
     * @param passwd  验证码/密码
     */
    public void login(boolean isLoginByVertifyCode, final String account, String passwd){
        Observable<JsonObject> observable = mDataManager.login(isLoginByVertifyCode,account,passwd);
        observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject o) {
                        //TODO:对结果进行相关的逻辑处理
                        ModelAction<String> action = new ModelAction<String>();
                        action.action = Action.SignInModel_Login;
                        action.t = o.get("errmsg").getAsString();
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
