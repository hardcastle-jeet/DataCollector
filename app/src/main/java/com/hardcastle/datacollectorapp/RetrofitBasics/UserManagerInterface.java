package com.hardcastle.datacollectorapp.RetrofitBasics;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
public interface UserManagerInterface {

    /*
     * @FormUrlEncoded : Point out this method will construct a form submit action.
     * @POST : Point out the form submit will use post method, the form action url is the parameter of @POST annotation.
     * @Field("form_field_name") : Indicate the form filed name, the filed value will be assigned to input parameter userNameValue.
     * */
    @FormUrlEncoded
    @POST("register.php/")
    public Call<ResponseBody> registerUser(@Field("EMAIL_ID") String emailValue,
                                           @Field("USER_NAME") String userNameValue,
                                           @Field("PASSWORD") String passwordValue,
                                           @Field("USER_ADDRESS") String addressValue,
                                           @Field("USER_CONTACT_NO") String contactValue);


}
