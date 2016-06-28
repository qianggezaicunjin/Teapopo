package com.teapopo.life.model.welfare;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.rx.RxResultHelper;

import rx.Observable;
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
    public void getEventGoodsListModel(String id,int type){
        Observable<JsonObject> observable = mDataManager.getEventGoodsList(id,type);
//        observable.subscribeOn(Schedulers.io())
//                .compose(RxResultHelper.<JsonObject>handleResult())
//                .
    }
}
