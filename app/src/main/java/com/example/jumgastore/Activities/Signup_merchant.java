
package com.example.jumgastore.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jumgastore.R;
import com.example.jumgastore.SignUpButton;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class Signup_merchant extends AppCompatActivity {

    EditText name, shopName, phone_number, DOB, email, password, reTypePassword;
    Button Btn_signup_merchant_continue;
    Calendar mCalendar;
    DatePickerDialog mDatePickerDialog;
    CountryCodePicker countryCodePicker;

    private String mCurrentDate;
    private String etPhone;
    private String etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_merchant);

        name = findViewById(R.id.signup_merchant_name);
        shopName = findViewById(R.id.signup_merchant_shopName);
        phone_number = findViewById(R.id.signup_merchant_phone);
        DOB = findViewById(R.id.signup_merchant_dob);
        email = findViewById(R.id.signup_merchant_email);
        password = findViewById(R.id.signup_merchant_password);
        reTypePassword = findViewById(R.id.signup_merchant_retypePassword);
        Btn_signup_merchant_continue = findViewById(R.id.signup_merchant_continue);

        DOB.setOnClickListener(v -> {

            pickDate();

        });

        Btn_signup_merchant_continue.setOnClickListener(v -> {

            String etName = name.getText().toString().trim();
            String etShopName = shopName.getText().toString().trim();

            String etDOB = DOB.getText().toString().trim();
            String etPhone = phone_number.getText().toString();
            String etPassword = password.getText().toString().trim();
            String etRetypePassword = reTypePassword.getText().toString().trim();

            if (!isValidEmail()){
                email.setError("Invalid email");
                return;
            }else if (TextUtils.isEmpty(etPassword)){
                password.setError("password cannot be empty");
            }else if (TextUtils.isEmpty(etPhone)) {
                phone_number.setError("password cannot be empty");
            }

                Bundle bundle = new Bundle();
                bundle.putString("MerchantName", etName);
                bundle.putString("shopName", etShopName);
                bundle.putString("phoneNo", etPhone);
                bundle.putString("DOB", etDOB);
                bundle.putString("email", etEmail);
                bundle.putString("password", etPassword);
                bundle.putString("retypePassword", etRetypePassword);

                Intent intent = new Intent(Signup_merchant.this, Signup_merchant_businessInfo.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

        });


    }

    private void pickDate() {
        mCalendar = Calendar.getInstance();
        final int day = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        final int year = mCalendar.get(Calendar.DAY_OF_MONTH);

        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.MONTH, mMonth);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);

                mCurrentDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
                DOB.setText(mCurrentDate);
            }
        }, day, month, year);
        mDatePickerDialog.show();
    }

    private boolean isValidEmail() {
        etEmail = email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (etEmail.isEmpty()){
            email.setError("Email cannot be empty");
            return false;
        }else if (!etEmail.matches(checkEmail)){
            email.setError("Invalid Email");
            return false;
        }else {
            return true;
        }
    }

}