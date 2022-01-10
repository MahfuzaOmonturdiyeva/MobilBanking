package uz.gita.mobilbanking.data.request

data class IgnoreBalanceRequest(
    var userCardId: Int,
    var ignoreBalance:Boolean
)