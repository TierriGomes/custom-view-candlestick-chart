package com.tierriapps.tradestrategytester.customviews.candlechartview


data class CandleDataCCV(val date: String,
                         val open: Float,
                         val close: Float,
                         val high: Float,
                         val low: Float,
                         val volume: Int,
                         val otherData: String = "",
                         var xRange: IntRange = 0..1,
                         var yRange: IntRange = 0..1)
fun <RandomCandleClass> convertToCandleDataCCV(
    listOfAny: List<RandomCandleClass>,
    dateProperty: String,
    openProperty: String,
    closeProperty: String,
    highProperty: String,
    lowProperty: String,
    volumeProperty: String,
    otherDataProperty: String = ""): List<CandleDataCCV> {

    val listToReturn = mutableListOf<CandleDataCCV>()
    var otherData = ""
    for (any in listOfAny) {

        val clazz = any!!::class.java

        val dateField = clazz.getDeclaredField(dateProperty)
        dateField.isAccessible = true
        val date = dateField.get(any) as String

        val openField = clazz.getDeclaredField(openProperty)
        openField.isAccessible = true
        val open = openField.get(any) as Float

        val closeField = clazz.getDeclaredField(closeProperty)
        closeField.isAccessible = true
        val close = closeField.get(any) as Float

        val highField = clazz.getDeclaredField(highProperty)
        highField.isAccessible = true
        val high = highField.get(any) as Float

        val lowField = clazz.getDeclaredField(lowProperty)
        lowField.isAccessible = true
        val low = lowField.get(any) as Float

        val volumeField = clazz.getDeclaredField(volumeProperty)
        volumeField.isAccessible = true
        val volume = volumeField.get(any) as Int

        try {
            val otherDataField = clazz.getDeclaredField(otherDataProperty)
            otherDataField.isAccessible = true
            otherData = otherDataField.get(any) as String
        }catch (_: Exception){}


        val candleCV = CandleDataCCV(
            date = date,
            open = open,
            close = close,
            high = high,
            low = low,
            volume = volume,
            otherData = otherData
        )
        listToReturn.add(candleCV)
    }

    return listToReturn
}