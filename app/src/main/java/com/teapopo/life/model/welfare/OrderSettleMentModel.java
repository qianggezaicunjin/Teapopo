package com.teapopo.life.model.welfare;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonObject;
import com.teapopo.life.model.Alipay.AliPay;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by louiszgm on 2016/7/2.
 */
public class OrderSettleMentModel extends BaseModel {
    public OrderSettleMentModel(Context context) {
        super(context);
    }

    public void getOrderInfo(String orderId){
        Observable<JsonObject> observable = mDataManager.getOrderInfo(orderId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {

                    }

                    @Override
                    public void _onError(String s) {

                    }
                });
    }


    private OrderInfo getPayInfo(JsonObject jsonObject){
        JsonObject alipay = jsonObject.getAsJsonObject("alipay");
        OrderInfo orderInfo = new OrderInfo();
        try {
            //添加支付宝相应信息
            AliPay aliPay = LoganSquare.parse(alipay.toString(),AliPay.class);
            orderInfo.aliPay = aliPay;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orderInfo;
    }

}
