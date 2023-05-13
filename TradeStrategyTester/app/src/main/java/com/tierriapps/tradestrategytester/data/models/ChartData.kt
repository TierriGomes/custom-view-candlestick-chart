package com.tierriapps.tradestrategytester.data.models

import androidx.room.ColumnInfo
import com.tierriapps.tradestrategytester.data.localstorage.ChartEntity

data class ChartData (
    val symbol: String,
    var lastUpdate: String = "",
    var dailyList: List<CandleData> = listOf(),
    var weeklyList: List<CandleData> = listOf(),
    var monthlyList: List<CandleData> = listOf(),
    var description: String = ""
    )

fun ChartEntity.convertToChartData(): ChartData{
    return with(this){
        ChartData(
            symbol = this.symbol,
            lastUpdate = this.lastUpdate,
            dailyList = this.dailyList,
            weeklyList = this.weeklyList,
            monthlyList = this.monthlyList,
            description = this.description
        )
    }
}

