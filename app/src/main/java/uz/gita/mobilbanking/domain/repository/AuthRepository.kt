package uz.gita.mobilbanking.domain.repository

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.*

interface AuthRepository {

    val getPin:String

    val getPhoneNumber:String

    fun userRegister(data: RegisterRequest): LiveData<MyResult<Unit>>

    fun userVerify(data: VerifyRequest): LiveData<MyResult<Unit>>

    fun userLogin(data: LoginRequest): LiveData<MyResult<Unit>>

    fun userLogout(): LiveData<MyResult<Unit>>

    fun userResend(data: LoginRequest): LiveData<MyResult<Unit>>

    fun userReset(phone: String): LiveData<MyResult<Unit>>

    fun userNewPassword(data: NewPasswordRequest): LiveData<MyResult<Unit>>

    fun userRefreshToken(): LiveData<MyResult<Unit>>
}