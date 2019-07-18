package com.hardcastle.datacollectorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hardcastle.datacollectorapp.RetrofitBasics.RegisterResponse;
import com.hardcastle.datacollectorapp.RetrofitBasics.UserManager;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    private EditText et_name, et_email, et_contact, et_password, et_confirm_password,et_address;
    private String BASE_URL_Registration = "http://hardcastle.co.in/PHP_WEB/datacollector/";
    private ProgressDialog progressDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();

        et_name = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_contact = findViewById(R.id.et_contact_no);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        et_address=findViewById(R.id.et_address);
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
    }

    public void onRegister(View view) {
        if (et_name.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter UserName", Toast.LENGTH_SHORT).show();
            return;
        } else if (et_email.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter email", Toast.LENGTH_SHORT).show();
            return;
        } else if (et_contact.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
            return;
        }else if (et_address.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter address", Toast.LENGTH_SHORT).show();
            return;
        }  else if (et_password.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_confirm_password.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please confirm password", Toast.LENGTH_SHORT).show();
            return;
        } else if (!et_confirm_password.getText().toString().equals(et_password.getText().toString())) {
            Toast.makeText(this, "Password should be same", Toast.LENGTH_SHORT).show();
            return;

        } else {
            /*Toast.makeText(this, "Registerd successfully", Toast.LENGTH_SHORT).show();
            finish();
*/


            // registrationProcessWithRetrofit(et_email.getText().toString(), et_password.getText().toString());
            showProgressDialog();

            String userNameValue = et_name.getText().toString();

            String passwordValue = et_password.getText().toString();

            String emailValue = et_email.getText().toString();

            String addressValue=et_address.getText().toString();
            String contactValue=et_contact.getText().toString();
            String adminIdValue="";
            if (getIntent().getStringExtra("from")!=null){
                adminIdValue=getIntent().getStringExtra("from");
            }

            // Use default converter factory, so parse response body text to okhttp3.ResponseBody object.
            Call<ResponseBody> call = UserManager.getUserManagerService(null).registerUser(emailValue,userNameValue, passwordValue,addressValue,contactValue,adminIdValue);

            // Create a Callback object, because we do not set JSON converter, so the return object is ResponseBody be default.
            retrofit2.Callback<ResponseBody> callback = new Callback<ResponseBody>() {

                /* When server response. */
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    hideProgressDialog();

                    StringBuffer messageBuffer = new StringBuffer();
                    int statusCode = response.code();
                    Log.i("Prasann",statusCode+"");
                    if (statusCode == 200) {
                        try {
                            // Get return string.
                            String returnBodyText = response.body().string();
                            Log.i("Prasann ",returnBodyText+"");

                            if (returnBodyText.equalsIgnoreCase("SUCCESS")) {
                                // messageBuffer.append(registerResponse.getMessage());
                                Toast.makeText(RegistrationActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (returnBodyText.equalsIgnoreCase("ALREADY EXISTS")){

                                Toast.makeText(RegistrationActivity.this, "User already exist", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                messageBuffer.append("User register failed.");
                            }


                            // Because return text is a json format string, so we should parse it manually.
                            /*Gson gson = new Gson();

                            TypeToken<List<RegisterResponse>> typeToken = new TypeToken<List<RegisterResponse>>() {
                            };*/

                            // Get the response data list from JSON string.
                           /* List<RegisterResponse> registerResponseList = gson.fromJson(returnBodyText, typeToken.getType());

                            if (registerResponseList != null && registerResponseList.size() > 0) {

                                RegisterResponse registerResponse = registerResponseList.get(0);

                                if (registerResponse.isSuccess()) {
                                   // messageBuffer.append(registerResponse.getMessage());
                                    Toast.makeText(RegistrationActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    messageBuffer.append("User register failed.");
                                }
                            }*/
                        } catch (IOException ex) {
                            Log.e("TAG_RETROFIT_GET_POST", ex.getMessage());
                        }
                    }/* else {
                        // If server return error.
                        messageBuffer.append("Server return error code is ");
                        messageBuffer.append(statusCode);
                        messageBuffer.append("\r\n\r\n");
                        messageBuffer.append("Error message is ");
                        messageBuffer.append(response.message());
                    }*/

                    // Show a Toast message.
                   // Toast.makeText(getApplicationContext(), messageBuffer.toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    hideProgressDialog();

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            };

            // Use callback object to process web server return data in asynchronous mode.
            call.enqueue(callback);
        }
    }


   /* private void registrationProcessWithRetrofit(final String email, String password) {
        ApiInterface mApiService = this.getInterfaceService();
        Call<Login> mService = mApiService.registration(email, password);
        mService.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login mLoginObject = response.body();
                String returnedResponse = mLoginObject.isLogin;
                //showProgress(false);
                if (returnedResponse.trim().equals("1")) {
                    // redirect to Main Activity page
                    Intent loginIntent = new Intent(RegistrationActivity.this, LocationActivity.class);
                    loginIntent.putExtra("EMAIL", email);
                    startActivity(loginIntent);
                }
                if (returnedResponse.trim().equals("0")) {
                    // use the registration button to register
                    *//*failedLoginMessage.setText(getResources().getString(R.string.registration_failed));
                    mPasswordView.requestFocus();*//*
                    Toast.makeText(RegistrationActivity.this, "Registerd", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                call.cancel();
                Toast.makeText(RegistrationActivity.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }

    private ApiInterface getInterfaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_Registration)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }*/

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

