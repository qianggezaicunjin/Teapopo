package com.teapopo.life.model.welfare;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.util.DataUtils;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * Created by louiszgm on 2016/7/4.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class OrderOverview extends BaseEntity {
    public long add_time;
    public String getAdd_time(){
        return "下单时间为:"+DataUtils.getTime(String.valueOf(add_time));
    }

    public String coupon_value;

    public String freight;

    public String goods_amount;

    public String id;

    public String order_amount;
    public String getOrder_amount(){
        return "总金额："+order_amount+"元 +"+order_points+"积分";
    }

    public String order_points;

    public String order_sn;
    public String getOrder_sn(){
        return "订单号为："+order_sn;
    }
    public String payment;

    public String shipping;

    public String status;

}
