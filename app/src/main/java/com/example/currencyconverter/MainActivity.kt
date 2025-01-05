package com.example.currencyconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() , View.OnClickListener {
    private var apiKey = "94598037c3464c2ca2e9bfc18492e874"
    private val baseUrl = "https://openexchangerates.org/api/"
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(Logging()) // Add your Logging interceptor here
        .build()
    val api: MyApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MyApi::class.java)
    }

    private lateinit var Edt_1 : EditText
    private lateinit var Edt_2 : EditText
    private lateinit var Spn_1 : Spinner
    private lateinit var Spn_2 : Spinner
    private lateinit var Btn_1 : Button
    private lateinit var Btn_2 : Button
    private lateinit var Tv_3 : TextView
    lateinit var symbol : Map<String,String>
    lateinit var rate: Map<String,Double>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Edt_1 = findViewById(R.id.edt_1)
        Edt_1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isEmpty()) Edt_1.setText("0")
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Optional: Do something before the text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Optional: Do something during the text change
            }
        })
        Edt_2 = findViewById(R.id.edt_2)
        Spn_1 = findViewById(R.id.spn_1)
        Spn_1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Optional: Handle the case where nothing is selected
            }
        }
        Spn_2 = findViewById(R.id.spn_2)
        Spn_2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Optional: Handle the case where nothing is selected
            }
        }
        Btn_1 = findViewById(R.id.btn_1)
        Btn_2 = findViewById(R.id.btn_2)
        Tv_3 = findViewById(R.id.tv_3)
        Btn_1.setOnClickListener(this)
        Btn_2.setOnClickListener {
            convertCurrency()
        }
        getLatestBase()
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
                val temp2 = Spn_1.selectedItemPosition
                Spn_1.setSelection(Spn_2.selectedItemPosition)
                Spn_2.setSelection(temp2)
            }
            R.id.btn_2 -> {
                convertCurrency()
            }
        }
    }
    private fun getCurrency(){
        lifecycleScope.launch {
            try {
                val currencyData = api.getCurrency(apiKey = apiKey)
                Log.d("Currency data", "Currency data: ${currencyData.keys.toList()}")
                symbol = currencyData
                val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item,currencyData.keys.toList())
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                Spn_1.adapter = adapter
                val initialValue1 = "USD"
                Spn_1.setSelection(149)
                Spn_2.adapter = adapter
                val initialValue2 = "VND"
                Spn_2.setSelection(154)

                delay(500L)
                convertCurrency()
            } catch (e: Exception) {
                Log.e("Get currency Data", "Error fetching currency data: ${e.toString()}")
            }
        }
    }

    private fun getLatestBase(){
        lifecycleScope.launch {
            try {
                val baseRate = api.getLatestRate(apiKey = apiKey)
                rate = baseRate.rates
            } catch (e: Exception) {
                Log.e("Get currency Data", "Error fetching currency data: ${e.toString()}")
            }
        }
    }
    private fun convertCurrency(){
        if (Spn_1.selectedItem == null|| Spn_2.selectedItem == null) return
        val from = Spn_1.selectedItem.toString()
        val to = Spn_2.selectedItem.toString()
        val amount = Edt_1.text.toString().toDouble()
        lifecycleScope.launch {
            try {
                val Result = String.format("%.4f",amount * rate.getValue(to) / rate.getValue(from))
                Edt_2.setText("$Result")
                Tv_3.setText("$amount ${symbol.getValue(from)} = $Result ${symbol.getValue(to)}")
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching convert data: ${e.message}")
            }
        }
    }

}