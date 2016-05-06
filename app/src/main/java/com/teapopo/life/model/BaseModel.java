package com.teapopo.life.model;

import android.content.Context;

import com.teapopo.life.MyApplication;
import com.teapopo.life.data.DataManager;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.view.customView.RequestView;

/**
 * Created by louiszgm on 2016/5/6.
 */
public class BaseModel {
    public DataManager mDataManager;
    public Context mContext;
    public RxBus mRxBus;
    public RequestView mRequestView;//当请求网络接口成功是，通过这个接口发送通知
    public BaseModel(Context context){
        this.mContext = context;
        mDataManager = MyApplication.get(mContext).getComponent().dataManager();
        mRxBus = MyApplication.get(mContext).getComponent().rxbus();
    }

    public void setView(RequestView<BaseEntity> requestView) {
        this.mRequestView = requestView;
    }
}
