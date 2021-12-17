package uz.gita.mobilbanking.data.response

data class ProfileInfoResponse(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val password: String,
)

/**
 *  "firstName":"User1 firstname",
"lastName":"User1 lastname",
"phone":"+998000000000",
"password":"123456789",
"status":"0"
 * */