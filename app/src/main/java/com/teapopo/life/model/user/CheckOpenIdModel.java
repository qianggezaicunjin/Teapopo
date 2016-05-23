package com.teapopo.life.model.user;

import android.content.Context;
import android.databinding.tool.reflection.SdkUtil;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/23.
 */
public class CheckOpenIdModel extends BaseModel {
    public CheckOpenIdModel(Context context) {
        super(context);
    }

    public void check_openid(String platform){
        Platform p = ShareSDK.getPlatform(platform);
        String openid = p.getDb().getUserId();
        Timber.d("%s的openid为:%s",platform,openid);
        Observable<JsonObject> observable = mDataManager.check_openid(openid,platform);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {

                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
