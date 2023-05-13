package com.tierriapps.tradestrategytester.customviews.candlechartview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tierriapps.tradestrategytester.customviews.candlechartview.indicators.Indicators
import com.tierriapps.tradestrategytester.utils.arredonda

class CandleChartViewModel(
    val fullListOfCandles: List<CandleDataCCV>,
    var totalHeight: Float,
    var totalWidth: Float
): ViewModel() {
    // DATA VALUES
    var paperName = ""
    val visibleCandles = mutableListOf<CandleDataCCV>()
    var gridPriceValues = listOf<Double>()
    var maxPrice = 0.0
    var minPrice = 0.0
    var maxVolume = 0
    var minVolume = 0
    private var firstCandleId = fullListOfCandles.lastIndex - 25
    private var lastCandleId = fullListOfCandles.lastIndex


    // RENDER DATA
    val indicators = mutableListOf<Indicators>()
    var areaYForCandles = totalHeight*0.80
    var areaYForVolume = totalHeight*0.20
    var candleWidth = 20f
    var candleMargin = 5f
    var selectedCandle: CandleDataCCV? = null
    private var zoom = 0f

    init {
        var c = 1
        for(i in firstCandleId..lastCandleId){
            val candle = fullListOfCandles[i]
            candle.xRange = c*25-25..c*25
            visibleCandles.add(candle)
            c ++
        }
        setMaxAndMin()
    }

    fun rightMove(){
        if (lastCandleId+1 < fullListOfCandles.size){
            lastCandleId ++
            firstCandleId ++
            visibleCandles.removeFirst()
            visibleCandles.add(fullListOfCandles[lastCandleId])
            setMaxAndMin()
        }
    }

    fun leftMove(){
        if(firstCandleId-1 >= 0){
            lastCandleId --
            firstCandleId --
            visibleCandles.removeLast()
            visibleCandles.add(0, fullListOfCandles[firstCandleId])
            setMaxAndMin()
        }
    }

    fun getSelectedCandleOrNull(x: Int, y: Int): CandleDataCCV?{
        for(candle in visibleCandles){
            if (x in candle.xRange && y in candle.yRange){
                selectedCandle = candle
                return selectedCandle
            }
        }
        selectedCandle = null
        return null
    }

    fun zoomMove(moreOrMinus: Boolean){
        if (moreOrMinus && zoom < 20){
            zoom += 0.5f
            candleWidth += 0.5f
        }
        else if(zoom > -10){
            zoom -= 0.5f
            candleWidth -= 0.5f
        }
        when(zoom){
            in 15.1f..20f -> adjustByZoom(14)
            in 10.1f..15f -> adjustByZoom(17)
            in 5.1f..10f -> adjustByZoom(20)
            in 2.6f..5f -> adjustByZoom(23)

            in -1.5f..2.5f -> adjustByZoom(25)

            in -2.5f..1.51f -> adjustByZoom(30)
            in -5f..2.51f -> adjustByZoom(35)
            in -7.5f..5.01f -> adjustByZoom(40)
            in -10f..7.51f -> adjustByZoom(45)
        }
        setMaxAndMin()
    }


    // OBTEM OS VALORES DE PREÇO E VOLUME MAXIMOS DO PERIODO
    // E TAMBEM A LISTA DE VALORES ARREDONDADOS PRO GRID
    private fun setMaxAndMin(){
        var max: Float? = null
        var min: Float? = null
        var vmax: Int? = null
        var vmin: Int? = null
        for(c in visibleCandles){
            if (max == null || c.high > max){
                max = c.high
            }
            if (min == null || c.low < min){
                min = c.low
            }
            if (vmax == null || c.volume > vmax){
                vmax = c.volume
            }
            if (vmin == null || c.volume < vmin){
                vmin = c.volume
            }
        }
        gridPriceValues = arredonda(max?:0f, min?:0f)
        maxVolume = vmax?: 0
        minVolume = vmin?: 0
        maxPrice = gridPriceValues[0]
        minPrice = gridPriceValues[gridPriceValues.lastIndex]
    }

    // UTILIZADA PELA FUNÇÃO DE ZOOM PARA ALTERAR A LISTA DE VISIBLECANDLES
    private fun adjustByZoom(sizeWanted: Int){
        if(visibleCandles.size == sizeWanted){
            return
        }
        if (visibleCandles.size < sizeWanted && lastCandleId < fullListOfCandles.lastIndex){
            lastCandleId ++
            visibleCandles.add(fullListOfCandles[lastCandleId])
        }
        if(visibleCandles.size < sizeWanted && firstCandleId > 0){
            firstCandleId --
            visibleCandles.add(0, fullListOfCandles[firstCandleId])
        }
        if (visibleCandles.size > sizeWanted){
            visibleCandles.removeLast()
            visibleCandles.removeFirst()
            firstCandleId ++
            lastCandleId --
        }
        adjustByZoom(sizeWanted)
    }
    class ChartViewModelFactory(
        private val listOfCandles: List<CandleDataCCV>,
        var height: Float,
        var width: Float): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CandleChartViewModel::class.java)){
                return CandleChartViewModel(listOfCandles, height, width) as T
            }
            throw java.lang.IllegalArgumentException("invalid class from viewmodel")
        }
    }
}