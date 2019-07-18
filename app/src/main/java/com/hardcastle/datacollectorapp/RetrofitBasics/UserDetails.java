package com.hardcastle.datacollectorapp.RetrofitBasics;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDetails implements Parcelable {

    String USER_ID,NAME,EMAIL,MOBILE,ADDRESS,ADMIN_ID;


    protected UserDetails(Parcel in) {
        USER_ID = in.readString();
        NAME = in.readString();
        EMAIL = in.readString();
        MOBILE = in.readString();
        ADDRESS = in.readString();
        ADMIN_ID = in.readString();
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getADMIN_ID() {
        return ADMIN_ID;
    }

    public void setADMIN_ID(String ADMIN_ID) {
        this.ADMIN_ID = ADMIN_ID;
    }

    public UserDetails(String USER_ID, String NAME, String EMAIL, String MOBILE, String ADDRESS, String ADMIN_ID) {
        this.USER_ID = USER_ID;
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.MOBILE = MOBILE;
        this.ADDRESS = ADDRESS;
        this.ADMIN_ID = ADMIN_ID;
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(USER_ID);
        dest.writeString(NAME);
        dest.writeString(EMAIL);
        dest.writeString(MOBILE);
        dest.writeString(ADDRESS);
        dest.writeString(ADMIN_ID);
    }
}
