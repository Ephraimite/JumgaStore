package com.example.jumgastore.PaymentGateways;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jumgastore.Activities.MerchantLogin;
import com.example.jumgastore.Constants;
import com.example.jumgastore.R;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.flutterwave.raveandroid.rave_presentation.RavePayManager;

public class FlutterwaveActivity extends AppCompatActivity {

    String email, name, phoneNumber;
    int amount;
    String narration = "payment for account approval";
    String txRef;
    String country;
    String currency;

    private RaveUiManager raveUiManager;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutterwave);


        bundle = getIntent().getExtras();


        raveUiManager = new RaveUiManager(FlutterwaveActivity.this);
        InitializePayment();
    }

    private void InitializePayment() {

        amount = bundle.getInt("amount");
        name = bundle.getString("name");
        phoneNumber = bundle.getString("phone");
        email = bundle.getString("email");


        raveUiManager.setAmount(amount)
                .setCurrency("NGN")
                .setfName(name)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setNarration(narration)
                .setEncryptionKey(Constants.ENCRYPTION_KEY)
                .setPublicKey(Constants.PUBLIC_KEY)
                .setTxRef(System.currentTimeMillis() + "REF")
                .acceptCardPayments(true)
                .acceptAccountPayments(true)
                .acceptUssdPayments(true)
                .acceptBarterPayments(true)
                .acceptBankTransferPayments(true)
                .acceptGHMobileMoneyPayments(true)
                .acceptMpesaPayments(true)
                .acceptUkPayments(true)
                .allowSaveCardFeature(false)
                .onStagingEnv(true)
                .shouldDisplayFee(true)
                .initialize();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
                openDialog();

            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "An Error Occured", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FlutterwaveActivity.this);
        builder.setTitle("Payment Successful");
        builder.setMessage("Your payment has been received, pls be patient while admin confirms and approve your store");
        builder.setPositiveButton("Ok", (dialog, which) -> {
            dialog.dismiss();
            startActivity(new Intent(FlutterwaveActivity.this, MerchantLogin.class));
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}