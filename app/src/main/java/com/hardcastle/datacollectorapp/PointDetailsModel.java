package com.hardcastle.datacollectorapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class PointDetailsModel implements Parcelable {


    String address,latitude,longitude,comment,file,userId;


    public PointDetailsModel(String address, String latitude, String longitude, String comment, String file, String userId) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.comment = comment;
        this.file = file;
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    protected PointDetailsModel(Parcel in) {
        address = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        comment = in.readString();
        file = in.readString();
        userId = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(comment);
        dest.writeString(file);
        dest.writeString(userId);
    }
}
