package com.hardcastle.datacollectorapp;

import com.hardcastle.datacollectorapp.RetrofitBasics.UserDetails;

import java.util.ArrayList;

public class PointsDTO {

    private String STATUS;

    private String MESSAGE;

    private ArrayList<PointDetailsModel> DATA;

    public PointsDTO(String STATUS, String MESSAGE, ArrayList<PointDetailsModel> DATA) {
        this.STATUS = STATUS;
        this.MESSAGE = MESSAGE;
        this.DATA = DATA;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public ArrayList<PointDetailsModel> getDATA() {
        return DATA;
    }

    public void setDATA(ArrayList<PointDetailsModel> DATA) {
        this.DATA = DATA;
    }
}
