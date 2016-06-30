package com.teapopo.life.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teapopo.life.R;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.activity.DaggerGoodsSettleMentActivityComponent;
import com.teapopo.life.injection.component.activity.GoodsSettleMentActivityComponent;
import com.teapopo.life.injection.component.fragment.GoodsSettleMentFragmentComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.activity.GoodsSettleMentActivityModule;
import com.teapopo.life.injection.module.fragment.GoodsSettleMentFragmentModule;
import com.teapopo.life.model.welfare.Event;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.view.fragment.ArticleInfoFragment;
import com.teapopo.life.view.fragment.welfare.GoodsSettleMentFragment;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class GoodsSettleMentActivity extends SwipeBackBaseActivity {
    private GoodsSettleMentActivityComponent mComponent;

    public static Intent getStartIntent(Context context, EventGoods goods) {
        Intent intent = new Intent(context, GoodsSettleMentActivity.class);
        intent.putExtra("goods",goods);
        return intent;
    }
    @Override
    public void onCreateBinding() {
        mComponent = DaggerGoodsSettleMentActivityComponent.builder().applicationComponent(ComponentHolder.getAppComponent())
                .activityModule(new ActivityModule(this))
                .goodsSettleMentActivityModule(new GoodsSettleMentActivityModule()).build();
        mComponent.inject(this);
    }

    public GoodsSettleMentFragmentComponent getFragmentComponent(){
        return  mComponent.goodsSettleMentFragmentComponent(new GoodsSettleMentFragmentModule());
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_settlement);
        start(GoodsSettleMentFragment.newInstance((EventGoods) getIntent().getParcelableExtra("goods")));
    }

    @Override
    protected int setContainerId() {
        return R.id.framelayout_goods_settlement;
    }
}
