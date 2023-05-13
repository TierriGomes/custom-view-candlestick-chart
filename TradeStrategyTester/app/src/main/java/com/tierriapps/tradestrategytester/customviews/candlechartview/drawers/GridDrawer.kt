package com.tierriapps.tradestrategytester.customviews.candlechartview.drawers

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.tierriapps.tradestrategytester.R
import com.tierriapps.tradestrategytester.customviews.candlechartview.CandleChartViewModel
import com.tierriapps.tradestrategytester.utils.toElegant

class GridDrawer(val viewModel: CandleChartViewModel, font: Typeface) {
    val paint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 2f
        textSize = 18f
    }
    val paintName = Paint().apply {
        color = Color.GRAY
        strokeWidth = 2f
        textSize = 50f
        typeface = font
    }

    var pointerX: Float? = null
    var pointerY: Float? = null
    val papeNamerPosition = (viewModel.totalWidth-paintName.measureText(viewModel.paperName))/2


    fun drawGrid(canvas: Canvas){
        canvas.drawText(viewModel.paperName, papeNamerPosition, viewModel.areaYForCandles.toFloat()/2, paintName)
        for ((k, v) in viewModel.gridPriceValues.withIndex()){
            var y = calc(v.toFloat())
            when(k){
                0 -> y += 5
                viewModel.gridPriceValues.lastIndex -> y -= 5
            }
            canvas.drawLine(0f, y, viewModel.totalWidth, y, paint)
            val text = v.toElegant()
            canvas.drawText(text, viewModel.totalWidth-paint.measureText(text)-5, y - 3, paint)
        }
        if(pointerX != null){
            paint.color = Color.BLUE
            canvas.drawLine(0f, pointerY!!, viewModel.totalWidth, pointerY!!, paint) // horizontal line
            val text = reversercalc(pointerY!!).toElegant()
            canvas.drawText(text, viewModel.totalWidth-paint.measureText(text)-10, pointerY!!-5, paint)
            canvas.drawLine(pointerX!!, 0f, pointerX!!, viewModel.totalHeight, paint) // vertical line
            pointerY = null
            pointerX = null
            paint.color = Color.GRAY
        }
    }
    fun drawPointer(x: Float?, y: Float?){
        pointerX = x
        pointerY = y
    }
    private fun calc(value: Float): Float{
        val pxpercent = viewModel.areaYForCandles/100
        val onePercent = (viewModel.maxPrice - viewModel.minPrice) / 100
        val pct = (value - viewModel.minPrice) / onePercent // quantas vezes cabe 1 por cento aqui?
        return (viewModel.areaYForCandles - (pct * pxpercent)).toFloat()
    }

    private fun reversercalc(valueY: Float): Float {
        val screenOnePercent = viewModel.areaYForCandles / 100
        val valuePercentInScreen = (100 - (valueY / screenOnePercent))/100
        var price = (viewModel.maxPrice - viewModel.minPrice) * valuePercentInScreen
        price += viewModel.minPrice
        return price.toFloat()
    }
}