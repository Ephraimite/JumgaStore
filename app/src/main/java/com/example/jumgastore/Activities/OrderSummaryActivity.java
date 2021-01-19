package com.example.jumgastore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jumgastore.Model.Cart;
import com.example.jumgastore.Model.CurrentUser;
import com.example.jumgastore.PaymentGateways.FlutterwavePaymentActivity2;
import com.example.jumgastore.R;
import com.example.jumgastore.ViewHolders.SummaryItemsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderSummaryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button btn_proceed;
    private TextView  tv_deliveryFee, tv_orderTotal;
    private String name;
    private String email;
    private String phone;
    private int total;
    private int delivery_fee;

    int finalPrice = 0;
    RadioGroup radioGroup;
    RadioButton pay_card, pay_after_service;

    ArrayList<Cart> newCartList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        recyclerView = findViewById(R.id.rv_summary);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        radioGroup = findViewById(R.id.radio_group);


        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("buyer_name");
        email = bundle.getString("buyer_email");
        phone = bundle.getString("buyer_phone");
        total = bundle.getInt("total_price");
        delivery_fee = bundle.getInt("delivery_fee");
        newCartList = bundle.getParcelableArrayList("newList1");

        tv_deliveryFee = findViewById(R.id.tvdelivery_fee);
        tv_orderTotal = findViewById(R.id.totalPrice_summary);
        btn_proceed = findViewById(R.id.btn_proceed_summary);

        finalPrice = delivery_fee + total;
        tv_deliveryFee.setText(String.valueOf(delivery_fee));
        tv_orderTotal.setText(String.valueOf(finalPrice));

        btn_proceed.setOnClickListener(v -> {

            RadioGroup rg = radioGroup;
            int ID = rg.getCheckedRadioButtonId();

            if (ID == R.id.rb_payNow) {
                proceedToFlutterwavePay();
            } else if (ID == R.id.rb_delivery) {
                Toast.makeText(OrderSummaryActivity.this, "pay on delivery is not accepted at the moment", Toast.LENGTH_SHORT).show();
            }


        });

    }

    private void proceedToFlutterwavePay() {

        Bundle bundle = new Bundle();
        bundle.putInt("finalPrice", total);
        bundle.putInt("deliveryPrice", delivery_fee);
        bundle.putString("bname", name);
        bundle.putString("bemail", email);
        bundle.putString("bphone", phone);
        bundle.putParcelableArrayList("newCartList1", newCartList);

        Intent intent = new Intent(OrderSummaryActivity.this, FlutterwavePaymentActivity2.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();


        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User View")
                                .child(CurrentUser.currentOnlineUsers.getPhone())
                                .child("products"), Cart.class).build();

        FirebaseRecyclerAdapter<Cart, SummaryItemsViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, SummaryItemsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SummaryItemsViewHolder holder, int position, @NonNull Cart model) {

                holder.product_name.setText(model.getPname());
                holder.product_qty.setText(model.getQuantity());
                holder.product_price.setText("₦" + model.getPrice());


            }

            @NonNull
            @Override
            public SummaryItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_checkout_items, parent, false);
                SummaryItemsViewHolder holder = new SummaryItemsViewHolder(view);
                return holder;
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}