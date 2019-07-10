package com.hardcastle.datacollectorapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class PointDetailsModel implements Parcelable {

    LatLng latLng;
    String address,comment;

    public PointDetailsModel(LatLng latLng, String address, String comment) {
        this.latLng = latLng;
        this.address = address;
        this.comment = comment;
    }

    protected PointDetailsModel(Parcel in) {
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        address = in.readString();
        comment = in.readString();
    }

    public static final Creator<PointDetailsModel> CREATOR = new Creator<PointDetailsModel>() {
        @Override
        public PointDetailsModel createFromParcel(Parcel in) {
            return new PointDetailsModel(in);
        }

        @Override
        public PointDetailsModel[] newArray(int size) {
            return new PointDetailsModel[size];
        }
    };

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(latLng, flags);
        dest.writeString(address);
        dest.writeString(comment);
    }
}
