package com.example.jumgastore.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.jumgastore.R;

public class CategoryActivity extends AppCompatActivity {

    private ImageView tShirts, sportTShirts, femaleDresses, sweaters, glasses;
    private ImageView hatsCaps, walletBagPurses, shoes, headPhone, laptops;
    private ImageView watches, mobilePhones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        tShirts = findViewById(R.id.category_t_shirts);
        sportTShirts = findViewById(R.id.category_sport_shirt);
        femaleDresses = findViewById(R.id.category_female_dresses);
        sweaters = findViewById(R.id.category_sweaters);
        glasses = findViewById(R.id.category_glasses);
        hatsCaps = findViewById(R.id.category_hats_caps);
        walletBagPurses = findViewById(R.id.category_bags_wallets);
        shoes = findViewById(R.id.category_shoes);
        headPhone = findViewById(R.id.category_headphones_handfree);
        laptops = findViewById(R.id.category_laptops);
        watches = findViewById(R.id.category_watches);
        mobilePhones = findViewById(R.id.category_mobile_phones);


        tShirts.setOnClickListener(v -> {

            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "TShirts");
            startActivity(intent);

        });
        sportTShirts.setOnClickListener(v -> {

            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "SportTShirts");
            startActivity(intent);

        });
        femaleDresses.setOnClickListener(v -> {

            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "FemaleDresses");
            startActivity(intent);

        });
        sweaters.setOnClickListener(v -> {

            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Sweaters");
            startActivity(intent);

        });
        glasses.setOnClickListener(v -> {

            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Glasses");
            startActivity(intent);

        });
        hatsCaps.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "HatsCaps");
            startActivity(intent);

        });
        walletBagPurses.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "WalletBags");
            startActivity(intent);

        });
        shoes.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Shoes");
            startActivity(intent);

        });
        headPhone.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Headphone Handfree");
            startActivity(intent);

        });
        laptops.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Laptops");
            startActivity(intent);

        });
        watches.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Watches");
            startActivity(intent);

        });
        mobilePhones.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, SellerAddNewProductActivity.class);
            intent.putExtra("category", "Mobile Phones");
            startActivity(intent);

        });

    }
}