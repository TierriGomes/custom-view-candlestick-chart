package com.tierriapps.tradestrategytester.customviews.candlechartview.indicators

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.tierriapps.tradestrategytester.customviews.candlechartview.CandleChartViewModel

class MoveAverage(val viewModel: CandleChartViewModel, val period: Int): Indicators {
    val yPercent = viewModel.areaYForCandles/100
    val paint = Paint().apply {
        color = Color.YELLOW
        strokeWidth = 2f
    }
    override fun drawIndicator(canvas: Canvas){
        val firstC = calcAverage(viewModel.fullListOfCandles.indexOf(viewModel.visibleCandles[0]))
        var lastY = calc(firstC)
        for (candle in viewModel.visibleCandles){
            val average = calcAverage(viewModel.fullListOfCandles.indexOf(candle))
            val y = calc(average)
            val xs = candle.xRange.first.toFloat()
            val xe = candle.xRange.last.toFloat()+5f
            canvas.drawLine(xs, lastY, xe, y, paint)
            lastY = y
        }
    }

    private fun calcAverage(candlePositionInFullList: Int): Float{
        var sum = 0f
        for (c in candlePositionInFullList-period..candlePositionInFullList){
            val safeC = if (c >= 0) c else 0
            sum += viewModel.fullListOfCandles[c].close
        }
        return sum/period
    }
    private fun calc(value: Float): Float{
        val onePercent = (viewModel.maxPrice - viewModel.minPrice) / 100
        val pct = (value - viewModel.minPrice) / onePercent // quantas vezes cabe 1 por cento aqui?
        return (viewModel.areaYForCandles - (pct * yPercent)).toFloat()
    }
}