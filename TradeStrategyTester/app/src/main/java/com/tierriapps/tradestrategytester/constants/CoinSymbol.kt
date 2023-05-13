package com.tierriapps.tradestrategytester.constants

enum class CoinSymbol(val symbol: String) {
    NULL(""),
    USD("USD"), //Dólar Americano
    EUR("EUR"), //Euro
    JPY("JPY"), //Iene Japonês
    GBP("GBP"), //Libra Esterlina
    CHF("CHF"), //Franco Suíço
    CAD("CAD"), //Dólar Canadense
    AUD("AUD"), //Dólar Australiano
    NZD("NZD"), //Dólar Neozelandês
    HKD("HKD"), //Dólar de Hong Kong
    NOK("NOK"), //Coroa Norueguesa
    SEK("SEK"), //Coroa Sueca
    DKK("DKK"), //Coroa Dinamarquesa
    SGD("SGD"), //Dólar de Cingapura
    ZAR("ZAR"), //Rand Sul-Africano
    MXN("MXN"), //Peso Mexicano
    TRY("TRY"), //Lira Turca
    RUB("RUB"), //Rublo Russo
    INR("INR"), //Rúpia Indiana
    BRL("BRL"), //Real Brasileiro
    PLN("PLN"), //Zloty Polonês
    CNY("CNY"), //Yuan Chinês
    KRW("KRW"), //Won Sul-Coreano
    TWD("TWD"), //Novo Dólar de Taiwan
    THB("THB"), //Baht Tailandês
    IDR("IDR"), //Rupia Indonésia
    SAR("SAR"), //Riyal Saudita
    AED("AED"), //Dirham dos Emirados Árabes Unidos
    MYR("MYR"), //Ringgit Malaio
    QAR("QAR"), //Riyal do Qatar
    COP("COP");  //Peso Colombiano

    fun contains(value: String): Boolean{
        for(s in this.symbol){
            if(value.equals(s)){
                return true
            }
        }
        return false
    }
}