package com.teapopo.life.model;

import android.content.Context;
import android.support.annotation.DimenRes;

import com.teapopo.life.MyApplication;
import com.teapopo.life.data.DataManager;
import com.teapopo.life.data.ServerException;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.customView.RequestView;

import rx.Observable;
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

    public String setWebImageSize(@DimenRes int width, @DimenRes int heigth, String imageUrl){
        int width1 = (int) mContext.getResources().getDimension(width);
        int height1 = (int) mContext.getResources().getDimension(heigth);
        return imageUrl+"_"+width1+"x"+height1;
    }
}
