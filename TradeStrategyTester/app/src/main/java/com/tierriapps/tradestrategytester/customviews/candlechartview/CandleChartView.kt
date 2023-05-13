package com.tierriapps.tradestrategytester.customviews.candlechartview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.tierriapps.tradestrategytester.R
import com.tierriapps.tradestrategytester.databinding.CandleChartViewBinding


class CandleChartView @JvmOverloads
constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int = 0):
    ConstraintLayout(context, attrs, defStyleAttrs) {
    private var viewModel: CandleChartViewModel? = null
    private val binding = CandleChartViewBinding
        .inflate(LayoutInflater.from(context), this, true)
    private var darkMode = false
    private var infoTextSize = 18f
    private var paperName = "Stock"

    init {
        if (viewModel != null){
            viewModel!!.totalWidth = width.toFloat()
            viewModel!!.totalHeight = height.toFloat()
            viewModel!!.areaYForCandles = height*0.80
            viewModel!!.areaYForVolume = height*0.20
        }
        if (attrs != null){
            val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.CandleChartView)

            infoTextSize = attributeSet.getDimension(R.styleable.CandleChartView_informationTextSize, 16f)
            binding.textView.textSize = infoTextSize/2

            darkMode = attributeSet.getBoolean(R.styleable.CandleChartView_darkMode, false)
            if (darkMode){
                binding.constraintLayout.setBackgroundResource(R.color.black)
            }

            paperName = attributeSet.getString(R.styleable.CandleChartView_paperName).toString()

            attributeSet.recycle()
        }
    }
    fun <RandomCandleClass>setAndConvertCandleList(
        listOfAny: List<RandomCandleClass>,
        dateProperty: String,
        openProperty: String,
        closeProperty: String,
        highProperty: String,
        lowProperty: String,
        volumeProperty: String){

        val candlesList = convertToCandleDataCCV(
            listOfAny, dateProperty,
            openProperty, closeProperty,
            highProperty, lowProperty,
            volumeProperty).reversed()

        val vmOwner = findViewTreeViewModelStoreOwner()
        val owner = findViewTreeLifecycleOwner()

        if(viewModel != null && viewModel?.fullListOfCandles == candlesList){
            return
        }
        viewModel = ViewModelProvider(vmOwner!!, CandleChartViewModel
            .ChartViewModelFactory(candlesList, height.toFloat(), width.toFloat()))[CandleChartViewModel::class.java]
        viewModel?.paperName = paperName

        binding.internalCandleChartView.startAndCreateViewModel(viewModel!!, darkMode)
        binding.internalCandleChartView.selectedCandle.observe(owner!!){
            val text = if (it == null) "" else "Date: ${it.date} Volume: ${it.volume} \n" +
                    "Open: ${it.open.toBigDecimal()} Close: ${it.close.toBigDecimal()} " +
                    "High: ${it.high.toBigDecimal()} Low: ${it.low.toBigDecimal()}"
            binding.textView.text = text
        }

        binding.progressBar.visibility = View.GONE
    }

    fun setPaperName(name: String){
        paperName = name
        viewModel?.paperName = name
    }



}