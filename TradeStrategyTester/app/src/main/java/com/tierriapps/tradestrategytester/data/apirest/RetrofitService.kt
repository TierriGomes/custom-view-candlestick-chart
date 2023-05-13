package com.tierriapps.tradestrategytester.data.apirest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.alphavantage.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitService = retrofit.create(ApiMethods::class.java)
}