package com.teapopo.life.model.welfare;

import android.content.Context;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class MakeOrderModel extends BaseModel {
    public MakeOrderModel(Context context) {
        super(context);
    }


    public void makeOrder(String buy_info,String address_id,String remark){
        Observable<JsonObject> observable = mDataManager.makeOrder(buy_info,address_id,remark);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.GoodsSettleMentModel_MakeOrder;

                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
