package com.tierriapps.tradestrategytester.data.localstorage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tierriapps.tradestrategytester.data.models.CandleData
import com.tierriapps.tradestrategytester.data.models.ChartData


@Entity("chart_entity")
data class ChartEntity (
    @PrimaryKey @ColumnInfo("symbol")val symbol: String,
    @ColumnInfo("last_update") val lastUpdate: String,
    @ColumnInfo("daily_list") var dailyList: List<CandleData>,
    @ColumnInfo("weekly_list") var weeklyList: List<CandleData>,
    @ColumnInfo("monthly_list") var monthlyList: List<CandleData>,
    @ColumnInfo("description") var description: String = ""
        )

fun ChartData.convertToCharEntity(): ChartEntity{
    return with(this){
        ChartEntity(
            symbol = this.symbol,
            lastUpdate = this.lastUpdate,
            dailyList = this.dailyList,
            weeklyList = this.weeklyList,
            monthlyList = this.monthlyList,
            description = this.description)
    }
}

class ListOfCandlesTypeConverter(){
    @TypeConverter
    fun toGson(list: List<CandleData>):String {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromGson(string: String): List<CandleData>{
        if(string == ""){
            return listOf()
        }
        val type = object : TypeToken<List<CandleData>>(){}.type
        return Gson().fromJson(string, type)
    }
}