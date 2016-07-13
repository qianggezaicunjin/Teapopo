package com.teapopo.life.model.welfare;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/6/28.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class EventGoods extends BaseEntity implements Parcelable{

    public String collect_num;

    public String comment_num;

    @JsonField(name = "wap_cover")
    public String coverImg;

    public String getCoverImg(){
        return coverImg;
    }
    public String goods_id;

//    public String guide_content;
    public String content;

    public String id;

    public String name;

    public int points;

    public String getPoints(){
        return points+"积分";
    }
    public float price;

    public String getPrice(){
        return "￥"+price;
    }
    public int sale_num;

    public String getPayAmount(){
        return "￥"+price+"+"+points+"积分";
    }

    public String getSale_num(){
        return sale_num+"件";
    }
    public String storage;

    public String getStorage(){
        return "库存"+storage+"件";
    }
    public int is_commend;

    //新增加的属性
    public int buy_num = 1;

    public String cover_ImgforOrderList;
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(collect_num);
        dest.writeString(comment_num);
        dest.writeString(coverImg);
        dest.writeString(goods_id);
        dest.writeString(content);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(points);
        dest.writeFloat(price);
        dest.writeInt(sale_num);
        dest.writeString(storage);
        dest.writeInt(is_commend);
        dest.writeInt(buy_num);
        dest.writeString(cover_ImgforOrderList);
    }

    protected EventGoods(Parcel in) {
        collect_num = in.readString();
        comment_num = in.readString();
        coverImg = in.readString();
        goods_id = in.readString();
        content = in.readString();
        id = in.readString();
        name = in.readString();
        points = in.readInt();
        price = in.readFloat();
        sale_num = in.readInt();
        storage = in.readString();
        is_commend = in.readInt();
        buy_num = in.readInt();
        cover_ImgforOrderList = in.readString();
    }
    public EventGoods(){

    }
    public static final Creator<EventGoods> CREATOR = new Creator<EventGoods>() {
        public EventGoods createFromParcel(Parcel source) {
            return new EventGoods(source);
        }

        public EventGoods[] newArray(int size) {
            return new EventGoods[size];
        }
    };
}
