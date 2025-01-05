package com.example.currencyconverter

data class ConvertData(
    val success: Boolean,
    val query: query,
    val info: Info,
    val historical: String,
    val date: String,
    val result: Double
)
data class query(
    val from: String,
    val to: String,
    val amount: Int
)
data class Info(
    val timestamp: Int,
    val rate: Double
)

data class CurrencyData(
    val success: Boolean,
    val symbols: Map<String, String>
)
data class LatestRateData (
    val success: Boolean,
    val timestamp: Int,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
