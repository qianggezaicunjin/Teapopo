package com.teapopo.life.viewModel.welfare;

import android.databinding.Bindable;

import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.viewModel.BaseViewModel;

/**
 * Created by louiszgm on 2016/6/28.
 */
public class ItemEventGoodsViewModel extends BaseViewModel {

    @Bindable
    public EventGoods eventGoods = new EventGoods();
    public ItemEventGoodsViewModel(){

    }
}
