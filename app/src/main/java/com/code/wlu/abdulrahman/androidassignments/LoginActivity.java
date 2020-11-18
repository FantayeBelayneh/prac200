package com.code.wlu.abdulrahman.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    protected static final String SHARED_PREFS = "";

    protected TextView  txtView_EmailAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreated()");
        uploadSharedPreferenceValue();
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferenceValue();
                Intent intent = new Intent (LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    public void getSharedPreferenceValue()
    {
        txtView_EmailAddress = (TextView) findViewById(R.id.txtLoginID);
        SharedPreferences emailAddress = getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor editor = emailAddress.edit();
        editor.putString(SHARED_PREFS, txtView_EmailAddress.getText().toString());
        editor.apply();
    }
    public void uploadSharedPreferenceValue()
    {
        txtView_EmailAddress = (TextView) findViewById(R.id.txtLoginID);
        SharedPreferences settings = getSharedPreferences(SHARED_PREFS, 0);
        String reCapturedText = settings.getString(SHARED_PREFS, "email@domain.com");
        txtView_EmailAddress.setText(reCapturedText);

    }
}