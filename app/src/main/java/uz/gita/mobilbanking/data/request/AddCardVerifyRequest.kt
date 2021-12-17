package uz.gita.mobilbanking.data.request

data class AddCardVerifyRequest(
    var pan: String,// 16 talik
    var code: String, //"206464"
)