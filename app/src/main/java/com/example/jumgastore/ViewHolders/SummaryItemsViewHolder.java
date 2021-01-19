package com.example.jumgastore.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jumgastore.Interface.ItemClickListener;
import com.example.jumgastore.R;

public class SummaryItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ItemClickListener Listener2;
    public TextView product_name, product_price, product_qty;

    public SummaryItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        product_name = itemView.findViewById(R.id.p_name);
        product_price = itemView.findViewById(R.id.price);
        product_qty = itemView.findViewById(R.id.qty);
    }

    public void  setItemclickListener(ItemClickListener listener){
        this.Listener2 = listener;
    }


    @Override
    public void onClick(View v) {
        Listener2.Onclick(v, getAdapterPosition(), false);


    }
}
