package com.teapopo.life.model.welfare.EventGoodsList;

import android.content.Context;
import android.graphics.AvoidXfermode;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.sharedpreferences.EventGoodsSp;
import com.teapopo.life.model.sharedpreferences.RxSpf_EventGoodsSp;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by louiszgm on 2016/6/24.
 */
public class EventGoodsListModel extends BaseModel {

    private RxSpf_EventGoodsSp cache = RxSpf_EventGoodsSp.create(mContext);

    public EventGoodsListModel(Context context) {
        super(context);
    }
    /**
     *获取活动商品的列表
     * @param id    活动id
     * @param type 活动商品列表的类型
     *             1.全部活动商品  2.积分兑换 3.热门 4.最新
     * @return
     */
    public void getEventGoodsListModel(String id,  int type){
        getFromNetWork(id,type);
    }


    private void getFromNetWork(final String id, final int type) {
        Observable<JsonObject> observable = mDataManager.getEventGoodsList(id,type);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<List<EventGoods>>>() {
                    @Override
                    public Observable<List<EventGoods>> call(JsonObject jsonObject) {
                        List<EventGoods> list = handleEventGoodsJson(jsonObject);
                        return Observable.just(list);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<EventGoods>>() {
                    @Override
                    public void _onNext(List<EventGoods> eventGoodses) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.EventGoodsListModel_GetGoodsList;
                        modelAction.t = eventGoodses;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }

    /**
     *
     * @param jsonObject
     * @return
     */
    private List<EventGoods> handleEventGoodsJson(JsonObject jsonObject){
        List<EventGoods> eventGoodsList = new ArrayList<>();
        JsonObject data = jsonObject.getAsJsonObject("data");
        JsonArray goods = data.getAsJsonArray("goods");
        for (JsonElement o:goods){
            try {
                EventGoods eventGoods = LoganSquare.parse(o.toString(),EventGoods.class);
                    eventGoodsList.add(eventGoods);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return eventGoodsList;
    }
}
