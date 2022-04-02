package drip.geek

import kotlinx.serialization.Serializable

// {
//  "updated_at":1648608568918,
//  "data":{
//      "name":"Wrapped BNB",
//      "symbol":"WBNB",
//      "price":"430.1597423816028612239334151412",
//      "price_BNB":"1"
//  }
// }
@Serializable
data class BnbPrice(val data: BnbPriceData)
@Serializable
data class BnbPriceData(val symbol: String, val price: Double)