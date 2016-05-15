package com.teapopo.life.model.user;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.erroinfo.ErroInfo;
import com.teapopo.life.model.PostKeyValue;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/11.
 */
public class LoginModel extends BaseModel {
    public LoginModel(Context context) {
        super(context);
    }

    public void login(){
        List<PostKeyValue>  params = new ArrayList<>();
        params.add(new PostKeyValue("no_verify","1"));
        params.add(new PostKeyValue("use_sms","0"));
        params.add(new PostKeyValue("login_name","13798969669"));
        params.add(new PostKeyValue("password","42418909"));
        Observable<JsonObject> observable = mDataManager.login(params);
        observable.subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e.toString());
                    }

                    @Override
                    public void onNext(JsonObject erroInfo) {
                        Timber.d("登录返回的信息为:%s",erroInfo.toString());
                        SharedPreferences preferences = mContext.getSharedPreferences("LoginInfo",0);

                    }
                });
    }
}
