package com.tierriapps.tradestrategytester.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.JsonObject
import com.tierriapps.tradestrategytester.constants.CoinSymbol
import com.tierriapps.tradestrategytester.constants.CryptoSymbol
import com.tierriapps.tradestrategytester.constants.StockSymbol
import com.tierriapps.tradestrategytester.data.apirest.ApiMethods
import com.tierriapps.tradestrategytester.data.localstorage.ChartDAO
import com.tierriapps.tradestrategytester.data.localstorage.convertToCharEntity
import com.tierriapps.tradestrategytester.data.models.CandleData
import com.tierriapps.tradestrategytester.data.models.ChartData
import com.tierriapps.tradestrategytester.data.models.convertToChartData
import com.tierriapps.tradestrategytester.utils.catchFirstSymbol
import com.tierriapps.tradestrategytester.utils.catchLastSymbol
import com.tierriapps.tradestrategytester.utils.unifySymbols
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.measureTimeMillis

class RepositoryBuilder(private val apiMethods: ApiMethods, private val chartDAO: ChartDAO
    ): Repository {
    private var today = ""
    private val mapOfMemberNames = mapOf<String, List<String>>(
        "STOCK" to listOf<String>(
            "Time Series (Daily)",
            "Weekly Adjusted Time Series",
            "Monthly Adjusted Time Series"),
        "FOREX" to listOf<String>(
            "Time Series FX (Daily)",
            "Time Series FX (Weekly)",
            "Time Series FX (Monthly)"),
        "CRYPTO" to listOf<String>(
            "Time Series (Digital Currency Daily)",
            "Time Series (Digital Currency Weekly)",
            "Time Series (Digital Currency Monthly)"))

    init {
        val date = Date()
        val dateFormat = SimpleDateFormat("yyyy/MM/dd")
        today = dateFormat.format(date)
    }

    override suspend fun getAllCharts(): List<ChartData> {
        return coroutineScope{
            val chartEntityList = chartDAO.getAllCharts()
            val chartDataList = mutableListOf<ChartData>()
            if (chartEntityList != null){
                for (c in chartEntityList){
                    chartDataList.add(c.convertToChartData())
                }
            }
            chartDataList
        }
    }

    override suspend fun getStockBySymbol(symbol: StockSymbol): ChartData? {
        return withContext(Dispatchers.IO) {
            val chart = chartDAO.getChartBySymbol(symbol.symbol)
            if (chart != null){
                Log.d("repository", "data from local")
                chart.convertToChartData()
            }
            val c = getFromApi(symbol.symbol, "STOCK")
            chartDAO.insertChart(c!!.convertToCharEntity())
            c
        }
    }

    override suspend fun getForexBySymbol(fromCoin: CoinSymbol, toCoin: CoinSymbol): ChartData? {
        return withContext(Dispatchers.IO) {
            val symbol = unifySymbols(fromCoin.symbol, toCoin.symbol)
            val chart = chartDAO.getChartBySymbol(symbol)
            if (chart != null && chart.lastUpdate == today){
                chart.convertToChartData()
            }
            val c = getFromApi(symbol, "FOREX")
            chartDAO.insertChart(c!!.convertToCharEntity())
            c
        }
    }

    override suspend fun getCryptoBySymbol(fromCrypto: CryptoSymbol, toCoinOrCrypto: String): ChartData? {
        return withContext(Dispatchers.IO) {
            val symbol = unifySymbols(fromCrypto.symbol, toCoinOrCrypto)
            val chart = chartDAO.getChartBySymbol(symbol)
            chart?.convertToChartData()


        }
    }

    override suspend fun deleteChartBySymbol(symbol: String) {
        chartDAO.deleteChartBySymbol(symbol)
    }

    override suspend fun updateChart(chart: ChartData) {
        chartDAO.updateChart(chart.convertToCharEntity())
    }

    override suspend fun insertChart(chart: ChartData) {
        chartDAO.insertChart(chart.convertToCharEntity())
    }


    // essa função chama 3 coroutines pra fazer 3 chamadas a api, e obter os graficos diario,
    // semanal e mensal de um determinado simbolo
    private suspend fun getFromApi(symbol: String, memberType: String): ChartData? {
        val memberNames = mapOfMemberNames[memberType]
        val s1 = catchFirstSymbol(symbol)
        val s2 = catchLastSymbol(symbol)

        return try {
            val chartData = ChartData(symbol)
            withContext(Dispatchers.IO) {

                launch {
                    var jsonDaily: JsonObject? = null
                    when(memberType) {
                        "STOCK" -> jsonDaily = apiMethods.getStockDaily(symbol)
                        "FOREX" -> jsonDaily = apiMethods.getForexDaily(s1, s2)
                        "CRYPTO" -> jsonDaily = apiMethods.getCryptoDaily(s1, s2)
                    }
                    chartData.dailyList = parseCandleData(jsonDaily!!, memberNames!![0])}

                launch {
                    var jsonWeekly: JsonObject? = null
                    when(memberType) {
                        "STOCK" -> jsonWeekly = apiMethods.getStockWeekly(symbol)
                        "FOREX" -> jsonWeekly = apiMethods.getForexWeekly(s1, s2)
                        "CRYPTO" -> jsonWeekly = apiMethods.getCryptoWeekly(s1, s2)
                    }
                    chartData.weeklyList = parseCandleData(jsonWeekly!!, memberNames!![1])
                }
                launch {
                    var jsonMonthly: JsonObject? = null
                    when(memberType){
                        "STOCK" -> jsonMonthly = apiMethods.getStockMonthly(symbol)
                        "FOREX" -> jsonMonthly = apiMethods.getForexMonthly(s1, s2)
                        "CRYPTO" -> jsonMonthly = apiMethods.getCryptoMonthly(s1, s2)
                    }
                    chartData.monthlyList = parseCandleData(jsonMonthly!!, memberNames!![2])
                }

            }

            // retornando o chart data com as listas preenchidas
            chartData
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    // função pra fazer o parse dos dados
    private fun parseCandleData(apiData: JsonObject, memberName: String): List<CandleData> {
        val listOfCandles = mutableListOf<CandleData>()
        for((date, values) in apiData.getAsJsonObject(memberName)?.entrySet()!!){
            val listforvalues = mutableListOf<Float>()
            for (v in values.asJsonObject.entrySet()){
                listforvalues.add(v.value.asFloat)
            }
            val candleData = CandleData(
                date = date,
                open = listforvalues[0],
                high = listforvalues[2],
                low = listforvalues[4],
                close = listforvalues[6],
                //adjustedClose = if(listforvalues.size > 4) listforvalues[4] else 0f,
                volume = if(listforvalues.size > 8) listforvalues[8].toInt() else 0,
                dividendAmount = if(listforvalues.size > 10) listforvalues[6] else 0f,
                splitCoeficient = if(listforvalues.size == 10) listforvalues[7] else 0f
                )
            listOfCandles.add(candleData)
        }
        return listOfCandles
    }
}