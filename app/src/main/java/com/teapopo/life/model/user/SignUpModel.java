package com.teapopo.life.model.user;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.Constans.ViewModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/19.
 */
public class SignUpModel extends BaseModel {
    public static final int GetVertifyCodeAction = 0;
    public static final int VertifyPhoneAction = 1;

    public SignUpModel(Context context) {
        super(context);
    }

    public void getVertifyCode(String phonenum){
        //55804为短信模板的id
        Observable<JsonObject> observable = mDataManager.getSmsVertify(phonenum,"55804");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        Timber.d("请求服务器成功");
                        ViewModelAction<String> action = new ViewModelAction<String>();
                        action.action = GetVertifyCodeAction;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });

    }
    //验证该手机号码是否存在
    public void vertifyPhone(String phonenum,String vertifycode){
        Observable<JsonObject> observable = mDataManager.vertifyPhone(phonenum,vertifycode);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        ViewModelAction<String> action = new ViewModelAction<String>();
                        action.action = VertifyPhoneAction;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
