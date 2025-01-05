package com.example.currencyconverter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyApi {
    @GET("latest.json")
    suspend fun getLatestRate(@Query("app_id") apiKey: String): LatestRateData

    @GET("currencies.json")
    suspend fun getCurrency(@Query("app_id") apiKey: String): Map<String,String>
}