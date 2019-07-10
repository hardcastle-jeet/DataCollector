package com.hardcastle.datacollectorapp.RetrofitBasics;

public class RegisterResponse {

    private boolean success = false;

    private String message = "SUCCESS";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}