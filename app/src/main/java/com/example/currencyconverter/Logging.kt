package com.example.currencyconverter

import android.util.Log
import okhttp3.Response
import okhttp3.Interceptor

class Logging : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()

        try {
            val response = chain.proceed(request)
            if (!response.isSuccessful) {
                Log.e("API Error", "URL: $url, Code: ${response.code()}, Message: ${response.message()}")
            }
            return response
        } catch (e: Exception) {
            Log.e("API Error", "URL: $url, Exception: ${e.message}", e)
            throw e
        }
    }
}