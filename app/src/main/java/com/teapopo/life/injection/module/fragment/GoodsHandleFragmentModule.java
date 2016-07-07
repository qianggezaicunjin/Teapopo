package com.teapopo.life.injection.module.fragment;

import android.content.Context;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.welfare.AddressManage.AddressManageModel;
import com.teapopo.life.model.welfare.GoodsDetail.GoodsDetailModel;
import com.teapopo.life.model.welfare.MakeOrder.MakeOrderModel;
import com.teapopo.life.model.welfare.OrderSettleMentModel;
import com.teapopo.life.model.welfare.ShoppingCart.CartListModel;
import com.teapopo.life.viewModel.welfare.AddressManageViewModel;
import com.teapopo.life.viewModel.welfare.CartListViewModel;
import com.teapopo.life.viewModel.welfare.GoodsDetailViewModel;
import com.teapopo.life.viewModel.welfare.MakeOrderViewModel;
import com.teapopo.life.viewModel.welfare.OrderSettleMentViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/6/30.
 */
@Module
public class GoodsHandleFragmentModule {

    //for MakeOrderFragment
    @Provides
    @PerActivity
    MakeOrderViewModel provideGoodsSettleMentViewModel(MakeOrderModel model){
        return new MakeOrderViewModel(model);
    }
    @Provides
    @PerActivity
    MakeOrderModel provideGoodsSettleMentModel(Context context){
        return new MakeOrderModel(context);
    }

    //for AddressManageFragment
    @Provides
    @PerActivity
    AddressManageViewModel provideAddressManageViewModel(AddressManageModel model){
        return new AddressManageViewModel(model);
    }

    @Provides
    @PerActivity
    AddressManageModel provideAddressManageModel(Context context){
        return new AddressManageModel(context);
    }

    //for OrderSettleMentFragment
    @Provides
    @PerActivity
    OrderSettleMentViewModel provideOrderSettleMentViewModel(OrderSettleMentModel model){
        return new OrderSettleMentViewModel(model);
    }
    @Provides
    @PerActivity
    OrderSettleMentModel provideOrderSettleMentModel(Context context){
        return new OrderSettleMentModel(context);
    }
    //for GoodsDetailFragment
    @Provides
    @PerActivity
    GoodsDetailViewModel provideGoodsDetailViewModel(GoodsDetailModel model){
        return new GoodsDetailViewModel(model);
    }
    @Provides
    @PerActivity
    GoodsDetailModel provideGoodsDetailModel(Context context){
        return new GoodsDetailModel(context);
    }
    //for ShoppingCartListFragment
    @Provides
    @PerActivity
    CartListViewModel provideCartListViewModel(CartListModel model){
        return new CartListViewModel(model);
    }
    @Provides
    @PerActivity
    CartListModel provideCartListModel(Context context){
        return new CartListModel(context);
    }
}
