package com.morningessetial.morningessentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.morningessetial.sohammorningessentials.R;

public class SplashScreen extends AppCompatActivity {

    public static final String share = "share";
    SharedPreferences sharedPreferences;
    Boolean sharename;
    public static final String login1 = "text1001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);
        sharename = sharedPreferences.getBoolean(login1, false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharename){
                    Intent i = new Intent(SplashScreen.this,Home.class);
                    startActivity(i);
                }

                else{
                        Intent i = new Intent(SplashScreen.this, Register.class);
                        startActivity(i);

                }
            }
        },1600);

    }
}
