package com.tierriapps.tradestrategytester.customviews.candlechartview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import com.tierriapps.tradestrategytester.R
import com.tierriapps.tradestrategytester.customviews.candlechartview.drawers.CandleDrawer
import com.tierriapps.tradestrategytester.customviews.candlechartview.drawers.GridDrawer
import com.tierriapps.tradestrategytester.customviews.candlechartview.drawers.PeriodDrawer


class InternalCandleChartView @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int = 0): View(context, attrs, defStyleAttrs) {
    private var viewModel: CandleChartViewModel? = null
    private lateinit var candleDrawer: CandleDrawer
    private lateinit var gridDrawer: GridDrawer
    private lateinit var periodDrawer: PeriodDrawer

    private val gestureDetector = GestureDetector(context, MyGestureListener())
    private val scaleDetector = ScaleGestureDetector(context, MyScaleListener())
    private var isLongTouch = false
    private var movement = 0f

    private val _selectedCandle = MutableLiveData<CandleDataCCV?>()
    val selectedCandle: LiveData<CandleDataCCV?> = _selectedCandle


    // THE START FUNCTION
    fun startAndCreateViewModel(mviewModel: CandleChartViewModel, isDarkMode: Boolean){
        viewModel = mviewModel

        candleDrawer = CandleDrawer(viewModel!!)

        val font = ResourcesCompat.getFont(context, R.font.bungee_inline)
        gridDrawer = GridDrawer(viewModel!!, font!!)

        val lightColor = if (!isDarkMode) context.getColor(R.color.white)
            else context.getColor(R.color.darkGray)
        val darkColor = if (!isDarkMode) context.getColor(R.color.whiteGray)
            else context.getColor(R.color.black)

        periodDrawer = PeriodDrawer(viewModel!!, PeriodDrawer.PeriodValue.DAILY, lightColor, darkColor)

        invalidate()
    }

    // THE DRAW FUNCTION
    override fun onDraw(canvas: Canvas) {
        if (viewModel == null){
            return
        }
        periodDrawer.drawnDateShadow(canvas)
        gridDrawer.drawGrid(canvas)
        for(indicator in viewModel!!.indicators){
            indicator.drawIndicator(canvas)
        }
        candleDrawer.drawnCandles(canvas)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_UP -> {isLongTouch = false ; gridDrawer.drawPointer(null, null)}
            MotionEvent.ACTION_MOVE -> if (isLongTouch) gridDrawer.drawPointer(event.x, event.y)
        }
        gestureDetector.onTouchEvent(event)
        scaleDetector.onTouchEvent(event)
        invalidate()
        return true
    }

    inner class MyGestureListener: GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            _selectedCandle.postValue(viewModel?.getSelectedCandleOrNull(e.x.toInt(), e.y.toInt()))
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            gridDrawer.drawPointer(e.x, e.y)
            invalidate()
            isLongTouch = true
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float
        ): Boolean {
            movement += distanceX
            if (movement >= 10){
                movement = 0f
                viewModel?.rightMove()
            }
            if (movement <= -10){
                movement = 0f
                viewModel?.leftMove()
            }
            return true
        }
    }
    inner class MyScaleListener: ScaleGestureDetector.SimpleOnScaleGestureListener(){
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            viewModel?.zoomMove(detector.scaleFactor > 1)
            return true
        }
    }

}