package com.teapopo.life.viewModel.welfare;

import android.databinding.Bindable;

import com.teapopo.life.model.welfare.GoodsOverview;
import com.teapopo.life.viewModel.BaseViewModel;

/**
 * Created by lhq on 2016/7/5.
 */

public class ItemSettlementViewModel extends BaseViewModel {
    @Bindable
    public GoodsOverview goodsOverview=new GoodsOverview();

    public ItemSettlementViewModel(){

    }
}
