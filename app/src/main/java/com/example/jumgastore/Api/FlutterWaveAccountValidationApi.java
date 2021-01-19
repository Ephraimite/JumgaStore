package com.example.jumgastore.Api;

import com.example.jumgastore.Model.ValidateAccountNo;
import com.example.jumgastore.Model.ValidateAccountRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FlutterWaveAccountValidationApi {

    @POST("accounts/resolve")
    Call<ValidateAccountNo> validateUserAccount(@Body ValidateAccountRequest validateAccountRequest);
}
