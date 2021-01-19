package com.example.jumgastore.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jumgastore.Activities.HomeActivity;
import com.example.jumgastore.Activities.ProductDetailsActivity;
import com.example.jumgastore.Model.CurrentUser;
import com.example.jumgastore.Model.Products;
import com.example.jumgastore.R;
import com.example.jumgastore.ViewHolders.ProductViewHolder;
import com.example.jumgastore.ViewHolders.SellerHomeDisplayViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellerFragmentHome extends Fragment {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView uploadedProducts;
    private Animation fadeInAnim;
    private Animation fadeOutAnim;
    private FragmentTransaction mFragmentTransaction;
    DatabaseReference ProductRef;

    public SellerFragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.seller_fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uploadedProducts = view.findViewById(R.id.tv_uploaded_Produt_Heading);
        recyclerView = view.findViewById(R.id.seller_home_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ProductRef = FirebaseDatabase.getInstance().getReference().child("products");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.
                Builder<Products>().setQuery(ProductRef.orderByChild("phone").equalTo(CurrentUser.currentMerchant.getPhone()), Products.class).build();

        FirebaseRecyclerAdapter<Products, SellerHomeDisplayViewHolder> adapter = new FirebaseRecyclerAdapter<Products, SellerHomeDisplayViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SellerHomeDisplayViewHolder holder, int position, @NonNull Products model) {

                String listDesc = model.getDescription();
                if (listDesc.length() >50){
                    listDesc = listDesc.substring(0, 50);
                }

                holder.sp_name.setText(model.getProductname());
                holder.sp_description.setText(listDesc + ".....");
                holder.sp_productPrice.setText("₦" + model.getPrice());
                String imageUrl = model.getImage();
                Glide.with(holder.itemView.getContext())
                        .load(imageUrl)
                        .centerCrop()
                        .into(holder.sp_productImage);

                if (position == -1){
                    uploadedProducts.setText("You Havent uplaoded Products Yet");
                }



            }

            @NonNull
            @Override
            public SellerHomeDisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_seller_product_home, parent, false);
                return new SellerHomeDisplayViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}

