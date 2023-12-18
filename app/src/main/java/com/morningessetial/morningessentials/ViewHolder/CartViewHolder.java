package com.morningessetial.morningessentials.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.morningessetial.morningessentials.Interface.ItemClickListener;
import com.morningessetial.sohammorningessentials.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView price,itemslist,name,weight;
    public ImageView img;
    public Spinner quantity;
    public ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        price= itemView.findViewById(R.id.price);
        itemslist= itemView.findViewById(R.id.itemslist);
        name = itemView.findViewById(R.id.name);
        weight = itemView.findViewById(R.id.weight);
        img = itemView.findViewById(R.id.itemimage);
        quantity = itemView.findViewById(R.id.quantity);
    }

    public void setItemClickListener(ItemClickListener listener){

        this.itemClickListener = listener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false);
    }
}
