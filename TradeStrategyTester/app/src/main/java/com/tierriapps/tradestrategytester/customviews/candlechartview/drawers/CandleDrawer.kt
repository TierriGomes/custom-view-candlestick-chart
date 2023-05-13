package com.tierriapps.tradestrategytester.customviews.candlechartview.drawers

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.tierriapps.tradestrategytester.R
import com.tierriapps.tradestrategytester.customviews.candlechartview.CandleChartViewModel

class CandleDrawer (val viewModel: CandleChartViewModel){
    private val paint = Paint().apply { strokeWidth = 2f }
    private val yPercent = viewModel.areaYForCandles/100

    // FUNÇÃO A SER CHAMADA NO ONDRAWN PARA DESENHAR OS CANDLES
    fun drawnCandles(canvas: Canvas){
        var startX = viewModel.candleMargin
        var endX = viewModel.candleWidth + startX
        var middleX = (startX + endX) / 2
        var startY = 0f
        var endY = 0f
        for (candle in viewModel.visibleCandles){
            startY = calc(candle.high)
            endY = calc(candle.low)
            // se for um candle de alta
            if (candle.open < candle.close){
                paint.color = Color.GREEN
                canvas.drawRect(startX, calc(candle.close), endX, calc(candle.open), paint)
            }
            // se for um candle de baixa
            else{
                paint.color = Color.RED
                canvas.drawRect(startX, calc(candle.open), endX, calc(candle.close), paint)
            }
            // se for o candle selecionado
            if (candle == viewModel.selectedCandle){
                paint.color = Color.MAGENTA
                canvas.drawRect(startX, calc(candle.close), endX, calc(candle.open), paint)
            }
            canvas.drawLine(middleX, startY, middleX, endY, paint)

            // desenha o volume
            paint.color = Color.BLUE
            canvas.drawRect(startX, volumeCalc(candle.volume), endX, viewModel.totalHeight, paint)

            candle.xRange = startX.toInt()..endX.toInt()
            candle.yRange = startY.toInt()..endY.toInt()

            startX += viewModel.candleWidth+ viewModel.candleMargin // aonde terminou o candle anterior + margin
            endX = startX + viewModel.candleWidth // o nova posição de inicio + largura
            middleX = (startX + endX) / 2

        }
    }




    private fun calc(value: Float): Float{
        val onePercent = (viewModel.maxPrice - viewModel.minPrice) / 100
        val pct = (value - viewModel.minPrice) / onePercent // quantas vezes cabe 1 por cento aqui?
        return (viewModel.areaYForCandles - (pct * yPercent)).toFloat()
    }

    fun volumeCalc(valor: Int): Float{
        val percent: Float = valor.toFloat()/viewModel.maxVolume
        val result = viewModel.areaYForVolume*percent
        return viewModel.totalHeight-result.toFloat()
    }

}