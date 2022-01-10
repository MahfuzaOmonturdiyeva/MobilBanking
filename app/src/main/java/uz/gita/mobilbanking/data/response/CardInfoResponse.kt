package uz.gita.mobilbanking.data.response

data class CardInfoResponse(
    var id:Int,
    var pan: String,// 16 talik
    var exp: String,//"02/25"
    var owner:String,
    var cardName: String,
    var balance:Double,
    var color:Int,//0-8
    var ignoreBalance:Boolean,
    var status:Int //0 1
)