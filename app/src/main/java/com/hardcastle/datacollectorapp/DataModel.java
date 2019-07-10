package com.hardcastle.mapofflinedemo.Model;


import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

public class DataModel implements Parcelable, Comparator<DataModel> {
    public static final Parcelable.Creator<DataModel> CREATOR = new Parcelable.Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel source) {
            return new DataModel(source);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };
    private LatLng[] points = null;
    private Point screenPoint[] = null;
    private int count;
    private String drawType;
    private String tag;

    public DataModel(Parcel in) {
        this.points = in.createTypedArray(LatLng.CREATOR);
        this.screenPoint = in.createTypedArray(Point.CREATOR);
        this.count = in.readInt();
        this.drawType = in.readString();
        this.tag = in.readString();
    }

    public DataModel() {

    }

    public Point[] getScreenPoint() {
        return screenPoint;
    }

    public void setScreenPoint(Point[] screenPoint) {
        this.screenPoint = screenPoint;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDrawType() {
        return drawType;
    }

    public void setDrawType(String drawType) {
        this.drawType = drawType;
    }

    public LatLng[] getPoints() {
        return points;
    }

    public void setPoints(LatLng[] points) {
        this.points = points;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.points, flags);
        dest.writeTypedArray(this.screenPoint, flags);
        dest.writeInt(this.count);
        dest.writeString(this.drawType);
        dest.writeString(this.tag);
    }

    @Override
    public int compare(DataModel o1, DataModel o2) {
        return o1.getTag().compareToIgnoreCase(o2.getTag());
    }
}
