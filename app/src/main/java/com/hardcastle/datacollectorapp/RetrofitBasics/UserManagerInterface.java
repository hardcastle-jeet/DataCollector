package com.hardcastle.datacollectorapp.RetrofitBasics;


import com.hardcastle.datacollectorapp.Login;
import com.hardcastle.datacollectorapp.PointDetailsModel;
import com.hardcastle.datacollectorapp.PointsDTO;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
                                           @Field("USER_CONTACT_NO") String contactValue,
                                           @Field("ADMIN_ID") String adminIdValue);




    @FormUrlEncoded
    @POST("login.php/")
    public Call<UserDTO> loginUser(@Field("USERNAME") String emailValue,
                                        @Field("PASSWORD") String passwordValue);

   /* @GET("login.php/{email}/{password}")
    Call<UserDTO> loginUser(@Path("email") String email, @Path("password") String password);
*/

    @FormUrlEncoded
    @POST("get_point_details.php/")
    public Call<PointsDTO> getPoints(@Field("ADMIN_ID") String userIdValue);

    @FormUrlEncoded
    @POST("add_point_details.php/")
    public Call<ResponseBody> AddPoint(@Field("ADDRESS") String addressValue,
                                      @Field("LATITUDE") String latValue,
                                      @Field("LONGITUDE") String longValue,
                                      @Field("COMMENTS") String commentValue,
                                      @Field("UPLOAD_FILE")  String fileUploadvalue,
                                      @Field("USER_ID") String userIdValue);
}
