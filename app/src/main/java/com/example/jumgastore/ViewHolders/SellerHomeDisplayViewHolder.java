package com.example.jumgastore.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jumgastore.Interface.ItemClickListener;
import com.example.jumgastore.R;

public class SellerHomeDisplayViewHolder extends RecyclerView.ViewHolder {

    public ItemClickListener Listener;

    public TextView sp_name, sp_description, sp_productPrice;
    public ImageView sp_productImage;

    public SellerHomeDisplayViewHolder(@NonNull View itemView) {
        super(itemView);

        sp_productImage = itemView.findViewById(R.id.display_image_seller);
        sp_name = itemView.findViewById(R.id.display_name_seller);
        sp_description = itemView.findViewById(R.id.display_description_seller);
        sp_productPrice = itemView.findViewById(R.id.display_price_seller);
    }

}
