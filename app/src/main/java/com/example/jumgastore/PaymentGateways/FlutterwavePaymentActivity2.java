package com.example.jumgastore.PaymentGateways;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jumgastore.Activities.HomeActivity;
import com.example.jumgastore.Api.FlutterwaveSubAccountsApi;
import com.example.jumgastore.Constants;
import com.example.jumgastore.Model.Cart;
import com.example.jumgastore.Model.CurrentUser;
import com.example.jumgastore.R;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.flutterwave.raveandroid.rave_java_commons.SubAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FlutterwavePaymentActivity2 extends AppCompatActivity {

    private RaveUiManager raveUiManager;
    private int finalPrice;
    private int deliveryPrice;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;
    private String narration = "Payment for goods";

    ArrayList<Cart> selectedCartItems = new ArrayList<>();
    private final ArrayList<SubAccount> subAccounts = new ArrayList<>();
    private double deliverySplitValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutterwave_payment2);

         Bundle bundle = getIntent().getExtras();

        finalPrice = bundle.getInt("finalPrice");
        deliveryPrice = bundle.getInt("deliveryPrice");
        buyerName = bundle.getString("bname");
        buyerEmail = bundle.getString("bemail");
        buyerPhone = bundle.getString("bphone");
        selectedCartItems = bundle.getParcelableArrayList("newCartList1");






        for (Cart item: selectedCartItems){
            double splitValue = Double.parseDouble(item.getPrice()) * 0.1;
            SubAccount subAccount = new SubAccount(item.getCartItemID(), null,
                    SubAccount.FLAT, String.valueOf(splitValue));

            deliverySplitValue = deliveryPrice * 0.1;
            String deliveryAgentSubAccountRefID = "RS_B0970D7D10EC36EE083BAF66E352422B";
            SubAccount deliveryAgentID = new SubAccount(deliveryAgentSubAccountRefID, null, SubAccount.FLAT, String.valueOf(deliverySplitValue));

            subAccounts.add(subAccount);
            subAccounts.add(deliveryAgentID);
        }


        raveUiManager = new RaveUiManager(FlutterwavePaymentActivity2.this);
        InitializePayment();

    }
    private void InitializePayment() {

        raveUiManager.setAmount(finalPrice)
                .setCurrency("NGN")
                .setfName(buyerName)
                .setEmail(buyerEmail)
                .setPhoneNumber(buyerPhone)
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
                .withTheme(R.style.RaveTheme)
                .setSubAccounts(subAccounts)
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
        AlertDialog.Builder builder = new AlertDialog.Builder(FlutterwavePaymentActivity2.this);
        builder.setTitle("Payment Successful");
        builder.setMessage("Dear" + " " + buyerName + "\n" + "Your order as been recorded, our shipping agent will contact you shortly");
        builder.setPositiveButton("Ok", (dialog, which) -> {
            dialog.dismiss();
            startActivity(new Intent(FlutterwavePaymentActivity2.this, HomeActivity.class));
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}