package com.morningessetial.morningessentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.morningessetial.sohammorningessentials.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {

    EditText name1,number1,building1,area1,locality1,pincode1;
    Button update;
    ImageView finish;
    String name,number,building,area,locality,pincode,address,sharename,sharephone,sharebuild,sharearea,sharelocality,sharepin,shareadd,ids;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView signout;

    public static final String share = "share";
    public static final String addresskey = "text1";
    public static final String namekey = "text2";
    public static final String phonekey = "text3";
    public static final String buildingkey = "text4";
    public static final String areakey = "text5";
    public static final String localitykey = "text6";
    public static final String pincodekey = "text7";
    public static final String login1 = "text1001";
    public static final String idkey = "text8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name1 = findViewById(R.id.name);
        number1 = findViewById(R.id.contact);
        building1 = findViewById(R.id.building);
        area1 = findViewById(R.id.area);
        locality1 = findViewById(R.id.locality);
        pincode1 = findViewById(R.id.pincode);
        update = findViewById(R.id.update);
        finish = findViewById(R.id.finish);
        signout = findViewById(R.id.signout);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aaa();
            }
        });

        bbb();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               delete();
            }
        });
    }

    private void aaa(){
        name = name1.getText().toString();
        number = number1.getText().toString();
        building = building1.getText().toString();
        area = area1.getText().toString();
        locality = locality1.getText().toString();
        pincode = pincode1.getText().toString();
        if (name.isEmpty()||number.isEmpty()||building.isEmpty()||area.isEmpty()||locality.isEmpty()||pincode.isEmpty()){
            Toast.makeText(Profile.this,"Profile Incomplete",Toast.LENGTH_SHORT).show();
        }
        else {
            address = ""+building+", "+area+", "+locality+", "+pincode+"";
            sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(namekey,name);
            editor.putString(phonekey,number);
            editor.putString(addresskey,address);
            editor.putString(buildingkey,building);
            editor.putString(areakey,area);
            editor.putString(localitykey,locality);
            editor.putString(pincodekey,pincode);
            editor.apply();
            finish();
        }

    }

    private void bbb(){

        sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);
        sharename = sharedPreferences.getString(namekey,"");
        sharephone = sharedPreferences.getString(phonekey,"");
        shareadd = sharedPreferences.getString(addresskey,"");
        sharebuild = sharedPreferences.getString(buildingkey,"");
        sharearea = sharedPreferences.getString(areakey,"");
        sharelocality = sharedPreferences.getString(localitykey,"");
        sharepin = sharedPreferences.getString(pincodekey,"");
        ids = sharedPreferences.getString(idkey,"");

        name1.setText(sharename);
        number1.setText(sharephone);
        building1.setText(sharebuild);
        area1.setText(sharearea);
        locality1.setText(sharelocality);
        pincode1.setText(sharepin);

    }

    private void delete(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Cart").child(ids).removeValue();
        ref.child("Total").child(ids).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putBoolean(login1,false);
                            editor.apply();
                            Intent i = new Intent(Profile.this,Register.class);
                            startActivity(i);
                        }
                    }
                });
    }

}
