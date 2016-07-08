package com.teapopo.life.model.welfare;

import com.teapopo.life.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louiszgm on 2016/7/5.
 */
public class EventGoodsInfo extends BaseEntity {
    //活动商品的基本信息
    public EventGoods eventGoods;
    //活动商品导购的基本信息
    public Guide guide;
    //是否收藏了该商品
    public boolean is_collect;
    //商品的图片
    public List<String> wap_images = new ArrayList<>();
    //品牌简介
    public Brand brand;
    //剩余的评论数
    public String leftComment;
    public String getLeftComment(){
        return "还有"+leftComment+"条评论，点击查看";
    }
}
