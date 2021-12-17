package uz.gita.mobilbanking.data.request

data class LoginRequest(
    val phone: String,
    val password: String
)