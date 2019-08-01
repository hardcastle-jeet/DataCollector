package com.hardcastle.datacollectorapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class PointDetailsModel implements Parcelable {
    public PointDetailsModel(String ADDRESS, String LATITUDE, String LONGITUDE, String COMMENT, String IMAGE_URL, String USER_ID) {
        this.ADDRESS = ADDRESS;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.COMMENT = COMMENT;
        this.IMAGE_URL = IMAGE_URL;
        this.USER_ID = USER_ID;
    }

    public PointDetailsModel() {
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getCOMMENTS() {
        return COMMENT;
    }

    public void setCOMMENTS(String COMMENTS) {
        this.COMMENT = COMMENTS;
    }

    public String getUPLOAD_FILE() {
        return IMAGE_URL;
    }

    public void setUPLOAD_FILE(String UPLOAD_FILE) {
        this.IMAGE_URL = UPLOAD_FILE;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
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
    String ADDRESS, LATITUDE, LONGITUDE, COMMENT, IMAGE_URL, USER_ID;

    public PointDetailsModel(Parcel in) {


    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
