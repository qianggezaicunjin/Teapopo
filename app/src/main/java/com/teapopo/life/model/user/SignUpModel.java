package com.teapopo.life.model.user;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.data.ServerException;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.rx.RxResultHelper;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/19.
 */
public class SignUpModel extends BaseModel {

    public SignUpModel(Context context) {
        super(context);
    }

    public void getVertifyCode(String phonenum){
        //55804位短信模板的id
        Observable<JsonObject> observable = mDataManager.getSmsVertify(phonenum,"55804");
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter(new Func1<JsonObject, Boolean>() {
                    @Override
                    public Boolean call(JsonObject jsonObject) {
                        return null;
                    }
                });
    }
    //验证该手机号码是否存在
    public void vertifyPhone(String phonenum,String vertifycode){
        Observable<JsonObject> observable = mDataManager.vertifyPhone(phonenum,vertifycode);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.handleResult())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof ServerException){
                            mRequestView.onRequestErroInfo(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(Object object) {
                        Timber.d("onNext");
                    }
                });
    }
}
