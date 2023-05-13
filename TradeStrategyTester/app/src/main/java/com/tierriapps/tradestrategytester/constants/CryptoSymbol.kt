package com.tierriapps.tradestrategytester.constants

enum class CryptoSymbol(val symbol: String) {
    NULL(""),
    BTC("BTC"),     //Bitcoin
    ETH("ETH"),     //Ethereum
    BNB("BNB"),     //Binance Coin
    ADA("ADA"),     //Cardano
    XRP("XRP"),     //XRP
    SOL("SOL"),     //Solana
    DOT("DOT"),     //Polkadot
    USDT("USDT"),   //Tether
    DOGE("DOGE"),   //Dogecoin
    AVAX("AVAX"),   //Avalanche
    SHIB("SHIB"),   //Shiba Inu
    LUNA("LUNA"),   //Terra
    MATI("MATIC"),  //Polygon
    LINK("LINK"),   //Chainlink
    UNI ("UNI"),    //Uniswap
    LTC ("LTC"),    //Litecoin
    ALGO("ALGO"),   //Algorand
    BCH ("BCH"),    //Bitcoin Cash
    FIL ("FIL"),    //Filecoin
    FTT ("FTT"),    //FTX Token
    XTZ ("XTZ"),    //Tezos
    ATOM("ATOM"),   //Cosmos
    XEC ("XEC"),    //XeniosCoin
    ETC ("ETC"),    //Ethereum Classic
    ICP ("ICP"),    //Internet Computer
    TRX ("TRX"),    //TRON
    CRV ("CRV"),    //Curve DAO Token
    SUSHI("SUSHI"), //SushiSwap
    AXS ("AXS"),    //Axie Infinity
    NEO ("NEO"),    //Neo
    KSM ("KSM"),    //Kusama
    DASH("DASH"),   //Dash
    AAVE("AAVE"),   //Aave
    GRT ("GRT"),    //The Graph
    COMP("COMP"),   //Compound
    HOT ("HOT"),    //Holo
    MKR ("MKR"),    //Maker
    XMR ("XMR"),    //Monero
    CEL ("CEL"),    //Celsius Network
    EGLD("EGLD"),   //Elrond
    OMG ("OMG"),    //OMG Network
    MANA("MANA"),   //Decentraland
    ICX ("ICX"),    //ICON
    FTM ("FTM"),    //Fantom
    ZEC ("ZEC"),    //Zcash
    RVN ("RVN"),    //Ravencoin
    KAVA("KAVA"),   // Kava.io
    WAVES("WAVES"), //Waves
    ONE ("ONE"),    //Harmony
    ZIL ("ZIL");//Zilliqa


    fun contains(value: String): Boolean{
        for(s in this.symbol){
            if(value.equals(s)){
                return true
            }
        }
        return false
    }
}