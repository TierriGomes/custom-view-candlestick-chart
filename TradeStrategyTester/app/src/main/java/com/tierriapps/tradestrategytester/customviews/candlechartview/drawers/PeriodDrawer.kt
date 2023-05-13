package com.tierriapps.tradestrategytester.customviews.candlechartview.drawers

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.tierriapps.tradestrategytester.R
import com.tierriapps.tradestrategytester.customviews.candlechartview.CandleChartViewModel
import com.tierriapps.tradestrategytester.customviews.candlechartview.CandleDataCCV

class PeriodDrawer(
    val viewModel: CandleChartViewModel,
    val period: PeriodValue,
    val lightColor: Int,
    val darkColor: Int) {
    private val paint = Paint().apply {
        strokeWidth = 2f
        textSize = 10f
    }
    private var listOfEnds = mutableListOf<Int>()
    private var listOfColors = mutableListOf<Int>()
    private var listOfTexts = mutableListOf<String>()
    private var lastDay = -16
    private var lastMonth = -4
    private var lastYear = 0

    // date format = yyyy/mm/dd
    enum class PeriodValue {
        DAILY,
        WEEKLY,
        MONTHLY
    }

    fun drawnDateShadow(canvas: Canvas){
        setListOfEnds()
        var ant = 0f
        for((k, v) in listOfEnds.withIndex()){
            paint.textSize = 10f
            paint.color = listOfColors[k]
            val valor = if (k == listOfEnds.lastIndex) viewModel.totalWidth else v.toFloat()
            val area = valor-ant
            var textSize = paint.measureText(listOfTexts[k])
            while (area-textSize > 40f && paint.textSize < 28){
                paint.textSize ++
                textSize = paint.measureText(listOfTexts[k])
            }
            Log.d("testenow", "key: $k area: $area, textSize: $textSize, pt: ${paint.textSize}")
            val textpX = (area-textSize)/2+ant
            canvas.drawRect(ant, 0f, valor, viewModel.totalHeight, paint)
            paint.color = Color.GRAY
            canvas.drawText(listOfTexts[k], textpX, 30f, paint)
            ant = valor
        }
    }

    // Verifica se um objeto Candle pertence a um período diário
    private val isDaily: (CandleDataCCV) -> Boolean = { candle ->
        val day = candle.date.substring(8..9).toInt()
        val text = candle.date.substring(0..6)
        val validator = day/16 != lastDay/16
        if (validator && day <= 15){
            listOfTexts.add(text)
            listOfColors.add(darkColor)
        }else if (validator && day > 15){
            listOfTexts.add(text)
            listOfColors.add(lightColor)
        }
        validator
    }

    // Verifica se um objeto Candle pertence a um período semanal
    private val isWeekly: (CandleDataCCV) -> Boolean = { candle ->
        val month = candle.date.substring(5..6).toInt()
        var text = candle.date.substring(0..5)
        val validator = month/4 != lastMonth/4
        if (validator && month in 1..3 || month in 7..9){
            text += if (month <= 3) "Quarter 1" else "Quarter 3"
            listOfTexts.add(text)
            listOfColors.add(darkColor)
        }else if (validator){
            text += if (month <= 6) "Quarter 2" else "Quarter 4"
            listOfTexts.add(text)
            listOfColors.add(lightColor)
        }
        validator
    }

    // Verifica se um objeto Candle pertence a um período mensal
    private val isMonthly: (CandleDataCCV) -> Boolean = { candle ->
        val year = candle.date.substring(0..3).toInt()
        val validator = year != lastYear
        if (validator && year%2 == 0){
            listOfTexts.add(year.toString())
            listOfColors.add(darkColor)
        }else if (validator){
            listOfTexts.add(year.toString())
            listOfColors.add(lightColor)
        }
        validator
    }

    fun setListOfEnds() {
        val checkPeriod: (CandleDataCCV) -> Boolean = when (period) {
            PeriodValue.DAILY -> isDaily
            PeriodValue.WEEKLY -> isWeekly
            PeriodValue.MONTHLY -> isMonthly
        }
        listOfTexts = mutableListOf()
        listOfColors = mutableListOf()
        listOfEnds = mutableListOf()
        lastDay = -16
        lastMonth = -4
        lastYear = 0

        for (c in viewModel.visibleCandles) {
            if (checkPeriod(c)) {
                listOfEnds.add(c.xRange.last)
            } else {
                listOfEnds[listOfEnds.lastIndex] = c.xRange.last
            }
            lastDay = c.date.substring(8..9).toInt()
            lastMonth = c.date.substring(5..6).toInt()
            lastYear = c.date.substring(0..3).toInt()
        }
    }
}


