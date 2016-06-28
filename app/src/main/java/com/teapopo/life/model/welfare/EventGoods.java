package com.teapopo.life.model.welfare;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/6/28.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class EventGoods extends BaseEntity{

    public String collect_num;

    public String comment_num;

    @JsonField(name = "wap_cover")
    public String coverImg;

    public String goods_id;

    public String guide_content;

    public String id;

    public String name;

    public String points;

    public String price;

    public int sale_num;

    public String storage;

    public int is_commend;
}
