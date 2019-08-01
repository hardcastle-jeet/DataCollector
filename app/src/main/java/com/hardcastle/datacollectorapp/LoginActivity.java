package com.hardcastle.datacollectorapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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

        if (!checkPermissions()){
            requestPermissions();
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
                            finish();
                        }  else {
                            Toast.makeText(LoginActivity.this, "User Login failed.", Toast.LENGTH_SHORT).show();                        }
                    }


                @Override
                public void onFailure(Call<UserDTO> call, Throwable t) {
                    hideProgressDialog();
                    call.cancel();
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            showSnackbar(11,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission

                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(11,
                        10, new View.OnClickListener() {
                            @Override

                            public void onClick(View view) {

                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }
}
