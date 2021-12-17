package uz.gita.mobilbanking.data.response

data class CardDataResponse(
    var pan: String,// 16 talik
    var exp: String,//"02/25"
    var cardName: String? = "Card", //"Student"
)