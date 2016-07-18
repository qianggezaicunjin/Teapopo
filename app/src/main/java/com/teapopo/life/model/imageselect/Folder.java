package com.teapopo.life.model.imageselect;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

import com.teapopo.life.model.BaseEntity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Folder bean
 * Created by Yancy on 2015/12/2.
 */
public class Folder extends BaseEntity implements Parcelable{

    public String name;
    public String path;
    public String coverPath;
    public ArrayList<Image> images;
    //新增的属性
    public boolean isSelected = false;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(coverPath);
        Image[] imagesArray = (Image[]) images.toArray();
        dest.writeParcelableArray(imagesArray,flags);
        dest.writeByte((byte)(isSelected ?1:0));
    }

    protected Folder(Parcel in) {
        name = in.readString();
        path = in.readString();
        coverPath = in.readString();
        Image[] array = null;
        Parcelable[] parcelables = in.readParcelableArray(Image.class.getClassLoader());
        if (parcelables != null) {
            array = Arrays.copyOf(parcelables, parcelables.length, Image[].class);
        }
        Collections.addAll(images,array);
        isSelected =in.readByte()!=0;
    }
    public Folder(){

    }
    public static final Parcelable.Creator<Folder> CREATOR = new Parcelable.Creator<Folder>() {
        public Folder createFromParcel(Parcel source) {
            return new Folder(source);
        }

        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };
    @Override
    public boolean equals(Object o) {
        try {
            Folder other = (Folder) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}