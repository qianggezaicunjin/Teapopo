package com.teapopo.life.model.user;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.erroinfo.ErroInfo;
import com.teapopo.life.model.PostKeyValue;
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

    public void login(){
        List<PostKeyValue>  params = new ArrayList<>();
        params.add(new PostKeyValue("no_verify","1"));
        params.add(new PostKeyValue("use_sms","0"));
        params.add(new PostKeyValue("login_name","13798969669"));
        params.add(new PostKeyValue("password","42418909"));
        Observable<JsonObject> observable = mDataManager.login(params);
        observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        //TODO:对结果进行相关的逻辑处理
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
