package com.teapopo.life.model.imageselect;



import android.os.Parcel;
import android.os.Parcelable;

import com.teapopo.life.model.BaseEntity;

/**
 * Image bean
 * Created by Yancy on 2015/12/2.
 */
public class Image extends BaseEntity implements Parcelable{

    public String path;
    public String name;
    public long time;
    public boolean isSelected = false;
    public Image(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(name);
        dest.writeLong(time);
        dest.writeByte((byte)(isSelected ?1:0));
    }

    protected Image(Parcel in) {
        path = in.readString();
        name = in.readString();
        time = in.readLong();
        isSelected =in.readByte()!=0;
    }
    public Image(){

    }
    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}