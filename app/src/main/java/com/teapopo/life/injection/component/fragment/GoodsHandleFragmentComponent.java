package com.teapopo.life.injection.component.fragment;

import com.teapopo.life.injection.module.fragment.GoodsHandleFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.welfare.OrderSettleMentModel;
import com.teapopo.life.view.fragment.welfare.AddressManageFragment;
import com.teapopo.life.view.fragment.welfare.GoodsDetailFragment;
import com.teapopo.life.view.fragment.welfare.MakeOrderFragment;
import com.teapopo.life.view.fragment.welfare.OrderSettleMentFragment;

import dagger.Subcomponent;

/**
 * Created by louiszgm on 2016/6/30.
 */
@PerActivity
@Subcomponent(modules = GoodsHandleFragmentModule.class)
public interface GoodsHandleFragmentComponent {
    void inject(MakeOrderFragment fragment);
    void inject(AddressManageFragment fragment);
    void inject(OrderSettleMentFragment fragment);
    void inject(GoodsDetailFragment fragment);
}
