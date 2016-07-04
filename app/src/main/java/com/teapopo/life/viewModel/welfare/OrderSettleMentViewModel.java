package com.teapopo.life.viewModel.welfare;

import com.teapopo.life.model.welfare.OrderSettleMentModel;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

/**
 * Created by louiszgm on 2016/7/2.
 */
public class OrderSettleMentViewModel extends BaseRecyclerViewModel {

    private OrderSettleMentModel mModel;

    public OrderSettleMentViewModel(OrderSettleMentModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getOrderInfo(String orderId){
        mModel.getOrderInfo(orderId);
    }
}
