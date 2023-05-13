package com.tierriapps.tradestrategytester.data.models

data class CandleData (
    val date: String,
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float,
    val adjustedClose: Float = close,
    val volume: Int = 0,
    val dividendAmount: Float = 0f,
    val splitCoeficient: Float = 0f
)