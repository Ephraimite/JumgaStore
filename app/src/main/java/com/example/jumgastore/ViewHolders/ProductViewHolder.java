package com.example.jumgastore.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jumgastore.Interface.ItemClickListener;
import com.example.jumgastore.R;

public class ProductViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ItemClickListener Listener;

    public TextView tv_product_name, tv_product_description, tv_productPrice;
    public ImageView productImage;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        productImage = itemView.findViewById(R.id.product_display_image);
        tv_product_name = itemView.findViewById(R.id.product_display_name);
        tv_product_description = itemView.findViewById(R.id.product_display_description);
        tv_productPrice = itemView.findViewById(R.id.product_display_price);
    }

    public void  setItemclickListener(ItemClickListener listener){
        this.Listener = listener;
    }

    @Override
    public void onClick(View v) {
        Listener.Onclick(v, getAdapterPosition(), false);
    }
}
