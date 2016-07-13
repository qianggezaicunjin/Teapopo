package com.teapopo.life.viewModel.welfare;

import android.os.Parcelable;

import com.google.gson.JsonObject;
import com.teapopo.life.BR;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.Goods;
import com.teapopo.life.model.event.MakeOrderSuccessEvent;
import com.teapopo.life.model.welfare.CartGoods;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.model.welfare.MakeOrder.MakeOrderModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxSubscriber;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class MakeOrderViewModel extends BaseRecyclerViewModel {

    private MakeOrderModel mModel;

    public MakeOrderViewModel(MakeOrderModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void makeOrder(){
        //id 220 for TPP
        String address_id = "222";
//        JsonObject buy_info = new JsonObject();
//        buy_info.addProperty("2",1);
        mModel.makeOrder(makebuy_info(data),address_id,"测试订单");
    }

    //生产订单信息
    private String makebuy_info(List data) {
        JsonObject buy_info = new JsonObject();
        for(Object o:data){
            Goods goods = (Goods) o;
            Timber.d("订单的商品为:%s, %s",goods.event_goods_id,goods.name);
            buy_info.addProperty(goods.event_goods_id,goods.buy_num);
        }
        return buy_info.toString();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action ==  Action.GoodsSettleMentModel_MakeOrder){
            String orderId = (String) data.t;
            MakeOrderSuccessEvent makeOrderSuccessEvent = new MakeOrderSuccessEvent();
            makeOrderSuccessEvent.orderId = orderId;
            ComponentHolder.getAppComponent().rxbus().post(makeOrderSuccessEvent);
        }
    }

    public void handleGoodsList(ArrayList<Parcelable> goodsList) {
        Observable<Parcelable> observable = Observable.from(goodsList);
        observable.observeOn(Schedulers.io())
                .map(new Func1<Parcelable, Goods>() {
                    @Override
                    public Goods call(Parcelable parcelable) {
                        return CastToGoods(parcelable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<Goods>() {
                    @Override
                    public void _onNext(Goods goods) {
                        Timber.d("订单列表的商品图片为:%s",goods.cover_Img);
                        data.add(goods);
                    }

                    @Override
                    public void _onError(String s) {

                    }

                    @Override
                    public void onCompleted() {
                        notifyPropertyChanged(BR.data);
                    }
                });
    }

    /**
     * 将活动商品或者购物车的商品转换成 Goods类型
     * @param parcelable
     * @return
     */
    private Goods CastToGoods(Parcelable parcelable) {
        Goods goods = new Goods();

        if(parcelable instanceof EventGoods){
            Timber.d("活动商品转Goods");
            goods = eventGoodsCastToGoods((EventGoods) parcelable);
        }else if (parcelable instanceof CartGoods){
            Timber.d("购物车商品转Goods");
            goods = cartGoodsCastToGoods((CartGoods) parcelable);
        }
        return goods;
    }

    private Goods cartGoodsCastToGoods(CartGoods cartGoods) {
        Goods goods = new Goods();
        goods.event_goods_id = cartGoods.event_goods_id;
        Timber.d("购物车商品的id为:%s",cartGoods.event_goods_id);
        goods.name = cartGoods.goods_name;
        goods.cover_Img = cartGoods.cover_ImgForOrderList;
        goods.price = cartGoods.goods_price;
        goods.points = cartGoods.goods_points;
        goods.buy_num = cartGoods.goods_num;
        return goods;
    }

    private Goods eventGoodsCastToGoods(EventGoods eventGoods) {
            Goods goods = new Goods();
            goods.event_goods_id = eventGoods.id;
            goods.name = eventGoods.name;
            goods.cover_Img = eventGoods.cover_ImgforOrderList;
            goods.price = eventGoods.price;
            goods.points = eventGoods.points;
            goods.buy_num = eventGoods.buy_num;
            return goods;
    }
}
