package com.example.projectgdsync;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 3000;                 //Timeout duration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);        //Cover entire screen

        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               // Intent i = new Intent(MainActivity.this,LoginRegistrationActivity.class);  //Intent is used to switch from one activity to another.

                Intent i = new Intent(MainActivity.this,LoginActivity.class);

                startActivity(i);        //Invoke the LoginRegistrationActivity.

                finish();                //The current activity will get finished.

            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}