package com.teapopo.life.injection.module.fragment;

import android.content.Context;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.articleinfo.ArticleInfoModel;
import com.teapopo.life.model.welfare.AddressManageModel;
import com.teapopo.life.model.welfare.GoodsSettleMentModel;
import com.teapopo.life.viewModel.articleinfo.ArticleInfoViewModel;
import com.teapopo.life.viewModel.welfare.AddressManageViewModel;
import com.teapopo.life.viewModel.welfare.GoodsSettleMentViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/6/30.
 */
@Module
public class GoodsHandleFragmentModule {

    //for GoodsSettleMentFragment
    @Provides
    @PerActivity
    GoodsSettleMentViewModel provideGoodsSettleMentViewModel(GoodsSettleMentModel model){
        return new GoodsSettleMentViewModel(model);
    }
    @Provides
    @PerActivity
    GoodsSettleMentModel provideGoodsSettleMentModel(Context context){
        return new GoodsSettleMentModel(context);
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
}
