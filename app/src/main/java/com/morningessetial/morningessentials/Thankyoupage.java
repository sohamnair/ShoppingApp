package com.morningessetial.morningessentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.morningessetial.sohammorningessentials.R;

public class Thankyoupage extends AppCompatActivity {

    TextView name;
    SharedPreferences sharedPreferences;
    String sharename;

    public static final String share = "share";
    public static final String namekey = "text2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyoupage);

        name = findViewById(R.id.name);

        sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);
        sharename = sharedPreferences.getString(namekey,"");
        name.setText(sharename);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
