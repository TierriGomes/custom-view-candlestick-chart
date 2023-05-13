package com.tierriapps.tradestrategytester.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.tierriapps.tradestrategytester.constants.CoinSymbol
import com.tierriapps.tradestrategytester.constants.CryptoSymbol
import com.tierriapps.tradestrategytester.constants.StockSymbol
import com.tierriapps.tradestrategytester.data.models.ChartData
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    private val TAG = "MainViewModel"
    private val _mychart = MutableLiveData<List<ChartData>>()
    val mychart: LiveData<List<ChartData>> = _mychart

    class MainViewModelFactory(private val repository: Repository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }

    fun getAllCharts(){
        viewModelScope.launch {
            _mychart.postValue(repository.getAllCharts())
        }
    }

    fun getStockChart(symbol: StockSymbol){
        viewModelScope.launch{
            val response = repository.getStockBySymbol(symbol)
            if(response != null){
                _mychart.postValue(listOf(response))
            }
            else{
                Log.d(TAG, "Error accessing data")
            }
        }
    }

    fun getForexChart(fromCoin: CoinSymbol, toCoin: CoinSymbol){
        viewModelScope.launch{
            val response = repository.getForexBySymbol(fromCoin, toCoin)
            if(response != null){
                _mychart.postValue(listOf(response))
            }
            else{
                Log.d(TAG, "Error accessing data")
            }
        }
    }

    fun getCryptoChart(fromCrypto: CryptoSymbol, toCoinOrCrypto: String){
        viewModelScope.launch{
            val response = repository.getCryptoBySymbol(fromCrypto, toCoinOrCrypto)
            if(response != null){
                _mychart.postValue(listOf(response))
            }
            else{
                Log.d(TAG, "Error accessing data")
            }
        }
    }

    fun deleteChart(symbol: String){
        viewModelScope.launch {
            repository.deleteChartBySymbol(symbol)
        }
    }

    fun updateChart(chart: ChartData){
        viewModelScope.launch {
            repository.updateChart(chart)
        }
    }



}