package com.morningessetial.morningessentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.morningessetial.morningessentials.Modal.Products;
import com.morningessetial.morningessentials.ViewHolder.ProductViewHolder;
import com.morningessetial.sohammorningessentials.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DisplayProd extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView back;
    EditText searchbar;
    String name,id,totalamt,total;
    Intent i;
    TextView title;
    int a,b,c,d;
    ImageView cart;
    SharedPreferences sharedPreferences;
    public static final String share = "share";
    public static final String idkey = "text8";
    RelativeLayout bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_prod);

        bar = findViewById(R.id.bar);

        back = findViewById(R.id.back);
        recyclerView= findViewById(R.id.recycle);
        title = findViewById(R.id.t1);
        cart = findViewById(R.id.cartbtn);

        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        searchbar = findViewById(R.id.searchbar);
        i = getIntent();
        name = i.getStringExtra("type");
        title.setText(name);

       searchbar.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               if(s.toString()!=null){
                   search(s.toString());
               }
               else{
                   search("");
               }
           }
       });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisplayProd.this,Cart.class);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sharedPreferences = getSharedPreferences(share, MODE_PRIVATE);
        id = sharedPreferences.getString(idkey,"");
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Total").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Products products = snapshot.getValue(Products.class);
                total = products.getTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference productsref= FirebaseDatabase.getInstance().getReference().child("Category").child(name);

        FirebaseRecyclerOptions<Products> options=
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(productsref, Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final Products model) {
                        String price = "\u20B9"+model.getPrice();
                        holder.name.setText(model.getName());
                        holder.price.setText(price);
                        holder.itemslist.setText(model.getItems());
                        holder.weight.setText(model.getWeight());
                        Glide.with(DisplayProd.this).load(model.getImage()).into(holder.img);

                        DatabaseReference ref10 = FirebaseDatabase.getInstance().getReference().child("OutOfStock").child(model.getName());
                        ref10.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(!(snapshot.exists())){
                                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Cart").child(id).child(model.getName());
                                    ref1.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists()){
                                                holder.addtocart.setVisibility(View.GONE);
                                                holder.aaa.setVisibility(View.VISIBLE);
                                            }

                                            else{
                                                holder.aaa.setVisibility(View.GONE);
                                                holder.addtocart.setVisibility(View.VISIBLE);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }

                                else{
                                    holder.addtocart.setVisibility(View.GONE);
                                    holder.aaa.setVisibility(View.VISIBLE);
                                    holder.aaa.setText("Out Of Stock");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bar.setVisibility(View.GONE);
                            }
                        },1000);

                        holder.aaa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bar.setVisibility(View.VISIBLE);
                                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                ref.child("Cart").child(id).child(model.getName()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()) {
                                            Products products = snapshot.getValue(Products.class);
                                            totalamt = String.valueOf(products.getTotal());
                                            d = Integer.valueOf(total) - Integer.valueOf(totalamt);
                                            HashMap<String, Object> hashMap1 = new HashMap<>();
                                            hashMap1.put("Total", String.valueOf(d));
                                            final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                            ref1.child("Total").child(id).updateChildren(hashMap1)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                ref1.child("Cart").child(id).child(model.getName()).removeValue();
                                                                ref1.child("Orderlist").child(id).child(model.getName()).removeValue();
                                                                bar.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    });
                                        }
                                        else {
                                            bar.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });


                        holder.addtocart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bar.setVisibility(View.VISIBLE);
                                a = Integer.valueOf(total);
                                b = Integer.valueOf(model.getPrice());
                                c = a + b;
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                HashMap<String,Object> hashMap1 = new HashMap<>();
                                hashMap1.put("Total",String.valueOf(c));
                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                ref1.child("Total").child(id).updateChildren(hashMap1);

                                HashMap<String,Object> hashMap2 = new HashMap<>();
                                hashMap2.put("Name",model.getName());
                                hashMap2.put("Weight",model.getWeight());
                                hashMap2.put("Quantity","1");
                                ref.child("Orderlist").child(id).child(model.getName()).updateChildren(hashMap2);

                                HashMap<String,Object> hashMap = new HashMap<>();
                                hashMap.put("Name",model.getName());
                                hashMap.put("Image",model.getImage());
                                hashMap.put("Price",model.getPrice());
                                hashMap.put("Weight",model.getWeight());
                                hashMap.put("Items",model.getItems());
                                hashMap.put("Quantity","1");
                                hashMap.put("Total",model.getPrice());
                                ref.child("Cart").child(id).child(model.getName()).updateChildren(hashMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    holder.addtocart.setVisibility(View.GONE);
                                                    holder.aaa.setVisibility(View.VISIBLE);
                                                    bar.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prodcardview,parent,false);
                        ProductViewHolder productViewHolder = new ProductViewHolder(view);
                        return productViewHolder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    private void search(String string){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Total").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Products products = snapshot.getValue(Products.class);
                total = products.getTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query query = FirebaseDatabase.getInstance().getReference().child("Category").child(name).orderByChild("Name").startAt(string).endAt(string + "\uf8ff");

        FirebaseRecyclerOptions<Products> options=
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(query, Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final Products model) {
                        String price = "\u20B9"+model.getPrice();
                        holder.name.setText(model.getName());
                        holder.price.setText(price);
                        holder.itemslist.setText(model.getItems());
                        holder.weight.setText(model.getWeight());
                        Glide.with(DisplayProd.this).load(model.getImage()).into(holder.img);

                        DatabaseReference ref10 = FirebaseDatabase.getInstance().getReference().child("OutOfStock").child(model.getName());
                        ref10.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(!(snapshot.exists())){
                                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Cart").child(id).child(model.getName());
                                    ref1.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists()){
                                                holder.addtocart.setVisibility(View.GONE);
                                                holder.aaa.setVisibility(View.VISIBLE);
                                            }

                                            else{
                                                holder.aaa.setVisibility(View.GONE);
                                                holder.addtocart.setVisibility(View.VISIBLE);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }

                                else{
                                    holder.addtocart.setVisibility(View.GONE);
                                    holder.aaa.setVisibility(View.VISIBLE);
                                    holder.aaa.setText("Out Of Stock");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bar.setVisibility(View.GONE);
                            }
                        },1000);

                        holder.aaa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bar.setVisibility(View.VISIBLE);
                                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                ref.child("Cart").child(id).child(model.getName()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()) {
                                            Products products = snapshot.getValue(Products.class);
                                            totalamt = String.valueOf(products.getTotal());
                                            d = Integer.valueOf(total) - Integer.valueOf(totalamt);
                                            HashMap<String, Object> hashMap1 = new HashMap<>();
                                            hashMap1.put("Total", String.valueOf(d));
                                            final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                            ref1.child("Total").child(id).updateChildren(hashMap1)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                ref1.child("Cart").child(id).child(model.getName()).removeValue();
                                                                ref1.child("Orderlist").child(id).child(model.getName()).removeValue();
                                                                bar.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    });
                                        }
                                        else {
                                            bar.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });


                        holder.addtocart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bar.setVisibility(View.VISIBLE);
                                a = Integer.valueOf(total);
                                b = Integer.valueOf(model.getPrice());
                                c = a + b;
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                HashMap<String,Object> hashMap1 = new HashMap<>();
                                hashMap1.put("Total",String.valueOf(c));
                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                ref1.child("Total").child(id).updateChildren(hashMap1);

                                HashMap<String,Object> hashMap2 = new HashMap<>();
                                hashMap2.put("Name",model.getName());
                                hashMap2.put("Weight",model.getWeight());
                                hashMap2.put("Quantity","1");
                                ref.child("Orderlist").child(id).child(model.getName()).updateChildren(hashMap2);

                                HashMap<String,Object> hashMap = new HashMap<>();
                                hashMap.put("Name",model.getName());
                                hashMap.put("Image",model.getImage());
                                hashMap.put("Price",model.getPrice());
                                hashMap.put("Weight",model.getWeight());
                                hashMap.put("Items",model.getItems());
                                hashMap.put("Quantity","1");
                                hashMap.put("Total",model.getPrice());
                                ref.child("Cart").child(id).child(model.getName()).updateChildren(hashMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    holder.addtocart.setVisibility(View.GONE);
                                                    holder.aaa.setVisibility(View.VISIBLE);
                                                    bar.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                            }
                        });

                    }
                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prodcardview,parent,false);
                        ProductViewHolder productViewHolder = new ProductViewHolder(view);
                        return productViewHolder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}
