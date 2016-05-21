package com.teapopo.life.model.user;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.PostKeyValue;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by louiszgm on 2016/5/21.
 */
public class SignUpUserInfoModel extends BaseModel {

    public SignUpUserInfoModel(Context context) {
        super(context);
    }


    public void regist(List<PostKeyValue> params){
        Observable<JsonObject> observable = mDataManager.regist(params);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    public void _onNext(Object o) {

                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
