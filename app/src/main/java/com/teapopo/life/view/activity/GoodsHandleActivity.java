package com.teapopo.life.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.teapopo.life.R;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.activity.DaggerGoodsHandleActivityComponent;
import com.teapopo.life.injection.component.activity.GoodsHandleActivityComponent;
import com.teapopo.life.injection.component.fragment.GoodsHandleFragmentComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.activity.GoodsHandleActivityModule;
import com.teapopo.life.injection.module.fragment.GoodsHandleFragmentModule;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.view.fragment.welfare.GoodsDetailFragment;
import com.teapopo.life.view.fragment.welfare.MakeOrderFragment;
import com.teapopo.life.view.fragment.welfare.ShoppingCartListFragment;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class GoodsHandleActivity extends SwipeBackBaseActivity {
    public static final int Navigate_TYPE_MakerOrder = 1;
    public static final int Navigate_TYPE_ShoppingCart = 2;
    public static final int Navigate_TYPE_GoodsDetail = 3;
    private GoodsHandleActivityComponent mComponent;

    private ArrayList<Parcelable> datalist;
    private int type;
    /**
     *
     * @param context
     * @param datalist 数据列表
     * @param type 要启动的类型
     *             1 商品的结算  2购物车  3商品的详情页
     * @return
     */
    public static Intent getStartIntent(Context context, ArrayList<Parcelable> datalist,int type) {
        Intent intent = new Intent(context, GoodsHandleActivity.class);
        intent.putParcelableArrayListExtra("datalist",datalist);
        intent.putExtra("type",type);
        return intent;
    }
    @Override
    public void onCreateBinding() {

        mComponent = DaggerGoodsHandleActivityComponent.builder().applicationComponent(ComponentHolder.getAppComponent())
                .activityModule(new ActivityModule(this))
                .goodsHandleActivityModule(new GoodsHandleActivityModule()).build();
        mComponent.inject(this);
    }

    public GoodsHandleFragmentComponent getFragmentComponent(){
        return  mComponent.goodsSettleMentFragmentComponent(new GoodsHandleFragmentModule());
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_settlement);
        datalist = getIntent().getParcelableArrayListExtra("datalist");
        type = getIntent().getIntExtra("type",0);
        routeFragment(datalist,type);
    }

    private void routeFragment(ArrayList<Parcelable> datalist, int type) {
        switch (type){
            case Navigate_TYPE_MakerOrder:
                startRootFragment(MakeOrderFragment.newInstance(datalist));
                break;
            case Navigate_TYPE_ShoppingCart:
                startRootFragment(ShoppingCartListFragment.newInstance());
                break;
            case Navigate_TYPE_GoodsDetail:
                EventGoods eventGoods = (EventGoods) datalist.get(0);
                startRootFragment(GoodsDetailFragment.newInstance(eventGoods.goods_id,eventGoods.id));
                break;
        }
    }

    private void startRootFragment(SupportFragment fragment){
        loadRootFragment(R.id.framelayout_goods_settlement,fragment);
    }
}
