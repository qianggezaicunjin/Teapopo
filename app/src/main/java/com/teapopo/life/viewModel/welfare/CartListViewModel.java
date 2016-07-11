package com.teapopo.life.viewModel.welfare;

import android.databinding.Bindable;
import android.os.Parcelable;

import com.teapopo.life.BR;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.welfare.CartGoods;
import com.teapopo.life.model.welfare.ShoppingCart.CardsGoodsOverView;
import com.teapopo.life.model.welfare.ShoppingCart.CartListModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

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

    //返回被选中的商品
    public ArrayList<Parcelable> getSelectedCartGoods(){
        ArrayList<Parcelable> result = new ArrayList<>();
        for(Object o:data){
            CartGoods cartGoods = (CartGoods) o;
            if(cartGoods.isSelected){
                result.add(cartGoods);
            }
        }
        return result;
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
