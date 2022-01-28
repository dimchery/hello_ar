package com.test_project.hello_arcore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.launch_main);
//        Log.d("TEST", "LOAD ....");
//
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
