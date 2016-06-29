package com.teapopo.life.model.sharedpreferences;

import me.yokeyword.rxapi.Spf;

/**
 * Created by louiszgm on 2016/6/29.
 *
 * 用途: 保存活动商品的列表
 */
@Spf
public class EventGoodsSp {
    String eventId;
    String content;//保存的是json字符串
}
