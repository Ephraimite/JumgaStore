package com.example.jumgastore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jumgastore.R;
import com.example.jumgastore.SignUpButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText et_name, et_email, et_phone, et_password;

    TextView login_here;
    View view;
    private SignUpButton signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        countryCodePicker = findViewById(R.id.ccp);


        et_name = findViewById(R.id.signup_name);
        et_email = findViewById(R.id.signup_email);
        et_phone = findViewById(R.id.signup_phone);
        et_password = findViewById(R.id.signUp_password);
        login_here = findViewById(R.id.Login_here);

        view = findViewById(R.id.btn_signup);


        signUpButton = new SignUpButton(SignupActivity.this, view);


        view.setOnClickListener(v -> {

            String Usname = et_email.getText().toString();
            String USemail = et_password.getText().toString();
            String Usphone = countryCodePicker.getSelectedCountryCodeWithPlus() + et_phone.getText().toString().trim();
            String Uspassword = et_password.getText().toString();

            if (TextUtils.isEmpty(Usname)){
                Toast.makeText(this, "Pls type your name", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(USemail)) {
                Toast.makeText(this, "Pls input email", Toast.LENGTH_LONG).show();
            }else if (TextUtils.isEmpty(Usphone)) {
                Toast.makeText(this, "Pls input phone number", Toast.LENGTH_LONG).show();
            }else if (TextUtils.isEmpty(Uspassword)) {
                Toast.makeText(this, "Pls input password", Toast.LENGTH_LONG).show();
            }
            else{
                signUpUser(Usname, Usphone, USemail, Uspassword);
                signUpButton.buttonActivated();
            }
        });

        login_here.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void signUpUser(String name, String phone, String email, String password) {

        final DatabaseReference Root;
        Root = FirebaseDatabase.getInstance().getReference();

        Root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> userdata = new HashMap<>();
                    userdata.put("name", name);
                    userdata.put("phone", phone);
                    userdata.put("email", email);
                    userdata.put("password", password);

                    Root.child("Users").child(phone).updateChildren(userdata).
                            addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    Toast.makeText(SignupActivity.this, "Account created sucessfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    signUpButton.buttonFinished();
                                }else{
                                    Toast.makeText(SignupActivity.this, "An Error occured...try again", Toast.LENGTH_SHORT).show();
                                    signUpButton.buttonFinished();
                                }
                            });
                }else {
                    Toast.makeText(SignupActivity.this, "This " + " " + phone + " Already Exist", Toast.LENGTH_SHORT).show();
                    signUpButton.buttonFinished();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}