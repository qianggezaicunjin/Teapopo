package com.teapopo.life.model.welfare;

import com.teapopo.life.model.article.Article;

import java.util.Comparator;

/**
 * Created by louiszgm on 2016/6/28.
 */
public class EventGoodsComparator implements Comparator<EventGoods> {

    private int type;//排序类型 1.全部活动商品  3.热门 4.最新

    public EventGoodsComparator(int type){
        this.type = type;
    }
    @Override
    public int compare(EventGoods lhs, EventGoods rhs) {
        switch (type){
            case 1:
                return sortNormal(lhs,rhs);
            case 3:
                return sortHot(lhs,rhs);
            case 4:
                return sortNew(lhs,rhs);
            default:
                return 0;
        }
    }

    /**
     * 显示全部商品的排序规则为:  在is_recommend为1的优先排在前面, is_recommend相同的情况下 id越大排在越前
     * @param lhs
     * @param rhs
     * @return
     */
    private int sortNormal(EventGoods lhs, EventGoods rhs) {
        int isCommend1 = lhs.is_commend;
        int isCommend2 = rhs.is_commend;
        if(isCommend1 == isCommend2){
            return sortNew(lhs,rhs);
        }else {
            return isCommend2-isCommend1;
        }

    }

    /**
     * 显示最新商品的规则为:根据活动id排序，id越大排在越前
     * @param lhs
     * @param rhs
     * @return
     */
    private int sortNew(EventGoods lhs, EventGoods rhs) {
        int id1 = Integer.parseInt(lhs.id);
        int id2 = Integer.parseInt(rhs.id);
        if(id1 == id2){
            return 0;
        }else {
            //销售数量从大到小排序
            return id2-id1;
        }
    }

    /**
     * 根据售出数量进行排序 售出越多排在越前
     * @param lhs
     * @param rhs
     * @return
     */
    private int sortHot(EventGoods lhs, EventGoods rhs) {
        if(lhs.sale_num == rhs.sale_num){
            return 0;
        }else {
            //销售数量从大到小排序
            return rhs.sale_num-lhs.sale_num;
        }
    }
}
