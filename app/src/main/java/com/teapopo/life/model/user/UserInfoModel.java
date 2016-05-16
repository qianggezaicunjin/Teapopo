package com.teapopo.life.model.user;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.erroinfo.ErroInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/11.
 */
public class UserInfoModel extends BaseModel {
    
    public UserInfoModel(Context context) {
        super(context);
    }

    public void getUserInfo() {
        Observable<JsonObject> observable = mDataManager.getUserInfo();
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .filter(new Func1<JsonObject, Boolean>() {
                    @Override
                    public Boolean call(JsonObject jsonObject) {
                        if(jsonObject.has("errcode")){
                            Timber.d("有errcode");
                            mRequestView.onRequestErroInfo(jsonObject.get("errmsg").getAsString());
                            return false;
                        }
                        return true;
                    }
                })
                .subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {
                        mRequestView.onRequestFinished();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.toString());
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Timber.d("获取到的个人信息为:%s", jsonObject.toString());
                        try {
                            UserInfo userInfo = LoganSquare.parse(jsonObject.toString(),UserInfo.class);
                            mRequestView.onRequestSuccess(userInfo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
