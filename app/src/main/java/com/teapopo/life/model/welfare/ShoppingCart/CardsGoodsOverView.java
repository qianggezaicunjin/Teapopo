package com.teapopo.life.model.welfare.ShoppingCart;

import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/7/7.
 */
public class CardsGoodsOverView extends BaseEntity {
    public int goods_num;

    public String getGoods_num(){
        return "合计"+goods_num;
    }
    public float goods_price;

    public int goods_points;
}
