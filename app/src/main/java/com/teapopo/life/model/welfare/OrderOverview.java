package com.teapopo.life.model.welfare;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/7/4.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class OrderOverview extends BaseEntity {
    public long add_time;

    public String coupon_value;

    public String freight;

    public String goods_amount;

    public String id;

    public String order_amount;

    public String order_points;

    public String order_sn;

    public String payment;

    public String shipping;

    public String status;

    //
}