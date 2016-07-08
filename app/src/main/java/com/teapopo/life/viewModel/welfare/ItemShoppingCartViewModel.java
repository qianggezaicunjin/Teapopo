package com.teapopo.life.viewModel.welfare;

import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.welfare.CartGoods;
import com.teapopo.life.view.customView.EditText.DrawableClickablEdT;
import com.teapopo.life.viewModel.BaseViewModel;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/7.
 */
public class ItemShoppingCartViewModel extends BaseViewModel {

    @Bindable
    public CartGoods cartGoods = new CartGoods();

    public ItemShoppingCartViewModel(){

    }
    public void setCartGoods(CartGoods cartGoods){
        this.cartGoods = cartGoods;
        //进入购物车结算界面,默认是全选状态
        selectCartGoodsOrNot(true);
        //计算商品的单价
        singleGoods = calculateSingleCartGoods(this.cartGoods);
    }
    private CartGoods singleGoods;

    //设置商品是否被选中的标识位
    public void selectCartGoodsOrNot(boolean isChecked){
        cartGoods.isSelected = isChecked;
        if(!isChecked){
            cartGoods.minusoradd = 0;
        }else {
            cartGoods.minusoradd = 1;
        }
        ComponentHolder.getAppComponent().rxbus().post(cartGoods);
        notifyPropertyChanged(BR.cartGoods);
    }

    /**
     * 计算商品的单价
     * @param cartGoods
     * @return
     */
    private CartGoods calculateSingleCartGoods(CartGoods cartGoods){
        CartGoods single = null;
        try {
            single = (CartGoods) cartGoods.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        single.goods_num = 1;
        single.goods_points = cartGoods.goods_points/cartGoods.goods_num;
        single.goods_price = cartGoods.goods_price/cartGoods.goods_num;
        return single;
    }
    //商品的数量改变
    public void changeBuyNum(int type){
        switch (type){
            case DrawableClickablEdT.LeftDrawAble:
                //商品数量减一
                //如果该商品被选中，则通知总数量改变
                if(cartGoods.goods_num == 1){
                    handleNoticeInfo("商品最小数量不能小于1");
                }else {

                    //把标识设为0,即为要减商品
                    singleGoods.minusoradd = 0;

                    if(cartGoods.isSelected){
                        ComponentHolder.getAppComponent().rxbus().post(singleGoods);
                    }
                    cartGoods.goods_num-=singleGoods.goods_num;
                    cartGoods.goods_price-=singleGoods.goods_price;
                    cartGoods.goods_points-=singleGoods.goods_points;
                }
                break;
            case DrawableClickablEdT.RightDrawAble:
                //商品数量加一
                //如果该商品被选中，则通知总数量改变

                //把标识设为1，即为要增加商品
                singleGoods.minusoradd = 1;
                if(cartGoods.isSelected){
                    ComponentHolder.getAppComponent().rxbus().post(singleGoods);
                }

                cartGoods.goods_num+=singleGoods.goods_num;
                cartGoods.goods_price+=singleGoods.goods_price;
                cartGoods.goods_points+=singleGoods.goods_points;
                Timber.d("single的为: %d",singleGoods.goods_num);
                break;
        }
        notifyPropertyChanged(BR.cartGoods);
    }
}
