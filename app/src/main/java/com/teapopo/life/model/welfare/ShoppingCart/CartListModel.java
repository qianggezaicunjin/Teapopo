package com.teapopo.life.model.welfare.ShoppingCart;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.welfare.CartGoods;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxSubscriber;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/7.
 */
public class CartListModel extends BaseModel {
    public CartListModel(Context context) {
        super(context);
    }

    public void getCartList(){
        Observable<List<CartGoods>> observable = mDataManager.getCartList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<CartGoods>>() {
                    @Override
                    public void _onNext(List<CartGoods> cartGoodses) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.t = cartGoodses;
                        modelAction.action = Action.CartListModel_GetCartList;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
