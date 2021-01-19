
package com.example.jumgastore.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jumgastore.Model.Cart;
import com.example.jumgastore.R;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {

    private EditText et_name, et_phone, et_home_address, et_city_name, et_email;
    Button btn_confirm_order;
    private String totalAmount = "";
    private final int deliveryFee = 1000;
    private String name;
    private String phone;
    private String homeAddress;
    private String cityName;
    private String email;
    ArrayList<Cart> newList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        totalAmount = getIntent().getStringExtra("total price");
        newList = getIntent().getParcelableArrayListExtra("myList");


        et_name = findViewById(R.id.name_ship);
        et_phone = findViewById(R.id.phone_ship);
        et_home_address = findViewById(R.id.home_address_ship);
        et_city_name = findViewById(R.id.city_name_ship);
        et_email = findViewById(R.id.email_ship);

        btn_confirm_order = findViewById(R.id.confirm_button);

        Toast.makeText(this, totalAmount, Toast.LENGTH_LONG).show();

        btn_confirm_order.setOnClickListener(v -> {

            name = et_name.getText().toString();
            phone = et_phone.getText().toString();
            homeAddress = et_home_address.getText().toString();
            cityName = et_city_name.getText().toString();
            email = et_email.getText().toString();

            if (TextUtils.isEmpty(name)){
                Toast.makeText(this, "Please provide your full name", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(phone)){
                Toast.makeText(this, "Please type your phone number", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(homeAddress)){
                Toast.makeText(this, "Please type your address", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(cityName)){
                Toast.makeText(this, "city name is required", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            }
            else {
                Bundle bundle = new Bundle();
                bundle.putInt("total_price", Integer.parseInt(totalAmount));
                bundle.putInt("delivery_fee", deliveryFee);
                bundle.putString("buyer_name", name);
                bundle.putString("buyer_email", email);
                bundle.putString("buyer_phone", phone);
                bundle.putParcelableArrayList("newList1", newList);

                Intent intent = new Intent(ConfirmOrderActivity.this, OrderSummaryActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
//                confirmOrder();
            }
        });


    }

//    private void confirmOrder() {
//
//        final String saveCurrenTime, saveCurrentDate;
//        Calendar cal = Calendar.getInstance();
//
//        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
//        saveCurrentDate = currentDate.format(cal.getTime());
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        saveCurrenTime = currentDate.format(cal.getTime());
//
//        final DatabaseReference OrderRef = FirebaseDatabase.getInstance().getReference().child("Orders")
//                .child(CurrentUser.currentOnlineUsers.getPhone());
//
//        HashMap<String, Object> orderMap = new HashMap<>();
//        orderMap.put("totalAmount", totalAmount);
//        orderMap.put("name", et_name.getText().toString());
//        orderMap.put("phone", et_phone.getText().toString());
//        orderMap.put("address", et_home_address);
//        orderMap.put("city", et_city_name);
//        orderMap.put("date", saveCurrentDate);
//        orderMap.put("time", currentTime);
//        orderMap.put("status", "Not Shipped");
//
//        OrderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                FirebaseDatabase.getInstance().getReference()
//                        .child("Cart List")
//                        .child("User View")
//                        .child(CurrentUser.currentOnlineUsers.getPhone())
//                        .removeValue()
//                        .addOnCompleteListener(task1 -> {
//                            if (task1.isSuccessful()){
//                                Toast.makeText(ConfirmOrderActivity.this, "Your order has been placed successfully", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });
//    }
}