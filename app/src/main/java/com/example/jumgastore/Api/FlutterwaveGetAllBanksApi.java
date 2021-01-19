package com.example.jumgastore.Api;

import com.example.jumgastore.Model.BanksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface FlutterwaveGetAllBanksApi {

    @GET("banks/{country}")
    Call<BanksResponse> getListOfBanks(@Path("country") String countryBanks);

}
