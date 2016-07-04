package com.teapopo.life.viewModel.welfare;

import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.teapopo.life.model.Alipay.AliPay;
import com.teapopo.life.model.welfare.OrderInfo;
import com.teapopo.life.model.welfare.OrderOverview;
import com.teapopo.life.model.welfare.OrderSettleMentModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.SignUtils;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/2.
 */
public class OrderSettleMentViewModel extends BaseRecyclerViewModel {

    private OrderSettleMentModel mModel;

    private OrderOverview orderOverview;
    public OrderSettleMentViewModel(OrderSettleMentModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getOrderInfo(String orderId){
        mModel.getOrderInfo(orderId);
    }

    public void pay(PayTask payTask){
        try {
            AliPay aliPay = new AliPay(orderOverview.order_sn,orderOverview.order_amount);
            payByAlipay(aliPay.getPayInfo(),payTask);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void payByAlipay(final String payinfo, final PayTask payTask){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 调用支付接口，获取支付结果
                String result = payTask.pay(payinfo, true);
                Timber.d("支付结果为:%s",result);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if (action ==  Action.OrderSettleMentModel_GetOrderInfo){
            OrderInfo orderInfo = (OrderInfo) data.t;
            orderOverview = orderInfo.orderOverview;
        }
    }
}
