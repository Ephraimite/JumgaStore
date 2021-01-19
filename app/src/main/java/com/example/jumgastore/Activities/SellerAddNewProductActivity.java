package com.example.jumgastore.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jumgastore.Model.CurrentUser;
import com.example.jumgastore.Model.Merchants;
import com.example.jumgastore.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;

public class SellerAddNewProductActivity extends AppCompatActivity {

    private StorageReference ProductImageRef;
    TextView tv_selectImage;
    private String CategoryName;
    Button btnAddNewProduct;
    EditText etProductname, etProductDesc, etPrice;

    private ImageView uploadedImage;
    public static final int GalleryPick = 1;
    ProgressBar progressBar;
    private Uri imageUri;
    String categoryName, productName, description, Productprice, saveCurrentDate, saveCurrentTime;
    private String productRandomKey, downloadImageUrl;
    private final String deliveryAgentID = "RS_B0970D7D10EC36EE083BAF66E352422B";

    private final String sellerName ="";

    private DatabaseReference ProductRef;
    private DatabaseReference sellerRef;
    private String sellerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_new_product);

        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("product images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("products");


        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();

        btnAddNewProduct = findViewById(R.id.addNewProduct);
        etProductname = findViewById(R.id.productName);
        etProductDesc = findViewById(R.id.productDesc);
        etPrice = findViewById(R.id.productPrice);
        tv_selectImage = findViewById(R.id.selectImage);
        uploadedImage = findViewById(R.id.selectedProductImage);
        progressBar = findViewById(R.id.myProgressBar);


        tv_selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });

        sellerRef.child(CurrentUser.currentMerchant.getPhone())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String sid = snapshot.child("SubAccountID").getValue().toString();
                            sellerID = sid;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void ValidateProductData() {
        description = etProductDesc.getText().toString();
        Productprice = etPrice.getText().toString();
        productName = etProductname.getText().toString();

        if (imageUri== null){
            Toast.makeText(this, "Product image is mandatory", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(description)){
            Toast.makeText(this, "Please write product description", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(Productprice)){
            Toast.makeText(this, "Please type product price", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(productName)){
            Toast.makeText(this, "Pls write product name...", Toast.LENGTH_LONG).show();
        }else {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {
    progressBar.setVisibility(View.VISIBLE);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        StorageReference filePath = ProductImageRef.child(imageUri.getLastPathSegment()
                + productRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(e -> {

            String message = e.toString();
            Toast.makeText(this,"Error: " + message, Toast.LENGTH_LONG).show();


        }).addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(this, "Product uploaded sucessfully", Toast.LENGTH_LONG).show();

            Task<Uri> uriTask = uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()){
                    throw task.getException();

                }
                downloadImageUrl = filePath.getDownloadUrl().toString();
                return filePath.getDownloadUrl();

            }).addOnCompleteListener(task -> {

                if (task.isSuccessful()){
                    downloadImageUrl = task.getResult().toString();
                    Toast.makeText(SellerAddNewProductActivity.this, "got Product image .....", Toast.LENGTH_SHORT).show();

                    saveProductInfoToDataBase();
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    private void saveProductInfoToDataBase() {
        HashMap<String, Object> productMap = new HashMap<>();

        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", description);
        productMap.put("Image", downloadImageUrl);
        productMap.put("category", categoryName);
        productMap.put("productname", productName);
        productMap.put("shopName", productName);
        productMap.put("price", Productprice);
        productMap.put("sid", sellerID);
        productMap.put("phone", CurrentUser.currentMerchant.getPhone());
        

        ProductRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(task -> {

            if (task.isSuccessful()){
                Toast.makeText(this, "Product added successfully..", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(SellerAddNewProductActivity.this, SellerMainActivity.class));
            }else {
                String message = task.getException().toString();
                Toast.makeText(this, "Error:" + message, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void openGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode == RESULT_OK && data !=null){
            imageUri = data.getData();
            uploadedImage.setImageURI(imageUri);
        }
    }


}