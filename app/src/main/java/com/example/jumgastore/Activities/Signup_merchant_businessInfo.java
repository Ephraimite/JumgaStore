package com.example.jumgastore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jumgastore.Api.FlutterWaveAccountValidationApi;
import com.example.jumgastore.Api.FlutterwaveGetAllBanksApi;
import com.example.jumgastore.Api.FlutterwaveSubAccountsApi;
import com.example.jumgastore.ApiClient.apiClient;
import com.example.jumgastore.Model.Banks;
import com.example.jumgastore.Model.BanksResponse;
import com.example.jumgastore.Model.CreateSubAccountData;
import com.example.jumgastore.Model.CreateSubAccountRequest;
import com.example.jumgastore.Model.CreateSubAccountResponse;
import com.example.jumgastore.Model.ValidateAccountNo;
import com.example.jumgastore.Model.ValidateAccountRequest;
import com.example.jumgastore.PaymentGateways.FlutterwaveActivity;
import com.example.jumgastore.R;
import com.example.jumgastore.SignUpButton;
import com.flutterwave.raveandroid.rave_core.models.Bank;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Signup_merchant_businessInfo extends AppCompatActivity {

    TextView tv_merchant_country_pay;
    Button btn__merchant_signup_pay, btnCancel;
    EditText address, city, companyName, bankName, accountNo;
    View btnSignup_merchant;
    private SignUpButton signUpButton;
    Dialog dialog;
    Spinner spinner;
    Spinner spinner_banks;
    View view;

    private FirebaseAuth mAuth;
    String NG = "Congratulations! you are required to pay a sum of ₦500 for store approval";
    String KY = "Congratulations! you are required to pay a sum of Ksh145 for store approval";
    String GH = "Congratulations! you are required to pay a sum of GHS8 for store approval";
    String UK = "Congratulations! you are required to pay a sum of €2 for store approval";

    String currency;
    int amount = 0;
    String country;

    String selectedCountry, name, shopName, phoneNumber, DOB, email, password, retypePassword;

    private String etAddress;
    private String etCity;
    private String etBankName;
    private String etAccountNo;
    private String bankCode;
    private String subAccountID;
    private boolean isPaidUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_merchant_business_info);

        Bundle bundle = getIntent().getExtras();

        mAuth = FirebaseAuth.getInstance();

        address = findViewById(R.id.signup_merchant_address);
        city = findViewById(R.id.signup_merchant_city);
        spinner_banks = findViewById(R.id.signup_merchant_bankName);
        accountNo = findViewById(R.id.signup_merchant_AccountNo);
        btnSignup_merchant = findViewById(R.id.btn_signup_merchant);

        mAuth = FirebaseAuth.getInstance();

        btnCancel = findViewById(R.id.signuUp_cancel);
        spinner = findViewById(R.id.spinner1);

        LayoutInflater inflater = getLayoutInflater();
        View myView = inflater.inflate(R.layout.custom_merchant_dialog, null);

        btn__merchant_signup_pay = myView.findViewById(R.id.signUp_pay);

        dialog = new Dialog(Signup_merchant_businessInfo.this);
        dialog.setContentView(R.layout.custom_merchant_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        btn__merchant_signup_pay = dialog.findViewById(R.id.signUp_pay);
        btnCancel = dialog.findViewById(R.id.signuUp_cancel);
        tv_merchant_country_pay = dialog.findViewById(R.id.tv_signUp_merchant_pay);


        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(Signup_merchant_businessInfo.this, HomeActivity.class));
        });
        btn__merchant_signup_pay.setOnClickListener(v -> {
        proceedToPaymentActivity();

        });


//        Display the available countries in a spinner
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.Countries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner_banks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Banks bank = (Banks) parent.getItemAtPosition(position);
                String bankn = bank.getName();
                Toast.makeText(Signup_merchant_businessInfo.this, bank.getCode() + " " + bankn, Toast.LENGTH_SHORT).show();
                // TODO: 1/12/2021 Remove the toast when done

                bankCode = bank.getCode();
                // TODO: 1/12/2021 Make API call with the bankCode variable

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        get selected countries and set the text, amount and currency appropriately
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = parent.getItemAtPosition(position).toString();



                if (position == 1) {
                    tv_merchant_country_pay.setText(NG);
                    amount = 500;
                    country = "NG";
                    getListOfBanks(country);
                } else if (position == 2) {
                    tv_merchant_country_pay.setText(GH);
                    amount = 8;
                    country = "GH";
                    getListOfBanks(country);
                } else if (position == 3) {
                    tv_merchant_country_pay.setText(KY);
                    amount = 145;
                    country = "KE";
                    getListOfBanks(country);
                } else if (position == 4) {
                    tv_merchant_country_pay.setText(UK);
                    amount = 2;
                    country = "UK";
                    getListOfBanks(country);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Signup_merchant_businessInfo.this, "Please select a Country", Toast.LENGTH_LONG).show();
            }
        });


        signUpButton = new SignUpButton(Signup_merchant_businessInfo.this, btnSignup_merchant);

        btnSignup_merchant.setOnClickListener(v -> {


            name = bundle.getString("MerchantName");
            shopName = bundle.getString("shopName");
            phoneNumber = bundle.getString("phoneNo");
            DOB = bundle.getString("DOB");
            email = bundle.getString("email");
            password = bundle.getString("password");
            retypePassword = bundle.getString("retypePassword");

            etAddress = address.getText().toString().trim();
            etCity = city.getText().toString().trim();
            etAccountNo = accountNo.getText().toString().trim();

            if (!isValidAccountNumber()) {
                accountNo.setError("Input account number");
            }else if (etAccountNo.length()<10){
                accountNo.setError("Account number cannot be less than 10");
            }else {

                validateAccount();
                signUpButton.buttonActivated();
            }


        });

    }

    private void proceedToPaymentActivity() {
        Bundle bundle1 = new Bundle();
        bundle1.putInt("amount", amount);
        bundle1.putString("name", name);
        bundle1.putString("phone", phoneNumber);
        bundle1.putString("email", email);
        bundle1.putString("country", country);

        Intent intent = new Intent(Signup_merchant_businessInfo.this, FlutterwaveActivity.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    private void validateAccount() {
        ValidateAccountRequest validateAccountRequest = new ValidateAccountRequest(etAccountNo, bankCode);
        Retrofit retrofit = apiClient.getRetrofitInstance();
        FlutterWaveAccountValidationApi flutterWaveAccountValidationApi = retrofit.create(FlutterWaveAccountValidationApi.class);
        Call<ValidateAccountNo> call = flutterWaveAccountValidationApi.validateUserAccount(validateAccountRequest);

        call.enqueue(new Callback<ValidateAccountNo>() {
            @Override
            public void onResponse(Call<ValidateAccountNo> call, Response<ValidateAccountNo> response) {
                TextView tv_invalidAccount = findViewById(R.id.tv_invalid_account_number);
                if (response.code() ==200) {
                    String message = response.body().getMessage();
                    if (response.body().getStatus().equals("success")){
                        SignUpMerchant();
                        SignUpAsSubAccount();

                    }else {
                        Toast.makeText(Signup_merchant_businessInfo.this, message, Toast.LENGTH_LONG).show();
                        accountNo.setText("");
                        signUpButton.buttonFinished();
                        tv_invalidAccount.setVisibility(View.VISIBLE);
                    }

                }else {
                    Toast.makeText(Signup_merchant_businessInfo.this, "Invalid Account Number", Toast.LENGTH_LONG).show();
                    accountNo.setText("");
                    signUpButton.buttonFinished();
                }

            }

            @Override
            public void onFailure(Call<ValidateAccountNo> call, Throwable t) {

            }
        });



    }

    private void getListOfBanks(String country) {
        Retrofit retrofit = apiClient.getRetrofitInstance();
        FlutterwaveGetAllBanksApi flutterwaveGetAllBanksApi = retrofit.create(FlutterwaveGetAllBanksApi.class);
        Call<BanksResponse> call = flutterwaveGetAllBanksApi.getListOfBanks(country);

        call.enqueue(new Callback<BanksResponse>() {
            @Override
            public void onResponse(Call<BanksResponse> call, Response<BanksResponse> response) {

                if (response.body() != null) {

                    Log.d("bankResponse", response.body().getData().toString());
                    List<Banks> bankList = response.body().getData();
                    ArrayAdapter<Banks> bankAdapter = new ArrayAdapter<>(Signup_merchant_businessInfo.this, android.R.layout.simple_spinner_dropdown_item, bankList);
                    bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_banks.setAdapter(bankAdapter);
                    String error = response.body().getStatus();
                    String message = response.body().getMessage();
                    Toast.makeText(Signup_merchant_businessInfo.this, message + error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BanksResponse> call, Throwable t) {

            }
        });
    }

    private void SignUpMerchant() {
        final DatabaseReference Root;
        Root = FirebaseDatabase.getInstance().getReference();

        Root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Merchants").child(phoneNumber).exists())) {
                    HashMap<String, Object> merchantData = new HashMap<>();

                    merchantData.put("MerchantName", name);
                    merchantData.put("shopName", shopName);
                    merchantData.put("phone", phoneNumber);
                    merchantData.put("email", email);
                    merchantData.put("city", etCity);
                    merchantData.put("address", etAddress);
                    merchantData.put("country", selectedCountry);
                    merchantData.put("Date of birth", DOB);
                    merchantData.put("bankName", etBankName);
                    merchantData.put("accountNumber", etAccountNo);
                    merchantData.put("password", retypePassword);
                    merchantData.put("SubAccountID", subAccountID);
                    merchantData.put("isPaidUser", "unPaid");


                    Root.child("Merchants").child(phoneNumber).updateChildren(merchantData).
                            addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    openAlertDialog();
                                    signUpButton.buttonFinished();
                                } else {
                                    Toast.makeText(Signup_merchant_businessInfo.this, "An Error occured...try again", Toast.LENGTH_LONG).show();
                                    signUpButton.buttonFinished();
                                }
                            });
                } else {
                    Toast.makeText(Signup_merchant_businessInfo.this, "Merchant account already exist", Toast.LENGTH_LONG).show();
                    signUpButton.buttonFinished();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SignUpAsSubAccount() {
        CreateSubAccountRequest createSubAccountRequest = new CreateSubAccountRequest(
                bankCode,etAccountNo,name,phoneNumber,email,phoneNumber,shopName, country,"percentage", 0.1);

        Retrofit retrofit = apiClient.getRetrofitInstance();
        FlutterwaveSubAccountsApi flutterwaveSubAccountsApi = retrofit.create(FlutterwaveSubAccountsApi.class);
        Call<CreateSubAccountResponse> call = flutterwaveSubAccountsApi.createSubMerchantAccounts(createSubAccountRequest);

        call.enqueue(new Callback<CreateSubAccountResponse>() {
            @Override
            public void onResponse(Call<CreateSubAccountResponse> call, Response<CreateSubAccountResponse> response) {

                if (response.body() !=null) {
                    CreateSubAccountResponse data = response.body();
                    String status = data.getMessage();
                    subAccountID = data.getData().getSubaccountId();
                    String resp = response.body().getMessage();
                    if (!status.equals("success")) {

                        Toast.makeText(Signup_merchant_businessInfo.this, resp, Toast.LENGTH_LONG).show();
                        signUpButton.buttonFinished();
                    }else {
                        signUpButton.buttonFinished();
                        Toast.makeText(Signup_merchant_businessInfo.this, "Sub Account created", Toast.LENGTH_LONG).show();
                    }
                    
                }


            }

            @Override
            public void onFailure(Call<CreateSubAccountResponse> call, Throwable t) {

            }
        });



    }

    private void openAlertDialog() {
        dialog.show();
    }
    private boolean isValidAccountNumber(){
        etAccountNo = accountNo.getText().toString().trim();
        validateAccount();
        if (etAccountNo.isEmpty()){

            accountNo.setError("Field cannot be empty");

                return false;
        }else {
            accountNo.setError(null);
            return true;
        }
    }
}