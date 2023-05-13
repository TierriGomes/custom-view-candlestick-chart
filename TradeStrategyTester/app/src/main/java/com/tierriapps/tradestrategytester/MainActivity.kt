package com.tierriapps.tradestrategytester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tierriapps.tradestrategytester.constants.CryptoSymbol

import com.tierriapps.tradestrategytester.constants.StockSymbol

import com.tierriapps.tradestrategytester.databinding.ActivityMainBinding

import com.tierriapps.tradestrategytester.viewmodels.MainViewModel

import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCryptoChart(CryptoSymbol.SHIB, "USD")
        viewModel.mychart.observe(this){
            binding.chartView.setAndConvertCandleList(
                it[0].dailyList,
                "date",
                "open",
                "close",
                "high",
                "low",
                "volume")
        }
    }

    fun printadora(){
        println("Hello gitHub")
    }

    fun funcaonogalhoteste(){
        println("hello galho teste do git rubi")
    }


}