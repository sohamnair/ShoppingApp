package com.morningessetial.morningessentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.morningessetial.morningessentials.Modal.Products;
import com.morningessetial.sohammorningessentials.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Dislikes extends AppCompatActivity implements PaymentResultListener {

    Spinner s1,s2,s3,s4,s5,tenure1;
    CheckBox fruits;
    Button continu;
    ArrayList<String> cat,cat1;
    int a,b,c;
    String string1,string2,string3,string4,string5,tenure,ss1,days15,days20,days30,fprice,name,phone,address,ids;
    private static final String TAG = checkoutpage.class.getSimpleName();
    ImageView back;

    SharedPreferences sharedPreferences;
    public static final String share = "share";
    public static final String namekey = "text2";
    public static final String phonekey = "text3";
    public static final String addresskey = "text1";
    public static final String idkey = "text8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dislikes);

        s1 = findViewById(R.id.spinner1);
        s2 = findViewById(R.id.spinner2);
        s3 = findViewById(R.id.spinner3);
        s4 = findViewById(R.id.spinner4);
        s5 = findViewById(R.id.spinner5);
        tenure1 = findViewById(R.id.tenure);
        fruits = findViewById(R.id.checbox);
        continu = findViewById(R.id.continu);
        back = findViewById(R.id.back);

        check();
        address();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cat= new ArrayList<>();
        cat.add("Dislike");cat.add("1");cat.add("2");cat.add("3");cat.add("4");cat.add("5");cat.add("Remove");
        s1.setAdapter(new ArrayAdapter<>(Dislikes.this,android.R.layout.simple_spinner_dropdown_item,cat));
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Dislike")){
                    string1 = "";

                }
                else {
                    string1 = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s2.setAdapter(new ArrayAdapter<>(Dislikes.this,android.R.layout.simple_spinner_dropdown_item,cat));
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Dislike")){
                    string2 = "";

                }
                else {
                    string2 = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s3.setAdapter(new ArrayAdapter<>(Dislikes.this,android.R.layout.simple_spinner_dropdown_item,cat));
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Dislike")){
                    string3 = "";

                }
                else {
                    string3 = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s4.setAdapter(new ArrayAdapter<>(Dislikes.this,android.R.layout.simple_spinner_dropdown_item,cat));
        s4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Dislike")){
                    string4 = "";

                }
                else {
                    string4 = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s5.setAdapter(new ArrayAdapter<>(Dislikes.this,android.R.layout.simple_spinner_dropdown_item,cat));
        s5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Dislike")){
                    string5 = "";

                }
                else {
                    string5 = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cat1= new ArrayList<>();
        cat1.add("Select");cat1.add("15");cat1.add("20");cat1.add("30");
        tenure1.setAdapter(new ArrayAdapter<>(Dislikes.this,android.R.layout.simple_spinner_dropdown_item,cat1));
        tenure1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenure = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tenure.equals("Select")){
                    Toast.makeText(Dislikes.this,"Select the tenure",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (fruits.isChecked()) {
                        if (tenure.equals("15")) {
                            b = Integer.valueOf(fprice);
                            c = Integer.valueOf(days15);
                            a = b + c;
                        }
                        else if (tenure.equals("20")) {
                            b = Integer.valueOf(fprice);
                            c = Integer.valueOf(days20);
                            a = b + c;
                        }
                        else {
                            b = Integer.valueOf(fprice);
                            c = Integer.valueOf(days30);
                            a = b + c;
                        }

                    }
                    else {
                        if (tenure.equals("15")) {
                            c = Integer.valueOf(days15);
                            a = c;
                        }
                        else if (tenure.equals("20")) {
                            c = Integer.valueOf(days20);
                            a =  c;
                        }
                        else {
                            c = Integer.valueOf(days30);
                            a =  c;
                        }

                    }
                    startPayment();
                }

            }
        });

    }


    private void check(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Price").child("Auto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Products products = snapshot.getValue(Products.class);
                days15 = products.getDays15();
                days20 = products.getDays20();
                days30 = products.getDays30();
                fprice = products.getFprice();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void startPayment() {


        /**
         * Reference to current activity
         */
        final Activity activity = this;
        Checkout checkout = new Checkout();

        checkout.setImage(R.drawable.ic_shopping_cart_black_24dp);

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "Morning Essentials");

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Buy Fresh Vegetables and Fruits");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", ""+a*100);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(Dislikes.this,"Successful",Toast.LENGTH_SHORT).show();
        upload();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Dislikes.this,"Error",Toast.LENGTH_SHORT).show();

    }

    private void address(){
        sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);

        name = sharedPreferences.getString(namekey,"");
        phone = sharedPreferences.getString(phonekey,"");
        address = sharedPreferences.getString(addresskey,"");
        ids = sharedPreferences.getString(idkey,"");
    }

    private void upload(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        if (string1.equals("")&&string2.equals("")&&string3.equals("")&&string4.equals("")&&string5.equals("")){
            ss1 = "";
        }
        else {
            ss1 = string1+","+string2+","+string3+","+string4+","+string5+",";
        }
        HashMap<String,Object> hashMap = new HashMap<>();
        if (ss1.equals("")){
            hashMap.put("Dislikes","No");
        }
        else {
            hashMap.put("Dislikes",ss1);
        }
        if (fruits.isChecked()){
            hashMap.put("Fruits","Yes");
        }
        else{
            hashMap.put("Fruits","No");
        }

        hashMap.put("Tenure",tenure);
        hashMap.put("Name",name);
        hashMap.put("Contact",phone);
        hashMap.put("Address",address);
        ref.child("Auto-Ordering").child(ids).updateChildren(hashMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(Dislikes.this,Thankyoupage.class);
                            startActivity(i);
                        }
                    }
                });
    }

}
