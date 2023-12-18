package com.morningessetial.morningessentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.morningessetial.sohammorningessentials.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText name1,number1,building1,area1,locality1,pincode1;
    String name,number,building,area,locality,pincode,address;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button reg;
    public static final String share = "share";
    public static final String addresskey = "text1";
    public static final String namekey = "text2";
    public static final String phonekey = "text3";
    public static final String buildingkey = "text4";
    public static final String areakey = "text5";
    public static final String localitykey = "text6";
    public static final String pincodekey = "text7";
    public static final String idkey = "text8";
    public static final String login1 = "text1001";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name1 = findViewById(R.id.name);
        number1 = findViewById(R.id.contact);
        building1 = findViewById(R.id.building);
        area1 = findViewById(R.id.area);
        locality1 = findViewById(R.id.locality);
        pincode1 = findViewById(R.id.pincode);
        reg = findViewById(R.id.reg);
        alertbox();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

    }

    private void upload(){

        name = name1.getText().toString();
        number = number1.getText().toString();
        building = building1.getText().toString();
        area = area1.getText().toString();
        locality = locality1.getText().toString();
        pincode = pincode1.getText().toString();

        if (name.isEmpty()||number.isEmpty()||building.isEmpty()||area.isEmpty()||locality.isEmpty()||pincode.isEmpty()){
            Toast.makeText(Register.this,"Profile Incomplete",Toast.LENGTH_SHORT).show();
        }
        else {
            address = ""+building+", "+area+", "+locality+", "+pincode+"";
            sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(namekey,name);
            editor.putString(phonekey,number);
            editor.putString(idkey,number);
            editor.putString(addresskey,address);
            editor.putString(buildingkey,building);
            editor.putString(areakey,area);
            editor.putString(localitykey,locality);
            editor.putString(pincodekey,pincode);
            editor.apply();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("Total","0");
            ref.child("Total").child(number).updateChildren(hashMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            editor.putBoolean(login1,true);
                            editor.apply();
                            Intent i = new Intent(Register.this,Home.class);
                            startActivity(i);
                        }
                    });
        }

    }

    private void alertbox(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("User Information");
        alert.setMessage("We require three user details: Name, Contact number & Address. Address is required " +
                "so that we can deliver the product you ordered at your door step. Name & Contact number is required " +
                "if in case we need to contact you during the delivery process. Your data is safe & securely transmitted using Security Socket Layer (SSL). " +
                "Continue with the registration process if you are comfortable in sharing your Name, Contact number & Address with us.");
        alert.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.setCancelable(true);
            }
        });
        alert.create().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
