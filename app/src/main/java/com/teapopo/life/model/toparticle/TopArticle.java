package com.teapopo.life.model.toparticle;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

import java.util.List;

/**
 * Created by louiszgm on 2016/4/9 0009.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class TopArticle extends BaseEntity implements Parcelable{
    @JsonField(name = "image")
    public String topImageUrl;

    public String title;

    @JsonField(name = "wap_link")
    public String contentUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.topImageUrl);
        dest.writeString(this.title);
        dest.writeString(this.contentUrl);
    }
    protected TopArticle(Parcel in) {
       this.topImageUrl = in.readString();
        this.title = in.readString();
        this.contentUrl = in.readString();
    }
    public TopArticle(){

    }
    public static final Creator<TopArticle> CREATOR = new Creator<TopArticle>() {
        public TopArticle createFromParcel(Parcel source) {
            return new TopArticle(source);
        }

        public TopArticle[] newArray(int size) {
            return new TopArticle[size];
        }
    };
}
