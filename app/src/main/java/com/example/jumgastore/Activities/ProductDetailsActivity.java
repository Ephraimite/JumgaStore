package com.example.jumgastore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.jumgastore.Model.CurrentUser;
import com.example.jumgastore.Model.Products;
import com.example.jumgastore.R;
import com.flutterwave.raveandroid.rave_java_commons.SubAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addTocart;
    private ImageView productImage;
    private TextView productName, productDescription, productPrice;

    String productID ="", state = "Normal";
    ElegantNumberButton numberButton;

    private DatabaseReference cartItemRef;
    private String sellerCartItemID ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        productImage = findViewById(R.id.product_image_details);
        productName =findViewById(R.id.product_name_details);
        addTocart = findViewById(R.id.addToCart_button);
        productDescription = findViewById(R.id.product_description_details);
        productPrice = findViewById(R.id.product_price_details);

        numberButton = findViewById(R.id.number_button_product_details);

        sellerCartItemID = getIntent().getStringExtra("cartItemId");

        getProductDetails(productID);

        addTocart.setOnClickListener(v -> {
            if (state.equals("Order placed") | state.equals("Order Shipped")){
                Toast.makeText(this, "You can purchase more products once your order is confirmed", Toast.LENGTH_LONG).show();

            }else {
                addingToCart();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkorderState();
    }

    private void addingToCart() {
        String saveCurrenTime, saveCurrentDate;
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(cal.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrenTime = currentDate.format(cal.getTime());

        final DatabaseReference cartList = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", productName.getText().toString());
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrenTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("cartItemID", sellerCartItemID);
        cartMap.put("discount", "");

        cartList.child("User View").child(CurrentUser.currentOnlineUsers.getPhone()).child("products").child(productID)
                .updateChildren(cartMap).addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        cartList.child("Admin View").child(CurrentUser.currentOnlineUsers.getPhone()).child("products").child(productID)
                                .updateChildren(cartMap)
                                .addOnCompleteListener(task1 -> {
                                    if (task.isSuccessful()){
                                        Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                });
                    }
                });

    }


    private void getProductDetails(String productID) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Products products = snapshot.getValue(Products.class);

                    productName.setText(products.getProductname());
                    productDescription.setText(products.getDescription());
                    productPrice.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkorderState(){
        DatabaseReference odersRef;
        odersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(CurrentUser.currentOnlineUsers.getPhone());

        odersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String shippingState = snapshot.child("status").getValue().toString();
                    String userName = snapshot.child("name").getValue().toString();

                    if (shippingState.equals("shipped")){
                        state = "Order Placed";

                    }else if (shippingState.equals("Not Shipped")){
                        state = "Order Shipped";

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}