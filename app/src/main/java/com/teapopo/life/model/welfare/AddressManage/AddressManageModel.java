package com.teapopo.life.model.welfare.AddressManage;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.welfare.Address;
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
 * Created by louiszgm on 2016/7/1.
 */
public class AddressManageModel extends BaseModel {
    public AddressManageModel(Context context) {
        super(context);
    }

    public void getAddressList(){
        Observable<JsonObject> observable = mDataManager.getAddressList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<List<Address>>>() {
                    @Override
                    public Observable<List<Address>> call(JsonObject jsonObject) {
                        List<Address> list = handleAddressListJson(jsonObject);
                        return Observable.just(list);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<Address>>() {
                    @Override
                    public void _onNext(List<Address> addresses) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.AddressManageModel_GetAddressLsit;
                        modelAction.t = addresses;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }

    private List<Address> handleAddressListJson(JsonObject jsonObject) {
        List<Address> addressList = new ArrayList<>();
        JsonObject data = jsonObject.getAsJsonObject("data");
        JsonArray jsonArray = data.getAsJsonArray("address");
        for(JsonElement o:jsonArray){
            try {
                Address address = LoganSquare.parse(o.toString(),Address.class);
                addressList.add(address);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return addressList;
    }
}
