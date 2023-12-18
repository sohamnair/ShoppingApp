package com.morningessetial.morningessentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class checkoutpage extends AppCompatActivity implements PaymentResultListener {

    private String s1,address,name,phone,mindelivery,mindiscount,discountval,deliveryfee,disvalue,status,ids;
    private Intent i;
    int mindel,mindis,delfee,discountvalue;
    double a,b,disval,finalamt,finalamt1;
    TextView delivery,carttol,cashbackval,finalamount,address1;
    Button payment,cod;
    ImageView back;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String share = "share";
    public static final String addresskey = "text1";
    public static final String namekey = "text2";
    public static final String phonekey = "text3";
    public static final String idkey = "text8";
    private static final String TAG = checkoutpage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoutpage);

        delivery = findViewById(R.id.deliv);
        carttol = findViewById(R.id.balance);
        cashbackval = findViewById(R.id.cashbackval);
        finalamount = findViewById(R.id.finalamount);
        back = findViewById(R.id.back);
        payment = findViewById(R.id.payment);
        address1 = findViewById(R.id.address);
        cod = findViewById(R.id.cod);

        check();

        i = getIntent();
        s1 = i.getStringExtra("value");

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "Cash On Delivery";
                uploaddata();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        address();
    }

    public void startPayment() {

        sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);

        /**
         * Reference to current activity
         */
        final Activity activity = this;
        Checkout checkout = new Checkout();

        checkout.setImage(R.drawable.ic_shopping_cart_black_24dp);

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        finalamt1 = sharedPreferences.getInt("finalamt",0);
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
            options.put("amount", ""+finalamt1);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        status = "Paid";
        uploaddata();
    }

    @Override
    public void onPaymentError(int i, String s) {
        alertbox10();
    }

    private void address(){
        sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);

        address = sharedPreferences.getString(addresskey,"");
        name = sharedPreferences.getString(namekey,"");
        phone = sharedPreferences.getString(phonekey,"");
        ids = sharedPreferences.getString(idkey,"");
        address1.setText(address);

    }

    private void check(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Price").child("Checkout").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Products products = snapshot.getValue(Products.class);
                    discountval = products.getDiscount();
                    deliveryfee = products.getDeliveryFee();
                    mindelivery = products.getMinDeliveryVal();
                    mindiscount = products.getMinDiscountVal();
                    disvalue = products.getDiscountVal();

                    mindel = Integer.valueOf(mindelivery);
                    mindis = Integer.valueOf(mindiscount);
                    delfee = Integer.valueOf(deliveryfee);
                    disval = Double.valueOf(discountval);
                    discountvalue = Integer.valueOf(disvalue);
                    aaa(s1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void aaa(String s1){

        String tol = "\u20B9"+s1;
        carttol.setText(tol);
        a = Double.valueOf(s1);

        if (a < mindel){
            String s2 = "+\u20B9"+deliveryfee;
            delivery.setText(s2);
            a = a + delfee;
            String s3 = "\u20B90";
            cashbackval.setText(s3);
            alertbox1();
        }
        else {
            String s5 = "Free";
            delivery.setText(s5);

            if (a < mindis) {
                String s2 = "\u20B90";
                cashbackval.setText(s2);
                alertbox();
            }
            else {
                b = a * disval;
                String s = String.format("%.1f",b);
                String s2 = "-\u20B9"+s;
                cashbackval.setText(s2);
                a = a - b;
            }
        }

        finalamt = a * 100;
        sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("finalamt", (int) finalamt);
        editor.apply();
        String t = "\u20B9"+a;
        finalamount.setText(t);
    }

    private void uploaddata(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("ddHHmmss");
        final String time = format.format(calendar.getTime());
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Contact",phone);
        hashMap.put("Name",name);
        hashMap.put("Status",status);
        hashMap.put("Total",String.valueOf(a));
        hashMap.put("Address",address);
        hashMap.put("Date",time);
        hashMap.put("Id",ids);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Order").child("Orders").child(ids).updateChildren(hashMap);

        ref.child("Cart").child(ids).removeValue();
        HashMap<String,Object> hashMap1 = new HashMap<>();
        hashMap1.put("Total","0");
        ref.child("Total").child(ids).updateChildren(hashMap1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(checkoutpage.this,Thankyoupage.class);
                        startActivity(i);
                    }
                });
    }

    private void alertbox1(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Shop for \u20B9"+mindel+" & more and get FREE delivery");
        alert.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.setCancelable(true);
            }
        });
        alert.create().show();
    }

    private void alertbox(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Shop for \u20B9"+mindis+" & more and get min. \u20B9"+discountvalue+" off");
        alert.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.setCancelable(true);
            }
        });
        alert.create().show();
    }

    private void alertbox10(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Transaction failed, Try again...");
        alert.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.setCancelable(true);
            }
        });
        alert.create().show();
    }
}
