package com.tierriapps.tradestrategytester.data.apirest

import androidx.annotation.StringDef
import com.google.gson.JsonObject
import com.tierriapps.tradestrategytester.constants.CoinSymbol
import com.tierriapps.tradestrategytester.constants.CryptoSymbol
import com.tierriapps.tradestrategytester.constants.StockSymbol
import com.tierriapps.tradestrategytester.data.apirest.ApiQuery
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

enum class ApiQuery(val string: String){
    API_KEY("KT4OIPSMEOBVPBBA"),
    OUTPUTSIZE("full"),

    STOCKS_DAILY("TIME_SERIES_DAILY_ADJUSTED"),
    STOCKS_WEEKLY("TIME_SERIES_WEEKLY_ADJUSTED"),
    STOCKS_MONTHLY("TIME_SERIES_MONTHLY_ADJUSTED"),
    FOREX_DAILY("FX_DAILY"),
    FOREX_WEEKLY("FX_WEEKLY"),
    FOREX_MONTHLY("FX_MONTHLY"),
    CRYPTO_DAILY("DIGITAL_CURRENCY_DAILY"),
    CRYPTO_WEEKLY("DIGITAL_CURRENCY_WEEKLY"),
    CRYPTO_MONTHLY("DIGITAL_CURRENCY_MONTHLY")
}

interface ApiMethods {
    // STOCKS
    @GET("/query")
    suspend fun getStockDaily(
        @Query("symbol") symbol: String,
        @Query("function") function: String = ApiQuery.STOCKS_DAILY.string,
        @Query("outputsize") outputSize: String = ApiQuery.OUTPUTSIZE.string,
        @Query("apikey") apikey: String = ApiQuery.API_KEY.string
    ): JsonObject

    @GET("/query")
    suspend fun getStockWeekly(
        @Query("symbol") symbol: String,
        @Query("function") function: String = ApiQuery.STOCKS_WEEKLY.string,
        @Query("outputsize") outputSize: String = ApiQuery.OUTPUTSIZE.string,
        @Query("apikey") apikey: String = ApiQuery.API_KEY.string
    ): JsonObject

    @GET("/query")
    suspend fun getStockMonthly(
        @Query("symbol") symbol: String,
        @Query("function") function: String = ApiQuery.STOCKS_MONTHLY.string,
        @Query("outputsize") outputSize: String = ApiQuery.OUTPUTSIZE.string,
        @Query("apikey") apikey: String = ApiQuery.API_KEY.string
    ): JsonObject

    // FOREX
    @GET("/query")
    suspend fun getForexDaily(
        @Query("from_symbol") fromCoin: String,
        @Query("to_symbol") toCoin: String,
        @Query("function") function: String = ApiQuery.FOREX_DAILY.string,
        @Query("outputsize") outputSize: String = ApiQuery.OUTPUTSIZE.string,
        @Query("apikey") apikey: String = ApiQuery.API_KEY.string
    ): JsonObject

    @GET("/query")
    suspend fun getForexWeekly(
        @Query("from_symbol") fromCoin: String,
        @Query("to_symbol") toCoin: String,
        @Query("function") function: String = ApiQuery.FOREX_WEEKLY.string,
        @Query("outputsize") outputSize: String = ApiQuery.OUTPUTSIZE.string,
        @Query("apikey") apikey: String = ApiQuery.API_KEY.string
    ): JsonObject

    @GET("/query")
    suspend fun getForexMonthly(
        @Query("from_symbol") fromCoin: String,
        @Query("to_symbol") toCoin: String,
        @Query("function") function: String = ApiQuery.FOREX_MONTHLY.string,
        @Query("outputsize") outputSize: String = ApiQuery.OUTPUTSIZE.string,
        @Query("apikey") apikey: String = ApiQuery.API_KEY.string
    ): JsonObject

    // CRYPTO
    @GET("/query")
    suspend fun getCryptoDaily(
        @Query("symbol") fromCrypto: String,
        @Query("market") toCoin: String,
        @Query("function") function: String = ApiQuery.CRYPTO_DAILY.string,
        @Query("apikey") apikey: String = ApiQuery.API_KEY.string
    ): JsonObject
    @GET("/query")
    suspend fun getCryptoWeekly(
        @Query("symbol") fromCrypto: String,
        @Query("market") toCoin: String,
        @Query("function") function: String = ApiQuery.CRYPTO_WEEKLY.string,
        @Query("apikey") apikey: String = ApiQuery.API_KEY.string
    ): JsonObject
    @GET("/query")
    suspend fun getCryptoMonthly(
        @Query("symbol") fromCrypto: String,
        @Query("market") toCoin: String,
        @Query("function") function: String = ApiQuery.CRYPTO_MONTHLY.string,
        @Query("apikey") apikey: String = ApiQuery.API_KEY.string
    ): JsonObject
}
