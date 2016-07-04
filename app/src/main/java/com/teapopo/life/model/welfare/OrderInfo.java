package com.teapopo.life.model.welfare;

import com.teapopo.life.model.Alipay.AliPay;
import com.teapopo.life.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louiszgm on 2016/7/2.
 */
public class OrderInfo extends BaseEntity {
    //商品信息
    public List<GoodsOverview> goodsOverviewList;
    //订单的总览
    public OrderOverview orderOverview;
    //配送地址
    public Address address;

}
