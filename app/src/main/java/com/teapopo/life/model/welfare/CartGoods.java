package com.teapopo.life.model.welfare;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/7/7.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class CartGoods extends BaseEntity implements Cloneable,Parcelable{

    public String id;//购物车id

    public String event_goods_id;//活动商品id

    public String goods_id;//商品id

    public String goods_name;

    public int goods_num;

    public String getGoods_num(){
        return goods_num+"";
    }
    public String goods_wap_cover;

    public int goods_points;

    public String getGoods_points(){
        return "积分"+goods_points;
    }
    public float goods_price;

    public String getGoods_price(){
        return "价格"+goods_price;
    }
    public String goods_storage;

    //新增加的属性
    public boolean isSelected;

    //用来识别在改变购物车商品数量的时候是加商品还是减商品
    //1为加   0为减
    public int minusoradd;

    public String cover_ImgForOrderList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(event_goods_id);
        dest.writeString(goods_id);
        dest.writeString(goods_name);
        dest.writeInt(goods_num);
        dest.writeString(id);
        dest.writeString(goods_wap_cover);
        dest.writeInt(goods_points);
        dest.writeFloat(goods_price);
        dest.writeString(goods_storage);
        dest.writeString(cover_ImgForOrderList);
    }

    protected CartGoods(Parcel in) {
        id = in.readString();
        event_goods_id = in.readString();
        goods_id = in.readString();
        goods_name = in.readString();
        goods_num = in.readInt();
        id = in.readString();
        goods_wap_cover = in.readString();
        goods_points = in.readInt();
        goods_price = in.readFloat();
        goods_storage = in.readString();
        cover_ImgForOrderList = in.readString();
    }
    public CartGoods(){

    }
    public static final Parcelable.Creator<CartGoods> CREATOR = new Parcelable.Creator<CartGoods>() {
        public CartGoods createFromParcel(Parcel source) {
            return new CartGoods(source);
        }

        public CartGoods[] newArray(int size) {
            return new CartGoods[size];
        }
    };
}
