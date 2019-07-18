package com.hardcastle.datacollectorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hardcastle.datacollectorapp.RetrofitBasics.RegisterResponse;
import com.hardcastle.datacollectorapp.RetrofitBasics.UserDTO;
import com.hardcastle.datacollectorapp.RetrofitBasics.UserDetails;
import com.hardcastle.datacollectorapp.RetrofitBasics.UserManager;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String BASE_URL = "http://hardcastle.co.in/PHP_WEB/datacollector/";
    private Button button_login;
    private EditText et_username;
    private EditText et_password;
    private String email;
    private String password;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        button_login = findViewById(R.id.btn_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        button_login.setOnClickListener(this);
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
    }
    @Override
    public void onClick(View v) {
        showProgressDialog();
        String emailValue=et_username.getText().toString();
        String passwordValue=et_password.getText().toString();

        if (et_username.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter UserName", Toast.LENGTH_SHORT).show();
        } else if (et_password.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        } else {
            //loginProcessWithRetrofit(et_username.getText().toString(),et_password.getText().toString());
            Call<UserDTO> call = UserManager.getUserManagerService(null).loginUser(emailValue, passwordValue);

            // Create a Callback object, because we do not set JSON converter, so the return object is ResponseBody be default.
            retrofit2.Callback<UserDTO> callback = new Callback<UserDTO>() {

                // When server response.
                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {

                    hideProgressDialog();

                    StringBuffer messageBuffer = new StringBuffer();
                    int statusCode = response.code();
                    Log.i("Prasann", statusCode + "");

                        // Get return string.
                        UserDTO returnBody = response.body();
                        Log.i("PrasannLogin", returnBody.getSTATUS() + "");

                        if (returnBody.getMESSAGE().equalsIgnoreCase("SUCCESS")) {
                            // messageBuffer.append(registerResponse.getMessage());
                            Toast.makeText(LoginActivity.this, "Successfully Logged", Toast.LENGTH_SHORT).show();

                            Intent loginIntent = new Intent(LoginActivity.this, LocationActivity.class);

                            UserDetails  userDetails=returnBody.getDATA().get(0);
                            loginIntent.putExtra("Data",userDetails);
                            startActivity(loginIntent);
                        }  else {
                            messageBuffer.append("User Login failed.");
                        }
                    }


                @Override
                public void onFailure(Call<UserDTO> call, Throwable t) {
                    hideProgressDialog();
                    call.cancel();
                    Log.i("uvsjd",t.getMessage());
                }
            };
            call.enqueue(callback);
        }


            /*Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
            startActivity(intent);*/

           /* Intent loginIntent = new Intent(LoginActivity.this, LocationActivity.class);
            loginIntent.putExtra("EMAIL", email);
            startActivity(loginIntent);*/
    }

                public void onRegister(View view) {
                    Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                    startActivity(intent);

                }





                private void loginProcessWithRetrofit(final String email, String password) {
                    ApiInterface mApiService = this.getInterfaceService();
                    Call<UserDTO> mService = mApiService.authenticate(email, password);
                    mService.enqueue(new Callback<UserDTO>() {
                        @Override
                        public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                            UserDTO mLoginObject = response.body();
                            String returnedResponse = mLoginObject.getSTATUS();

                            Log.i("PrasStatus",returnedResponse  );
                            Toast.makeText(LoginActivity.this, "Returned " + returnedResponse, Toast.LENGTH_LONG).show();
                            //showProgress(false);
                            if (returnedResponse.trim().equals("1")) {
                                // redirect to Main Activity page
                                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                                loginIntent.putExtra("EMAIL", email);
                                startActivity(loginIntent);
                            }

                        }

                        @Override
                        public void onFailure(Call<UserDTO> call, Throwable t) {
                            call.cancel();
                            Log.i("jeet",t.getMessage());
                            Toast.makeText(LoginActivity.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                private ApiInterface getInterfaceService() {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
                    return mInterfaceService;
                }


    /* Show progress dialog. */
    private void showProgressDialog()
    {
        // Set progress dialog display message.
        progressDialog.setMessage("Please Wait");

        // The progress dialog can not be cancelled.
        progressDialog.setCancelable(false);

        // Show it.
        progressDialog.show();
    }

    /* Hide progress dialog. */
    private void hideProgressDialog()
    {
        // Close it.
        progressDialog.hide();
    }

}
