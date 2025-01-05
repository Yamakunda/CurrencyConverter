package com.example.currencyconverter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() , View.OnClickListener {
    private val baseUrl = "https://api.exchangeratesapi.io/v1/"
    val api: MyApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)
    }
    private var apiKey = "2b9c3719455cabbf6764b0d99b54505e"
    private lateinit var Edt_1 : EditText
    private lateinit var Edt_2 : EditText
    private lateinit var Spn_1 : Spinner
    private lateinit var Spn_2 : Spinner
    private lateinit var Btn_1 : Button
    private lateinit var Btn_2 : Button
    lateinit var symbol : Map<String, String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Edt_1 = findViewById(R.id.edt_1)
        Edt_2 = findViewById(R.id.edt_2)
        Spn_1 = findViewById(R.id.spn_1)
        Spn_2 = findViewById(R.id.spn_2)
        Btn_1 = findViewById(R.id.btn_1)
        Btn_2 = findViewById(R.id.btn_2)
        Btn_1.setOnClickListener(this)
        Btn_2.setOnClickListener {
            convertCurrency()
        }
        getCurrency()

    }

    override fun onClick(v: View?) {
        val a = Edt_1.text
        val b = Edt_2.text
        when(v?.id){
            R.id.btn_1 -> {
                val temp = a
                Edt_1.setText(b.toString())
                Edt_2.setText(temp.toString())
                Spn_1.setSelection(Spn_2.selectedItemPosition)
                Spn_2.setSelection(Spn_1.selectedItemPosition)
            }
            R.id.btn_2 -> {
                convertCurrency()
            }
        }
    }
    private fun getCurrency(){
        lifecycleScope.launch {
            try {
                val currencyData = api.getCurrency(apiKey)
                symbol = currencyData.symbols
                val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item,symbol.keys.toList())
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                Spn_1.adapter = adapter
                val initialValue1 = "USD"
                val position1 = adapter.getPosition(initialValue1)
                if (position1 != -1) {
                    Spn_1.setSelection(position1)
                } else {
                    Log.e("Spinner", "Initial value not found in adapter")
                }
                Spn_2.adapter = adapter
                val initialValue2 = "VND"
                val position2 = adapter.getPosition(initialValue2)
                if (position2 != -1) {
                    Spn_2.setSelection(position2)
                } else {
                    Log.e("Spinner", "Initial value not found in adapter")
                }
                convertCurrency()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching currency data: ${e.message}")
            }
        }
    }
    private fun convertCurrency(){
        val from = Spn_1.selectedItem.toString()
        val to = Spn_2.selectedItem.toString()
        val amount = Edt_1.text.toString().toDouble()
        lifecycleScope.launch {
            try {
                val convertData = api.getLatestRate(accessKey = apiKey, base = from, symbols = to)
                Log.d("MainActivity", "Convert data: $convertData")
                val rate = convertData.rates.getValue(from)
                val Result = amount * rate
                Edt_2.setText(Result.toString())
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching convert data: ${e.message}")
            }
        }
    }
}