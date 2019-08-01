package com.hardcastle.datacollectorapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    Context context;
    PointDetailsModel markerModel;
    public MarkerInfoWindowAdapter(Context context,PointDetailsModel markerModel) {
        this.context = context.getApplicationContext();
        this.markerModel=markerModel;
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker arg0) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.map_marker_info_window, null);

        LatLng latLng = arg0.getPosition();
        ImageView imageView=v.findViewById(R.id.img);
        TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
        TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
        TextView tvComment = (TextView) v.findViewById(R.id.tv_comments);

        tvLat.setText("Latitude:" + latLng.latitude);
        tvLng.setText("Longitude:"+ latLng.longitude);
        tvComment.setText("Comments="+markerModel.getCOMMENTS());
        String myUrl = markerModel.getUPLOAD_FILE();


        Glide.with(context)
                .load(myUrl)
                .into(imageView);
        return v;
    }
}