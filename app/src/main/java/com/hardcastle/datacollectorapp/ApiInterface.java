package com.hardcastle.datacollectorapp;

import com.hardcastle.datacollectorapp.RetrofitBasics.UserDTO;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php/")
    Call<UserDTO> authenticate(@Path("email") String email, @Path("password") String password);

}