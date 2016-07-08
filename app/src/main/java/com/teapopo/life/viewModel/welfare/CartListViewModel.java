package com.teapopo.life.viewModel.welfare;

import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.model.welfare.CartGoods;
import com.teapopo.life.model.welfare.ShoppingCart.CardsGoodsOverView;
import com.teapopo.life.model.welfare.ShoppingCart.CartListModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louiszgm on 2016/7/7.
 */
public class CartListViewModel extends BaseRecyclerViewModel {

    private CartListModel mModel;

    @Bindable
    public CardsGoodsOverView cardsGoodsOverView = new CardsGoodsOverView();

    public CartListViewModel(CartListModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getCartList(){
        mModel.getCartList();
    }

    public void selectAll(boolean isSelected){
        for(Object o:data){
            CartGoods cartGoods = (CartGoods) o;
            cartGoods.isSelected = isSelected;
            notifyPropertyChanged(BR.cartGoods);
        }
    }
    //计算结算总额
    public void calculateCartGoodsOverView(CartGoods cartGoods){
        //cartGoods.minusoradd 为0表示要减去该商品，1表示要加上
        if(cartGoods.minusoradd == 0){
            cardsGoodsOverView.goods_num-=cartGoods.goods_num;
            cardsGoodsOverView.goods_price-=cartGoods.goods_price;
            cardsGoodsOverView.goods_points-=cartGoods.goods_points;
        }else if(cartGoods.minusoradd == 1){
            cardsGoodsOverView.goods_num+=cartGoods.goods_num;
            cardsGoodsOverView.goods_price+=cartGoods.goods_price;
            cardsGoodsOverView.goods_points+=cartGoods.goods_points;
        }
            notifyPropertyChanged(BR.cardsGoodsOverView);
    }
    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.CartListModel_GetCartList){
            List<CartGoods> list = (List<CartGoods>) data.t;
            super.data.addAll(list);
            notifyPropertyChanged(BR.data);
        }
    }
}
