package com.teapopo.life.model.welfare;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/7/1.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Address extends BaseEntity implements Parcelable{
    public String address;

    public String area_id;

    public String area_info;

    public String city_id;

    public String id;

    public boolean is_default;

    public String phone;

    public String province_id;

    public String truename;

    public String zipcode;

    //新增的属性
    public boolean isSelected;

    public String getBaseInfo(){
        return truename+" "+phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(area_id);
        dest.writeString(area_info);
        dest.writeString(city_id);
        dest.writeString(id);
//        dest.writeb(is_default);
        dest.writeString(phone);
        dest.writeString(province_id);
        dest.writeString(truename);
        dest.writeString(zipcode);
    }

    protected Address(Parcel in) {
        address = in.readString();
        area_id = in.readString();
        area_info = in.readString();
        city_id = in.readString();
        id = in.readString();
        phone = in.readString();
        province_id = in.readString();
        truename = in.readString();
        zipcode = in.readString();
    }
    public Address(){

    }
    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

}
