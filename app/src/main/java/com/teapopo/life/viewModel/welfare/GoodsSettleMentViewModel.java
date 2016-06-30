package com.teapopo.life.viewModel.welfare;

import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.model.welfare.GoodsSettleMentModel;
import com.teapopo.life.viewModel.BaseViewModel;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class GoodsSettleMentViewModel extends BaseViewModel {

    public EventGoods goods;

    private GoodsSettleMentModel mModel;
    public GoodsSettleMentViewModel(GoodsSettleMentModel model){
        mModel = model;
        mModel.setView(this);
    }
}
