package com.teapopo.life.model.user;

import android.content.Context;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonObject;
import com.teapopo.life.data.remote.cookie.SerializableOkHttpCookies;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.erroinfo.ErroInfo;
import com.teapopo.life.model.sharedpreferences.RxSpf_UserInfoSp;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/11.
 */
public class UserInfoModel extends BaseModel {

    RxSpf_UserInfoSp rxSpf_userInfoSp = RxSpf_UserInfoSp.create(mContext);//缓存

    public UserInfoModel(Context context) {
        super(context);
    }

    public void getUserInfo() {
//        if(rxSpf_userInfoSp.userInfo().exists()){
//            getFromCache();
//        }else {
//            getFromNetWork();
//        }
        getFromNetWork();
    }

    private void getFromCache() {
        Timber.d("从缓存取出数据");
        String encode = rxSpf_userInfoSp.userInfo().get();
        UserInfo userInfo = UserInfo.decodeUserInfo(encode);
        mRequestView.onRequestSuccess(userInfo);
    }

    //从网络获取数据
    private void getFromNetWork() {
        Timber.d("从网络取出数据");
        final Observable<JsonObject> observable = mDataManager.getUserInfo();
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<UserInfo>>() {
                    @Override
                    public Observable<UserInfo> call(JsonObject jsonObject) {
                        UserInfo userInfo = new UserInfo();
                        try {
                            userInfo = LoganSquare.parse(jsonObject.toString(),UserInfo.class);
                            //将userinfo序列化成string
//                            String encodeString = UserInfo.encodeUserInfo(new SerializableUserInfo(userInfo));
                            //将个人信息缓存至SP
                            rxSpf_userInfoSp.edit()
                                    .username()
                                    .put(userInfo.nickname)
                                    .commit();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.just(userInfo);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<UserInfo>() {
                    @Override
                    public void _onNext(UserInfo userInfo) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.UserInfoModel_GetUserInfo;
                        modelAction.t = userInfo;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {

                    }
                });
    }

    public void logOut(){
        Observable<JsonObject> observable = mDataManager.logOut();
        observable.subscribeOn(Schedulers.io())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.UserInfoModel_LogOut;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }

}
