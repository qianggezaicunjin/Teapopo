package com.teapopo.life.model.welfare;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louiszgm on 2016/6/22.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class  Event extends BaseEntity implements Parcelable{

    public String id;

    public String intro;
    @JsonField(name = "jingle")
    public String adWords;

    public String name;
    @JsonField(name = "wap_banner")
    public String bannerImg;
    @JsonField(name = "wap_cover")
    public String coverImg;

    public long start_time;

    public long end_time;

    public String min_price;


    //以下是增加的属性
    public List<EventGoods> goods = new ArrayList<>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.intro);
        dest.writeString(this.adWords);
        dest.writeString(this.name);
        dest.writeString(this.bannerImg);
        dest.writeString(this.coverImg);
        dest.writeLong(this.start_time);
        dest.writeLong(this.end_time);
        dest.writeString(this.min_price);
        dest.writeList(goods);
    }

    protected Event(Parcel in) {
        this.id = in.readString();
        this.intro = in.readString();
        this.adWords = in.readString();
        this.name = in.readString();
        this.bannerImg = in.readString();
        this.coverImg = in.readString();
        this.start_time = in.readLong();
        this.end_time = in.readLong();
        this.min_price = in.readString();
         in.readList(this.goods,null);
    }
    public Event(){

    }
    public static final Creator<Event> CREATOR = new Creator<Event>() {
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
