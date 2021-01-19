package com.example.jumgastore;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LoginButton {
    private CardView cardView;
    private ConstraintLayout constraintLayout;
    private ProgressBar progressBar;
    private TextView textView;

    public LoginButton(Context context, View view){
        cardView = view.findViewById(R.id.cardView);
        constraintLayout = view.findViewById(R.id.first_constriant);
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.tv_login);


    }

    public void buttonActivated(){
        progressBar.setVisibility(View.VISIBLE);
        textView.setText("Checking Credentials.....");
    }

    public void buttonFinished(){
        progressBar.setVisibility(View.GONE);
        textView.setText("Login");
    }
}
