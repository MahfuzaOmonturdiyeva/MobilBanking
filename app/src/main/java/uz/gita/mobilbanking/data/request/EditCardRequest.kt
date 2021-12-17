package uz.gita.mobilbanking.data.request

data class EditCardRequest(
    var cardNumber: String,// 16 talik
    var newName: String, //"Ish2"
)