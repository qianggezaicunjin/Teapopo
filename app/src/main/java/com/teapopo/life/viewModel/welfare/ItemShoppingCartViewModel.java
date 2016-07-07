package com.teapopo.life.viewModel.welfare;

import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.welfare.CartGoods;
import com.teapopo.life.view.customView.EditText.DrawableClickablEdT;
import com.teapopo.life.viewModel.BaseViewModel;

import java.util.HashMap;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/7.
 */
public class ItemShoppingCartViewModel extends BaseViewModel {

    @Bindable
    public CartGoods cartGoods = new CartGoods();

    public void selectCartGoodsOrNot(boolean isChecked){
        cartGoods.isSelected = isChecked;
        ComponentHolder.getAppComponent().rxbus().post(cartGoods);
    }

    public void changeBuyNum(int type){
        switch (type){
            case DrawableClickablEdT.LeftDrawAble:
                Timber.d("点击了左侧");
                if(cartGoods.goods_num == 1){
                    handleNoticeInfo("商品最小数量不能小于1");
                }else {
                    cartGoods.goods_num-=1;
                    cartGoods.goods_price-=cartGoods.goods_price;
                    cartGoods.goods_points-=cartGoods.goods_points;
                }
                break;
            case DrawableClickablEdT.RightDrawAble:
                Timber.d("点击了右侧");
                cartGoods.goods_num+=1;
                cartGoods.goods_price+=cartGoods.goods_price;
                cartGoods.goods_points+=cartGoods.goods_points;
                break;
        }
        notifyPropertyChanged(BR.cartGoods);
    }
}
