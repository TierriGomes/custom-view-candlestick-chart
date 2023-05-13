package com.tierriapps.tradestrategytester.utils

import java.math.BigDecimal

fun arredonda(max: Float, min: Float): List<Double>{
    val valoresRedondos = mutableListOf<Double>()
    val diferenca = max - min

    // Define os limiares e incrementos/decrementos correspondentes
    val limiares = doubleArrayOf(
        5000.00, 2500.00, 1000.00,
        500.00, 250.00, 100.00,
        50.00, 25.00, 10.00,
        5.00, 2.50, 1.00,
        0.50, 0.25, 0.10,
        0.050, 0.025, 0.01,
        0.0050, 0.0025, 0.001,
        0.00050, 0.00025, 0.0001,
        0.000050, 0.000025, 0.00001,
        0.0000050, 0.0000025, 0.000001,
        0.00000050, 0.00000025, 0.0000001)

    for (i in 0 until limiares.size) {
        if (diferenca/5 >= limiares[i]) {
            var valorRedondo = Math.ceil(max / limiares[i]) * limiares[i] // Faz o arredondamento
            while (valorRedondo > min) {
                valoresRedondos.add(valorRedondo)
                valorRedondo -= limiares[i]
            }
            valoresRedondos.add(valorRedondo)
            break
        }
    }
    return valoresRedondos
}
fun Double.toElegant(): String{
    val beforeDot = this.toInt()
    val afterDot = (this - beforeDot).toBigDecimal().toString().drop(0)

    val bString = beforeDot.toString()
    var aString = ""
    var count = 4
    for (c in afterDot){
        if (c in "0." && count == 4){
            aString += c
        }else{
            aString += c
            count --
        }
        if (count <= 0){
            break
        }
    }
    return bString + if (beforeDot > 9) aString.substring(0..2) else aString
}
fun Float.toElegant(): String{
    val beforeDot = this.toInt()
    val afterDot = (this - beforeDot).toBigDecimal().toString().drop(0)

    val bString = beforeDot.toString()
    var aString = ""
    var count = 4
    for (c in afterDot){
        if (c in "0." && count == 4){
            aString += c
        }else{
            aString += c
            count --
        }
        if (count <= 0){
            break
        }
    }
    return bString + if (beforeDot > 9) aString.substring(0..2) else aString
}