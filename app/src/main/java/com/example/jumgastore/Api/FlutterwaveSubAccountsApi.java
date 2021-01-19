package com.example.jumgastore.Api;

import com.example.jumgastore.Model.BanksResponse;
import com.example.jumgastore.Model.CreateSubAccountRequest;
import com.example.jumgastore.Model.CreateSubAccountResponse;
import com.example.jumgastore.Model.GetAllSubAccountResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FlutterwaveSubAccountsApi {

    @POST("subaccounts")
    Call<CreateSubAccountResponse> createSubMerchantAccounts (@Body CreateSubAccountRequest createSubAccountRequest);

    @GET("subaccounts")
    Call<GetAllSubAccountResponse> getAllSubAccounts();
}
