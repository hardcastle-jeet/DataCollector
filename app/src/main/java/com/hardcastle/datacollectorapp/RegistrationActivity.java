package com.hardcastle.datacollectorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText et_name,et_email,et_contact,et_password,et_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();

        et_name=findViewById(R.id.et_username);
        et_email=findViewById(R.id.et_email);
        et_contact=findViewById(R.id.et_contact_no);
        et_password=findViewById(R.id.et_password);
        et_confirm_password=findViewById(R.id.et_confirm_password);
    }

    public void onRegister(View view) {
        if (et_name.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter UserName", Toast.LENGTH_SHORT).show();
            return;
        } else if (et_email.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter email", Toast.LENGTH_SHORT).show();
            return;
        }else  if (et_contact.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
            return;
        } else if (et_password.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        } if (et_confirm_password.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please confirm password", Toast.LENGTH_SHORT).show();
            return;
        }else if (!et_confirm_password.getText().toString().equals(et_password.getText().toString())){
            Toast.makeText(this, "Password should be same", Toast.LENGTH_SHORT).show();
            return;

        }else{
            Toast.makeText(this, "Registerd successfully", Toast.LENGTH_SHORT).show();
            finish();

        }
    }
}
