package uz.gita.mobilbanking.data.request

data class VerifyRequest(
    val phone: String,
    val code: String
)
