package com.teapopo.life.model.article.publisharticle;

import android.os.Parcel;
import android.os.Parcelable;

import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.imageselect.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by louiszgm on 2016/7/20.
 */
public class PublishArticleData extends BaseEntity implements Parcelable{
    public String title;
    public String coverImg;
    public String content;
    public String[] tags;
    public List<Image> images = new ArrayList<>();
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(coverImg);
        dest.writeString(content);
        dest.writeStringArray(tags);
        dest.writeParcelableArray(images.toArray(new Image[images.size()]),flags);
    }

    protected PublishArticleData(Parcel in) {
        title = in.readString();
        coverImg = in.readString();
        content = in.readString();
         in.readStringArray(tags);
        Image[] array = new Image[images.size()];
        in.readParcelableArray(ClassLoader.getSystemClassLoader());
        Collections.addAll(images,array);
    }
    public PublishArticleData(){

    }
    public static final Parcelable.Creator<PublishArticleData> CREATOR = new Parcelable.Creator<PublishArticleData>() {
        public PublishArticleData createFromParcel(Parcel source) {
            return new PublishArticleData(source);
        }

        public PublishArticleData[] newArray(int size) {
            return new PublishArticleData[size];
        }
    };

}
