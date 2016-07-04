package com.teapopo.life.model.welfare;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.model.Alipay.AliPay;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
                .flatMap(new Func1<JsonObject, Observable<OrderInfo>>() {
                    @Override
                    public Observable<OrderInfo> call(JsonObject jsonObject) {
                        OrderInfo orderInfo = getOrderInfo(jsonObject);
                        return Observable.just(orderInfo);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<OrderInfo>() {
                    @Override
                    public void _onNext(OrderInfo orderInfo) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.OrderSettleMentModel_GetOrderInfo;
                        modelAction.t = orderInfo;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }


    private OrderInfo getOrderInfo(JsonObject jsonObject){
        JsonObject order = jsonObject.getAsJsonObject("order");
        JsonObject address = jsonObject.getAsJsonObject("address");
        JsonArray goodses = jsonObject.getAsJsonArray("goods");
        OrderInfo orderInfo = new OrderInfo();
        try {
            //添加订单总览
            OrderOverview orderOverview = LoganSquare.parse(order.toString(),OrderOverview.class);
            orderInfo.orderOverview = orderOverview;
            //添加配送地址
            Address address1 = LoganSquare.parse(address.toString(),Address.class);
            orderInfo.address = address1;
            //添加商品的总览信息
            List<GoodsOverview> list = new ArrayList<>();
            for(JsonElement element:goodses){
                GoodsOverview goodsOverview = LoganSquare.parse(element.toString(),GoodsOverview.class);
                list.add(goodsOverview);
            }
            orderInfo.goodsOverviewList = list;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orderInfo;
    }

}
