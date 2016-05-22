package com.teapopo.life.util.rx;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teapopo.life.data.ServerException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/18.
 */
public class RxResultHelper {

    public static <T> Observable.Transformer<T, T> handleResult() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.flatMap(
                        new Func1<T, Observable<T>>() {
                            @Override
                            public Observable<T> call(T json) {
                                if(json instanceof JsonObject){
                                    return doJsonObject((JsonObject) json);
                                }else {
                                    return doJsonArray((JsonArray)json);
                                }
                            }
                        }

                );
            }
        };
    }

    private static <T>Observable<T> doJsonArray(JsonArray json) {
        return (Observable<T>) createData(json);
    }

    private static <T>Observable<T> doJsonObject(JsonObject jsonObject) {

        if(!jsonObject.has("errcode")){
            return (Observable<T>) createData(jsonObject);
        }
        else {
            //如果errcode不等于0,则返回的是服务器错误信息
            if(jsonObject.get("errcode").getAsInt()!=0){
                Timber.d("errcode不为0");
                return Observable.error(new ServerException(jsonObject.get("errmsg").getAsString()));
            }else {
                return (Observable<T>) createData(jsonObject);
            }
        }
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
