package com.example.jumgastore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jumgastore.LoginButton;
import com.example.jumgastore.Model.CurrentUser;
import com.example.jumgastore.Model.Merchants;
import com.example.jumgastore.Model.Users;
import com.example.jumgastore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class MerchantLogin extends AppCompatActivity {

    TextView merchant_signUp_here;
    EditText et_phone_merchant, et_password_merchant;
    View view;
    private LoginButton loginButton;
    private String phone;
    private String password;

    CountryCodePicker countryCodePicker;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_login);

        mAuth = FirebaseAuth.getInstance();

        countryCodePicker = findViewById(R.id.ccp4);
        merchant_signUp_here = findViewById(R.id.signUp_here_merchant);
        et_phone_merchant = findViewById(R.id.login_phone_merchant);
        et_password_merchant = findViewById(R.id.Login_password_merchant);
        view = findViewById(R.id.btn_login_merchant);

        view.setOnClickListener(v -> {

            loginButton = new LoginButton(MerchantLogin.this, view);
            phone = countryCodePicker.getSelectedCountryCodeWithPlus() + et_phone_merchant.getText().toString().trim();
            password = et_password_merchant.getText().toString();

            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Pls input email", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Pls input password", Toast.LENGTH_LONG).show();
            } else {

                checkPaymentStatus();
                loginButton.buttonActivated();
            }
        });

        merchant_signUp_here.setOnClickListener(v -> startActivity(new Intent(MerchantLogin.this, Signup_merchant.class)));

    }

    private void checkPaymentStatus() {
        DatabaseReference odersRef;
        odersRef = FirebaseDatabase.getInstance().getReference().child("Merchants")
                .child(phone);

        odersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String isPaidUser = snapshot.child("isPaidUser").getValue().toString();

                    if (isPaidUser.equals("isPaid")) {
                        LoginMerchant(phone, password);
                        loginButton.buttonFinished();


                    } else if (isPaidUser.equals("unPaid")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MerchantLogin.this);
                        builder.setMessage("Your shop has not been approved yet, pls be patient while admin review your informations");
                        builder.setTitle("We are sorry");
                        builder.setPositiveButton("Ok", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        builder.setNegativeButton("Contact Support", (dialog, which) -> {

                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                        loginButton.buttonFinished();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void LoginMerchant(String email, String password) {
        final DatabaseReference Root;
        Root = FirebaseDatabase.getInstance().getReference();

        Root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Merchants").child(phone).exists()) {

                    Merchants sellers = snapshot.child("Merchants").child(phone).getValue(Merchants.class);
                    if (sellers.getPhone().equals(phone)) {
                        if (sellers.getPassword().equals(password)) {

                            CurrentUser.currentMerchant = sellers;
                            loginButton.buttonFinished();
                            startActivity(new Intent(MerchantLogin.this, SellerMainActivity.class));

                            Toast.makeText(MerchantLogin.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(MerchantLogin.this, "Account With this " + email + " email does not exist", Toast.LENGTH_LONG
                    ).show();
                    loginButton.buttonFinished();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}