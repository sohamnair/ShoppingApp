package com.morningessetial.morningessentials.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.morningessetial.morningessentials.Interface.ItemClickListener;
import com.morningessetial.sohammorningessentials.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView price,itemslist,name,weight,aaa;
    public ImageView img;
    public Button addtocart;
    public ItemClickListener itemClickListener;
    public Spinner s1;
    public CardView c1;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        price= itemView.findViewById(R.id.price);
        itemslist= itemView.findViewById(R.id.itemslist);
        name = itemView.findViewById(R.id.name);
        weight = itemView.findViewById(R.id.weight);
        img = itemView.findViewById(R.id.itemimage);
        addtocart = itemView.findViewById(R.id.addcart);
        s1 = itemView.findViewById(R.id.quantity);
        aaa = itemView.findViewById(R.id.aaa);
        c1 = itemView.findViewById(R.id.c1);

    }

    public void setItemClickListener(ItemClickListener listener){

        this.itemClickListener = listener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false);
    }
}
