package com.tierriapps.tradestrategytester.viewmodels


import androidx.room.Query
import androidx.room.Update
import com.tierriapps.tradestrategytester.constants.CoinSymbol
import com.tierriapps.tradestrategytester.constants.CryptoSymbol
import com.tierriapps.tradestrategytester.constants.StockSymbol
import com.tierriapps.tradestrategytester.data.localstorage.ChartEntity
import com.tierriapps.tradestrategytester.data.models.ChartData


interface Repository {

    suspend fun getAllCharts():List<ChartData>

    suspend fun getStockBySymbol(symbol: StockSymbol): ChartData?

    suspend fun getForexBySymbol(fromCoin: CoinSymbol, toCoin: CoinSymbol): ChartData?

    suspend fun getCryptoBySymbol(fromCrypto: CryptoSymbol, toCoinOrCrypto: String): ChartData?

    suspend fun deleteChartBySymbol(symbol: String)

    suspend fun updateChart(chart: ChartData)

    suspend fun insertChart(chart: ChartData)

}