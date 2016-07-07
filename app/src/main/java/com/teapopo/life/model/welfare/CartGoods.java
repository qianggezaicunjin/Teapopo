package com.teapopo.life.model.welfare;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/7/7.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class CartGoods extends BaseEntity {
    public String id;//购物车id

    public String evenets_goods_id;//活动商品id

    public String goods_id;//商品id

    public String goods_name;

    public String goods_num;

    public String goods_wap_cover;

    public String goods_points;

    public String goods_price;

    public String goods_storage;
}
