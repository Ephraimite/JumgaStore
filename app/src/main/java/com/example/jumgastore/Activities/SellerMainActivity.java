package com.example.jumgastore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.jumgastore.Fragments.SellerFragmentHome;
import com.example.jumgastore.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SellerMainActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);

        mBottomNavigationView = findViewById(R.id.bottomnav);


        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.home) {
                SellerFragmentHome fragment = new SellerFragmentHome();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.commit();
            }
            if (id == R.id.logout) {

                Intent intent = new Intent(SellerMainActivity.this, LoginActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            if (id == R.id.add_new_product) {
                startActivity(new Intent(SellerMainActivity.this, CategoryActivity.class));
            }

            return true;

        });
        mBottomNavigationView.setSelectedItemId(R.id.home);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        return true;
    }

}