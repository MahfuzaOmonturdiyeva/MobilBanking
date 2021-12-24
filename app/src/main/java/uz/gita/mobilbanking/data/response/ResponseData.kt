package uz.gita.mobilbanking.data.response

data class ResponseData<T>(
    val data: T? = null,
    var message: String? = null
)
