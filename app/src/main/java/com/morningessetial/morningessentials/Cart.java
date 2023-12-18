package com.morningessetial.morningessentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.morningessetial.morningessentials.Modal.Products;
import com.morningessetial.morningessentials.ViewHolder.CartViewHolder;
import com.morningessetial.sohammorningessentials.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart extends AppCompatActivity {

    RelativeLayout bar;
    private RecyclerView recyclerView1;
    Button checkout;
    DatabaseReference rootref;
    RecyclerView.LayoutManager layoutManager;
    int a,b,c,price1,quan1,totalp,totalp1,a1;
    ArrayList<String> cat;
    String str1,aaa,total,ids;
    TextView totalprice;
    int val=0,z;
    ImageButton home,settings;
    SharedPreferences sharedPreferences;
    public static final String share = "share";
    public static final String idkey = "text8";
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checkout = findViewById(R.id.checkout);
        recyclerView1= findViewById(R.id.recycle);
        home = findViewById(R.id.home1);
        settings = findViewById(R.id.settings);
        totalprice = findViewById(R.id.totalprice);
        msg = findViewById(R.id.msg);
        bar = findViewById(R.id.bar);
        recyclerView1.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);

        cat= new ArrayList<>();
        cat.add("Quantity");cat.add("1");cat.add("2");cat.add("3");cat.add("4");cat.add("5");cat.add("Remove");
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cart.this,Settings.class);
                startActivity(i);
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cart.this,checkoutpage.class);
                if (quan1 == 0 ){
                    aaa = ""+total;
                    i.putExtra("value",aaa);
                }
                else{
                    aaa = ""+quan1;
                    i.putExtra("value",aaa);
                }
                startActivity(i);
            }
        });

        sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);
        ids = sharedPreferences.getString(idkey,"");
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Total").child(ids).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Products products = snapshot.getValue(Products.class);
                total = products.getTotal();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        rootref = FirebaseDatabase.getInstance().getReference().child("Cart").child(ids);

        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    msg.setVisibility(View.GONE);
                    checkout.setVisibility(View.VISIBLE);

                    FirebaseRecyclerOptions<Products> options =
                            new FirebaseRecyclerOptions.Builder<Products>()
                                    .setQuery(rootref, Products.class).build();

                    FirebaseRecyclerAdapter<Products, CartViewHolder> adapter =
                            new FirebaseRecyclerAdapter<Products, CartViewHolder>(options) {
                                @Override
                                protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Products model) {
                                    String s1 = "\u20B9"+model.getPrice()+" x "+model.getQuantity()+" = \u20B9"+model.getTotal();
                                    holder.name.setText(model.getName());
                                    holder.price.setText(s1);
                                    holder.itemslist.setText(model.getItems());
                                    holder.weight.setText( model.getWeight());
                                    Glide.with(Cart.this).load(model.getImage()).into(holder.img);
                                    a = Integer.valueOf(model.getTotal());

                                    final DatabaseReference ref10 = FirebaseDatabase.getInstance().getReference();
                                    ref10.child("OutOfStock").child(model.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists()){
                                                int a = Integer.valueOf(model.getTotal());
                                                int b = Integer.valueOf(total);
                                                b = b - a;
                                                HashMap<String,Object> hashMap = new HashMap<>();
                                                hashMap.put("Total",String.valueOf(b));
                                                ref10.child("Total").child(ids).updateChildren(hashMap);
                                                ref10.child("Cart").child(ids).child(model.getName()).removeValue();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                    holder.quantity.setAdapter(new ArrayAdapter<>(Cart.this,android.R.layout.simple_spinner_dropdown_item,cat));
                                    holder.quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if((parent.getItemAtPosition(position).equals("Quantity"))){

                                            }
                                            else if (parent.getItemAtPosition(position).equals("Remove")){
                                                bar.setVisibility(View.VISIBLE);
                                                totalp1 = Integer.valueOf(model.getTotal());
                                                a1 = Integer.valueOf(total);
                                                val = a1 - totalp1;

                                                b = Integer.valueOf(model.getPrice());
                                                z = 0;
                                                price1 = b * z;

                                                quan1 = val + price1;
                                                HashMap<String,Object> hashMap1 = new HashMap<>();
                                                hashMap1.put("Total",String.valueOf(quan1));
                                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                                ref1.child("Cart").child(ids).child(model.getName()).removeValue();
                                                ref1.child("Orderlist").child(ids).child(model.getName()).removeValue();
                                                ref1.child("Total").child(ids).updateChildren(hashMap1)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    bar.setVisibility(View.GONE);
                                                                }
                                                            }
                                                        });
                                            }
                                            else {
                                                bar.setVisibility(View.VISIBLE);
                                                str1 = parent.getItemAtPosition(position).toString();
                                                c = Integer.valueOf(str1);
                                                b = Integer.valueOf(model.getPrice());
                                                price1 = b * c;
                                                totalp = Integer.valueOf(model.getTotal());
                                                a1 = Integer.valueOf(total);
                                                val = a1 - totalp;
                                                quan1 = val + price1;

                                                HashMap<String,Object> hashMap1 = new HashMap<>();
                                                hashMap1.put("Total",String.valueOf(quan1));
                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                ref.child("Total").child(ids).updateChildren(hashMap1);

                                                HashMap<String,Object> hashMap = new HashMap<>();
                                                String bbb = String.valueOf(c);
                                                String ccc = String.valueOf(price1);
                                                hashMap.put("Quantity",bbb);
                                                hashMap.put("Total",ccc);
                                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                                ref1.child("Cart").child(ids).child(model.getName()).updateChildren(hashMap);
                                                HashMap<String,Object> hashMap2 = new HashMap<>();
                                                hashMap2.put("Quantity",bbb);
                                                ref1.child("Orderlist").child(ids).child(model.getName()).updateChildren(hashMap2)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    bar.setVisibility(View.GONE);
                                                                }
                                                            }
                                                        });
                                            }
                                            if (quan1 == 0 ){
                                                String bbb;
                                                aaa = ""+total;
                                                bbb = "\u20B9"+aaa;
                                                totalprice.setText(bbb);
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        bar.setVisibility(View.GONE);
                                                    }
                                                },1000);

                                            }
                                            else{
                                                String bbb;
                                                aaa = ""+quan1;
                                                bbb = "\u20B9"+aaa;
                                                totalprice.setText(bbb);
                                                bar.setVisibility(View.GONE);
                                                App.refreshApp(Cart.this);

                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                }

                                @NonNull
                                @Override
                                public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartcardview,parent,false);
                                    CartViewHolder productViewHolder = new CartViewHolder(view);
                                    return productViewHolder;
                                }
                            };
                    recyclerView1.setAdapter(adapter);
                    adapter.startListening();
                }
                else {
                    msg.setVisibility(View.VISIBLE);
                    checkout.setVisibility(View.GONE);
                    totalprice.setText("\u20B90");
                    bar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
