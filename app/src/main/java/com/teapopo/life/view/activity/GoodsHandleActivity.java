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
import com.teapopo.life.view.fragment.welfare.MakeOrderFragment;

import java.util.ArrayList;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class GoodsHandleActivity extends SwipeBackBaseActivity {
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
            case 1:
                start(MakeOrderFragment.newInstance(datalist));
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    @Override
    protected int setContainerId() {
        return R.id.framelayout_goods_settlement;
    }
}
