package com.teapopo.life.model.welfare;

import android.content.Context;
import android.graphics.AvoidXfermode;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
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
    public void getEventGoodsListModel(String id, final int type){
        Observable<JsonObject> observable = mDataManager.getEventGoodsList(id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<List<EventGoods>>>() {
                    @Override
                    public Observable<List<EventGoods>> call(JsonObject jsonObject) {
                        List<EventGoods> list = handleEventGoodsJson(jsonObject,type);
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
     * @param type  活动商品列表的类型
     *             1.全部活动商品  2.积分兑换 3.热门 4.最新
     *              当type为2时，以积分是否为0进行筛选
     *              其他类型时，以相应的排序规则进行筛选
     * @return
     */
    private List<EventGoods> handleEventGoodsJson(JsonObject jsonObject,int type){
        List<EventGoods> eventGoodsList = new ArrayList<>();
        JsonObject data = jsonObject.getAsJsonObject("data");
        JsonArray goods = data.getAsJsonArray("goods");
        for (JsonElement o:goods){
            try {
                EventGoods eventGoods = LoganSquare.parse(o.toString(),EventGoods.class);
                if(type == 2){
                    int point = Integer.parseInt(eventGoods.points);
                    if(point!=0){
                        eventGoodsList.add(eventGoods);
                    }
                }else {
                    eventGoodsList.add(eventGoods);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //优化性能
        //当type为2的时候，不去执行Comparator
        if(type!=2){
            Collections.sort(eventGoodsList,new EventGoodsComparator(type));
        }
        return eventGoodsList;
    }
}
