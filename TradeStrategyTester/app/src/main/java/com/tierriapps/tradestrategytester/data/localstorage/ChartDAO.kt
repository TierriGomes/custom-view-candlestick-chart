package com.tierriapps.tradestrategytester.data.localstorage

import androidx.room.*

@Dao
interface ChartDAO {

    @Query("SELECT * FROM chart_entity")
    suspend fun getAllCharts():List<ChartEntity>?

    @Query("SELECT * FROM chart_entity WHERE symbol = :symbol")
    suspend fun getChartBySymbol(symbol: String): ChartEntity?

    @Query("DELETE FROM chart_entity WHERE symbol = :symbol")
    suspend fun deleteChartBySymbol(symbol: String)

    @Update
    suspend fun updateChart(chart: ChartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChart(chart: ChartEntity)
}