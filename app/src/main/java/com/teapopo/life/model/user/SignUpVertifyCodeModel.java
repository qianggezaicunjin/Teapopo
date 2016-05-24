package com.teapopo.life.model.user;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/19.
 */
public class SignUpVertifyCodeModel extends BaseModel {

    public SignUpVertifyCodeModel(Context context) {
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
                        ModelAction<Action> action = new ModelAction<Action>();
                        action.action = Action.SignUpVertifyCodeModel_GetVertifyCode;
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

                        ModelAction<Boolean> action = new ModelAction<Boolean>();
                        action.action = Action.SignUpVertifyCodeModel_VertifyPhone;
                        action.t = ((JsonObject)o).get("exists").getAsBoolean();
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
    //绑定第三方账号
    public void bindAccount(String phonenum,String platform){
        Observable<JsonObject> observable = mDataManager.bindAccount(platform,phonenum);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction action = new ModelAction();
                        action.action = Action.SignUpVertifyCodeModel_BindAccount;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
