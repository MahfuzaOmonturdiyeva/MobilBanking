package uz.gita.mobilbanking.data.request

data class NewPasswordRequest(
    val phone: String,
    val newPassword: String,
    val code: String
)