package com.hardcastle.datacollectorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String BASE_URL = "http://nigerianstudentshop.com/";
    private Button button_login;
    private EditText et_username;
    private EditText et_password;
    private String email;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        button_login = findViewById(R.id.btn_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        button_login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        if (et_username.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter UserName", Toast.LENGTH_SHORT).show();
        } else if (et_password.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        } else if (et_username.getText().toString().equals("admin") && et_password.getText().toString().equals("admin123")) {
           // loginProcessWithRetrofit(et_username.getText().toString(),et_password.getText().toString());
            /*Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
            startActivity(intent);*/

            Intent loginIntent = new Intent(LoginActivity.this, LocationActivity.class);
            loginIntent.putExtra("EMAIL", email);
            startActivity(loginIntent);
        } else {
            Toast.makeText(this, "Please Enter Valid UserName & Password", Toast.LENGTH_SHORT).show();

        }
    }

    public void onRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
        /*boolean mCancel = this.loginValidation();
        if (mCancel) {
           // focusView.requestFocus();
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        } else {
            registrationProcessWithRetrofit(et_username.getText().toString(),et_password.getText().toString());
        }*/
        
    }
    private boolean loginValidation() {
        // Reset errors.
        et_username.setError(null);
        et_password.setError(null);
        // Store values at the time of the login attempt.
        email = et_username.getText().toString();
        password = et_password.getText().toString();
        boolean cancel = false;
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
           /* mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;*/
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            /*mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;*/
            cancel = true;
        } else if (!isEmailValid(email)) {
           /* mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;*/
            cancel = true;
        }
        return cancel;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void loginProcessWithRetrofit(final String email, String password){
        ApiInterface mApiService = this.getInterfaceService();
        Call<Login> mService = mApiService.authenticate(email, password);
        mService.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login mLoginObject = response.body();
                String returnedResponse = mLoginObject.isLogin;
                Toast.makeText(LoginActivity.this, "Returned " + returnedResponse, Toast.LENGTH_LONG).show();
                //showProgress(false);
                if(returnedResponse.trim().equals("1")){
                    // redirect to Main Activity page
                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    loginIntent.putExtra("EMAIL", email);
                    startActivity(loginIntent);
                }
                if(returnedResponse.trim().equals("0")){
                    // use the registration button to register
                    /*failedLoginMessage.setText(getResources().getString(R.string.registration_message));
                    mPasswordView.requestFocus();*/
                    Toast.makeText(LoginActivity.this, "Please register  ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                call.cancel();
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


    private void registrationProcessWithRetrofit(final String email, String password){
        ApiInterface mApiService = this.getInterfaceService();
        Call<Login> mService = mApiService.registration(email, password);
        mService.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login mLoginObject = response.body();
                String returnedResponse = mLoginObject.isLogin;
                //showProgress(false);
                if(returnedResponse.trim().equals("1")){
                    // redirect to Main Activity page
                    Intent loginIntent = new Intent(LoginActivity.this, LocationActivity.class);
                    loginIntent.putExtra("EMAIL", email);
                    startActivity(loginIntent);
                }
                if(returnedResponse.trim().equals("0")){
                    // use the registration button to register
                    /*failedLoginMessage.setText(getResources().getString(R.string.registration_failed));
                    mPasswordView.requestFocus();*/
                    Toast.makeText(LoginActivity.this, "Registerd", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                call.cancel();
                Toast.makeText(LoginActivity.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }
}
