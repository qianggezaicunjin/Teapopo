package com.teapopo.life.viewModel.welfare;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.view.customView.EditText.DrawableClickablEdT;
import com.teapopo.life.viewModel.BaseViewModel;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/1.
 */
public class ItemMakeOrderGoodsViewModel extends BaseViewModel {

    @Bindable
   public  EventGoods goods = new EventGoods();

    public ItemMakeOrderGoodsViewModel(){

    }

    public void changeBuyNum(int type){
        switch (type){
            case DrawableClickablEdT.LeftDrawAble:
                Timber.d("点击了左侧");
                if(goods.buy_num == 1){
                    handleNoticeInfo("商品最小数量不能小于1");
                }else {
                    goods.buy_num-=1;
                }
                break;
            case DrawableClickablEdT.RightDrawAble:
                Timber.d("点击了右侧");
                goods.buy_num+=1;
                break;
        }
        notifyPropertyChanged(BR.goods);
    }
}
