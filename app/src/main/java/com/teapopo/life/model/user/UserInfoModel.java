package com.teapopo.life.model.user;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
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
public class UserInfoModel extends BaseModel {
    
    public UserInfoModel(Context context) {
        super(context);
    }

    public void getUserInfo(){
        Observable<JsonObject> observable = mDataManager.getUserInfo();
        observable.subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Timber.d("获取个人信息:%s",jsonObject.toString());
                    }
                });
    }
}
