package com.teapopo.life.viewModel.welfare;

import com.google.gson.JsonObject;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.model.welfare.MakeOrder.MakeOrderModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.List;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class MakeOrderViewModel extends BaseRecyclerViewModel {



    private MakeOrderModel mModel;
    public MakeOrderViewModel(MakeOrderModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void makeOrder(){
        String address_id = "220";
        JsonObject buy_info = new JsonObject();
        buy_info.addProperty("2",1);
        mModel.makeOrder(buy_info.toString(),address_id,"测试订单");
    }

    private String makebuy_info(List data) {
        JsonObject buy_info = new JsonObject();
        for(Object o:data){
            EventGoods goods = (EventGoods) o;
            buy_info.addProperty(goods.id,goods.buy_num);
        }
        return buy_info.toString();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action ==  Action.GoodsSettleMentModel_MakeOrder){

        }
    }
}
