package com.hardcastle.datacollectorapp.RetrofitBasics;

import java.util.ArrayList;

public class UserDTO {


    private String STATUS;

    private String MESSAGE;

    private ArrayList<UserDetails> DATA;

    public UserDTO(String STATUS, String MESSAGE, ArrayList<UserDetails> DATA) {
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

    public ArrayList<UserDetails> getDATA() {
        return DATA;
    }

    public void setDATA(ArrayList<UserDetails> DATA) {
        this.DATA = DATA;
    }
}