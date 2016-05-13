package com.teapopo.life.model.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.ErroInfo;
import com.teapopo.life.model.PostKeyValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
        Observable<ErroInfo> observable = mDataManager.login(params);
        observable.subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ErroInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e.toString());
                    }

                    @Override
                    public void onNext(ErroInfo erroInfo) {
                        Timber.d("登录返回的信息为:%s",erroInfo.errmsg);
                        SharedPreferences preferences = mContext.getSharedPreferences("ErroInfo",0);

                    }
                });
    }
}
