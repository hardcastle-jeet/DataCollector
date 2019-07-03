package com.hardcastle.datacollectorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button button_login;
    private EditText et_username;
    private EditText et_password;

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

            /*Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
            startActivity(intent);*/
        } else {
            Toast.makeText(this, "Please Enter Valid UserName & Password", Toast.LENGTH_SHORT).show();

        }
    }

    public void onRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}
