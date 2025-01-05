package com.example.currencyconverter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {
    @GET("latest")
    suspend fun getLatestRate(
        @Query("access_key") accessKey: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): LatestRateData

    @GET("symbols") // Your endpoint
    suspend fun getCurrency(@Query("access_key") apiKey: String): CurrencyData


}