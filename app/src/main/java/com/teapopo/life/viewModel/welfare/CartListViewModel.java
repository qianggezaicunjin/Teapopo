package com.teapopo.life.viewModel.welfare;

import com.teapopo.life.BR;
import com.teapopo.life.model.welfare.CartGoods;
import com.teapopo.life.model.welfare.ShoppingCart.CartListModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.List;

/**
 * Created by louiszgm on 2016/7/7.
 */
public class CartListViewModel extends BaseRecyclerViewModel {

    private CartListModel mModel;
    public CartListViewModel(CartListModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getCartList(){
        mModel.getCartList();
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
