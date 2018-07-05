package com.example.sanukumar.billsplit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    final int DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Main Activity is the splash screen of the application

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent appMainActivity = new Intent(MainActivity.this, HomeScreenActivityActivity.class);
            }
        }, DELAY);


    }
}
