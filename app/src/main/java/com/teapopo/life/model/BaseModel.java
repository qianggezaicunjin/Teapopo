package com.teapopo.life.model;

import android.content.Context;

import com.teapopo.life.data.DataManager;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.view.customView.RequestView;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by louiszgm on 2016/5/6.
 */
public class BaseModel {
    public DataManager mDataManager;
    public Context mContext;
    public RxBus mRxBus;
    public RequestView mRequestView;//当请求网络接口成功是，通过这个接口发送通知
    public CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    public BaseModel(Context context){
        this.mContext = context;
        mDataManager = ComponentHolder.getAppComponent().dataManager();
        mRxBus =ComponentHolder.getAppComponent().rxbus();
    }

    public void setView(RequestView requestView) {
        this.mRequestView = requestView;
    }
}
