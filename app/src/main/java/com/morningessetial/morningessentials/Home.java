package com.morningessetial.morningessentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morningessetial.morningessentials.Modal.Products;
import com.morningessetial.sohammorningessentials.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    Button viewrecipe;
    CardView recipecard,autoorder;
    ImageView fruits,vegetables,leafy,fcombo,vcombo,fvcombo;
    ImageButton cart,settings;
    TextView autordertxt,t103,t104,t105;
    String s = "1.0.0",msg,ver;
    RelativeLayout bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bar = findViewById(R.id.bar);
        viewrecipe = findViewById(R.id.viewrecipe);
        recipecard = findViewById(R.id.recipecard);
        fruits = findViewById(R.id.img1);
        vegetables = findViewById(R.id.img2);
        leafy = findViewById(R.id.herb);
        cart = findViewById(R.id.cart);
        fcombo = findViewById(R.id.img16);
        vcombo = findViewById(R.id.img17);
        fvcombo = findViewById(R.id.img18);
        autoorder = findViewById(R.id.autoorder);
        autordertxt = findViewById(R.id.a);
        settings = findViewById(R.id.settings);
        t103 = findViewById(R.id.t103);
        t104 = findViewById(R.id.t104);
        t105 = findViewById(R.id.t105);

        version();

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Cart.class);
                startActivity(i);
            }
        });

        viewrecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,DisplayProd.class);
                i.putExtra("type","Recipe");
                startActivity(i);
            }
        });

        recipecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,DisplayProd.class);
                i.putExtra("type","Recipe");
                startActivity(i);
            }
        });

        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,DisplayProd.class);
                i.putExtra("type","Fruits");
                startActivity(i);
            }
        });

        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,DisplayProd.class);
                i.putExtra("type","Vegetables");
                startActivity(i);
            }
        });

        leafy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,DisplayProd.class);
                i.putExtra("type","Leafy");
                startActivity(i);
            }
        });

        fcombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,DisplayProd.class);
                i.putExtra("type","Fcombo");
                startActivity(i);
            }
        });

        vcombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,DisplayProd.class);
                i.putExtra("type","Vcombo");
                startActivity(i);
            }
        });

        fvcombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,DisplayProd.class);
                i.putExtra("type","FVcombo");
                startActivity(i);
            }
        });

        autordertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,autoorder.class);
                startActivity(i);
            }
        });

        autoorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,autoorder.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Settings.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void version(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Price").child("Version").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Products products = snapshot.getValue(Products.class);
                    ver = products.getVersion();
                    msg = products.getMsg();
                    if(!(ver.equals(s))){
                        alertbox(msg);
                        bar.setVisibility(View.GONE);
                    }
                    else{
                        bar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void alertbox(final String msg){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("App Update Available");
        alert.setMessage(msg);
        alert.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.setCancelable(true);
            }
        });
        alert.create().show();
    }


}
