package com.example.jumgastore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jumgastore.Api.FlutterwaveSubAccountsApi;
import com.example.jumgastore.ApiClient.apiClient;
import com.example.jumgastore.Model.Cart;
import com.example.jumgastore.Model.CurrentUser;
import com.example.jumgastore.Model.GetAllSubAccountResponse;
import com.example.jumgastore.Model.SubAccounts;
import com.example.jumgastore.R;
import com.flutterwave.raveandroid.rave_java_commons.SubAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnProceedNext;
    private TextView tv_totalAmount;

    private ArrayList<Cart> list;
    private CartRecyclerAdapter adapter;
    private int overallTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        btnProceedNext = findViewById(R.id.btn_placeorder);
        tv_totalAmount = findViewById(R.id.c_total_price);

        recyclerView = findViewById(R.id.recycler_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("User View")
                .child(CurrentUser.currentOnlineUsers.getPhone())
                .child("products");

        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<String> keys = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {



                    Cart cart = dataSnapshot.getValue(Cart.class);
                    list.add(cart);
                    keys.add(String.valueOf(dataSnapshot.child("User View")));

                    overallTotalPrice += Integer.parseInt(cart.getPrice()) * Integer.parseInt(cart.getQuantity());

                    try {

                    }catch (NumberFormatException exception){
                        exception.printStackTrace();
                    }

                    tv_totalAmount.setText(String.valueOf(overallTotalPrice));

                }
                adapter = new CartRecyclerAdapter(list, keys);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnProceedNext.setOnClickListener(v -> {

            Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
            intent.putExtra("total price", String.valueOf(overallTotalPrice));
            intent.putParcelableArrayListExtra("myList", list);

            startActivity(intent);
            finish();
        });

        checkorderState();

    }


    private void checkorderState() {
        DatabaseReference odersRef;
        odersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(CurrentUser.currentOnlineUsers.getPhone());

        odersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String shippingState = snapshot.child("status").getValue().toString();
                    String userName = snapshot.child("name").getValue().toString();

                    if (shippingState.equals("shipped")) {
                        recyclerView.setVisibility(View.GONE);
                        btnProceedNext.setEnabled(false);

                    } else if (shippingState.equals("Not Shipped")) {

                        recyclerView.setVisibility(View.GONE);
                        btnProceedNext.setEnabled(false);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}