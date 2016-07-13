package com.teapopo.life.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by louiszgm on 2016/7/11.
 */
public class Goods extends BaseEntity implements Parcelable{
    public String event_goods_id;

    public String cover_Img;

    public String name;

    public int points;
    public String getPoints(){
        return "积分:"+points;
    }
    public float price;
    public String getPrice(){
        return "价格:"+price;
    }
    public int buy_num;
    public String getBuy_num(){
        return "购买数量:"+buy_num;
    }
    public String freight;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(event_goods_id);
        dest.writeString(cover_Img);
        dest.writeString(name);
        dest.writeInt(points);
        dest.writeFloat(price);
        dest.writeInt(buy_num);
        dest.writeString(freight);
    }

    protected Goods(Parcel in) {
        event_goods_id = in.readString();
        cover_Img = in.readString();
        name = in.readString();
        points = in.readInt();
        price = in.readFloat();
        buy_num = in.readInt();
        freight = in.readString();
    }
    public Goods(){

    }
    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        public Goods createFromParcel(Parcel source) {
            return new Goods(source);
        }

        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

}
