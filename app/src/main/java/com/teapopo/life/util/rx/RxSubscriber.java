package com.teapopo.life.util.rx;

import com.google.gson.JsonObject;
import com.teapopo.life.MyApplication;
import com.teapopo.life.data.ServerException;
import com.teapopo.life.util.DataUtils;

import rx.Subscriber;

/**
 * Created by louiszgm on 2016/5/20.
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        if(!DataUtils.isNetworkAvailable(MyApplication.getInstance())){
            _onError("请检查网络");
        }

        if(e instanceof ServerException){
            _onError(e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }
    public abstract void _onNext(T t);

    public abstract void _onError(String s);
}
