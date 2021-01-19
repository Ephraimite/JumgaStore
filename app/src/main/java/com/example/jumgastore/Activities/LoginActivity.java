package com.example.jumgastore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jumgastore.LoginButton;
import com.example.jumgastore.Model.CurrentUser;
import com.example.jumgastore.Model.Users;
import com.example.jumgastore.R;
import com.example.jumgastore.SignUpButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    TextView signUpHere, AdminLink, NotAdminLink, tv_login, merchant_signUp_here;
    EditText et_phone, et_password;
    View view;
    CountryCodePicker countryCodePicker;



    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private CheckBox checkBoxRememberMe;

    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        countryCodePicker = findViewById(R.id.ccp3);
        AdminLink = findViewById(R.id.admin_panel_link);

        checkBoxRememberMe = findViewById(R.id.checkBox_remember_me);
        tv_login = findViewById(R.id.tv_login);
        signUpHere = findViewById(R.id.signUp_here);
        et_phone = findViewById(R.id.login_phone);
        et_password = findViewById(R.id.Login_password);
        view = findViewById(R.id.btn_login);

        Paper.init(this);



        view.setOnClickListener(v -> {

            loginButton = new LoginButton(LoginActivity.this, view);
            String phoneNo = countryCodePicker.getSelectedCountryCodeWithPlus() + et_phone.getText().toString().trim();
            String password = et_password.getText().toString();

            if (TextUtils.isEmpty(phoneNo)){
                Toast.makeText(this, "Pls input phone number", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Pls input password", Toast.LENGTH_LONG).show();
            }else{
                loginUser(phoneNo, password);
                loginButton.buttonActivated();
            }
        });

        signUpHere.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            finish();
        });
        
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MerchantLogin.class));
            }
        });

        String userphone = Paper.book().read(CurrentUser.UserPhoneKey);
        String userpassword = Paper.book().read(CurrentUser.UserPhoneKey);

        if (userphone !="" && userpassword !=""){
            if (!TextUtils.isEmpty(userphone) && !TextUtils.isEmpty(userpassword)){
                allowAccess(userphone, userpassword);
            }
        }

        loginButton = new LoginButton(LoginActivity.this, view);

    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    private void allowAccess(String userphone, String userpassword) {
        final DatabaseReference Root;
        Root = FirebaseDatabase.getInstance().getReference();

        Root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(userphone).exists()){

                    Users users = snapshot.child("Users").child(userphone).getValue(Users.class);
                    if (users.getPhone().equals(userphone)){
                        if (users.getPassword().equals(userpassword)){

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            CurrentUser.currentOnlineUsers = users;
                            loginButton.buttonFinished();

                        }
                        loginButton.buttonFinished();
                    }else {
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        loginButton.buttonFinished();
                    }

                }else {
                    Toast.makeText(LoginActivity.this, "Account With this " + userphone + " email does not exist", Toast.LENGTH_LONG
                    ).show();
                    loginButton.buttonFinished();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loginUser(String phoneNo, String password) {

        if (checkBoxRememberMe.isChecked()){
            Paper.book().write(CurrentUser.UserPhoneKey, phoneNo);
            Paper.book().write(CurrentUser.UserPasswordKey, password);
        }

        final DatabaseReference Root;
        Root = FirebaseDatabase.getInstance().getReference();

        Root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(phoneNo).exists()){

                    Users users = snapshot.child("Users").child(phoneNo).getValue(Users.class);
                    if (users.getPhone().equals(phoneNo)){
                        if (users.getPassword().equals(password)){


                                Toast.makeText(LoginActivity.this, "Logged In successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            CurrentUser.currentOnlineUsers = users;

                        }
                    }

                }else {
                    Toast.makeText(LoginActivity.this, "Account With this " + phoneNo + " email does not exist", Toast.LENGTH_LONG
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