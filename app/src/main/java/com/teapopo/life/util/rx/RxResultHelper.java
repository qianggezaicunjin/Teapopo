package com.teapopo.life.util.rx;

import com.google.gson.JsonObject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by louiszgm on 2016/5/18.
 */
public class RxResultHelper {

    public static <T> Observable.Transformer<JsonObject, T> handleResult() {
        return new Observable.Transformer<JsonObject, T>() {
            @Override
            public Observable<T> call(Observable<JsonObject> tObservable) {
                return tObservable.flatMap(
                        new Func1<JsonObject, Observable<T>>() {
                            @Override
                            public Observable<T> call(JsonObject result) {

                                if(!result.has("errcode")){
                                    return (Observable<T>) createData(result.toString());
                                }
                               //// TODO: 2016/5/18 在此处加入处理错误的代码
                                return Observable.empty();

                            }
                        }

                );
            }
        };
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
