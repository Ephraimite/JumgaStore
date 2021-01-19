package com.example.jumgastore.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jumgastore.Model.Cart;
import com.example.jumgastore.Model.CurrentUser;
import com.example.jumgastore.Model.SubAccounts;
import com.example.jumgastore.R;
import com.flutterwave.raveandroid.rave_java_commons.SubAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.myViewHolder> {

    ArrayList<Cart> cartList;
    List<String> keys;


    public CartRecyclerAdapter(ArrayList<Cart> cartList, List<String> keys) {
        this.cartList = cartList;
        this.keys = keys;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        Cart model = cartList.get(position);
        holder.tv_productName.setText(model.getPname());
        holder.tv_productQuantity.setText(model.getQuantity());
        holder.tv_ProductPrice.setText("₦" + model.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (cartList == null){
            return 0;
        }else {
            return cartList.size();
        }
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_productName, tv_ProductPrice, tv_productQuantity;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_productName = itemView.findViewById(R.id.product_name_cart);
            tv_ProductPrice = itemView.findViewById(R.id.tv_cart_price);
            tv_productQuantity = itemView.findViewById(R.id.product_cart_quantity);

        }
    }

    public void deleteItem(int position){
        String key = keys.get(position);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cart List");
        ref.child(key).removeValue();
    }

}
