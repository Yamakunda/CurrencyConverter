package com.example.currencyconverter

data class CurrencyData(
    val result: Boolean,
    val terms_of_use: String,
    val documentation: String,
    val supported_codes: List<List<String>>,
)
data class LatestRateData (
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
    val rates: Map<String, Double>
)
