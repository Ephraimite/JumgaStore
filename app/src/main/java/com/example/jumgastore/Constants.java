package com.example.jumgastore;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Constants {

    public static final String FLUTTERWAVE_API_URL = "https://api.flutterwave.com/v3/";


    public static final String RAVE_API_SECRET_KEY = "FLWSECK_TEST-9a4f7549c6b40a1f632105a0b4635095-X";
   public static final String PUBLIC_KEY = "FLWPUBK_TEST-3cb8b3f8dceaf599e5b0f637702e2ae0-X"; //Get your public key from your account
    public  static final String ENCRYPTION_KEY = "FLWSECK_TEST1e1f1c7f0e62"; //Get your encryption key from your account

    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
        context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo !=null){

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                return true;

            }else return networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }else {
            return false;
        }
    }
}
