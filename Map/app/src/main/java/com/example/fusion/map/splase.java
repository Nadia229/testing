package com.example.fusion.map;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import gr.net.maroulis.library.EasySplashScreen;

public class splase extends AppCompatActivity {


    private static int Splase_time_out=1000;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splase);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                Intent splaseintent = new Intent(splase.this, MainActivity.class);
                startActivity(splaseintent);
                finish();
            }
        }, Splase_time_out);
    }
}
