package com.teapopo.life.model.welfare;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/7/4.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class GoodsOverview extends BaseEntity {
    public String content;

    public String event_goods_id;

    public String goods_id;

    public String goods_name;

    public String goods_num;
    public String getGoods_num(){
        return "×"+goods_num;
    }

    public String goods_wap_cover;

    public String goods_price;

    public String goods_points;
    public String getGoods_points(){
        return  goods_points+"积分";
    }
}
