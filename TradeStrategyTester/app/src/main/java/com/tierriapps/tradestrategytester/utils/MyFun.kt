package com.tierriapps.tradestrategytester.utils

fun catchFirstSymbol(symbol: String):String{
    var text = ""
    symbol.forEach {
        if (it != '/'){
        text += it
        }else{
            return text
        } }
    return text.trim()
}

fun catchLastSymbol(symbol: String): String{
    var text = ""
    var delete = true
    symbol.forEach {
        if(it == '/'){
            delete = false
        }else if(!delete){
            text += it
        } }
    return text.trim()
}

fun unifySymbols(symbol1: String, symbol2: String): String{
    return "$symbol1/$symbol2"
}