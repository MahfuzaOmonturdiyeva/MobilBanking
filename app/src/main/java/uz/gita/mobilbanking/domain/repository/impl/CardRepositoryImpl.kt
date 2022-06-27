package uz.gita.mobilbanking.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.*
import uz.gita.mobilbanking.data.source.local.LocalStorage
import uz.gita.mobilbanking.data.source.remote.api.api.CardApi
import uz.gita.mobilbanking.domain.repository.CardRepository
import java.io.IOException
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardApi: CardApi,
    private val localStorage: LocalStorage
) : CardRepository {
    override var favoriteCardId: Int
        get() = localStorage.favoriteCardId
        set(value) {
            localStorage.favoriteCardId = value
        }
    override var ignoreTotalSum: Boolean
        get() = localStorage.ignoreTotalSum
        set(value) {
            localStorage.ignoreTotalSum = value
        }

    override fun addCard(addCardRequest: AddCardRequest): LiveData<MyResult<Unit>> = liveData {
        try {
            val response = cardApi.addCard(addCardRequest)
            if (response.isSuccessful) {
                emit(MyResult.Success<Unit>(Unit))
            } else {
                if (response.code() == 401)
                    emit(MyResult.Logout<Unit>())
                if (response.code() != 401) {
                    response.errorBody()?.let {
                        val message = Gson().fromJson(it.string(), ResponseData::class.java)
                        emit(MyResult.Message<Unit>(message.message!!))
                    }
                } else
                    emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }

    override fun addCardVerify(addCardVerifyRequest: AddCardVerifyRequest): LiveData<MyResult<CardInfoResponse>> =
        liveData {
            try {
                val response = cardApi.addCardVerify(addCardVerifyRequest)
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        emit(MyResult.Success<CardInfoResponse>(it))
                        return@liveData
                    }
                    emit(MyResult.Message<CardInfoResponse>("Response data null"))
                } else {
                    if (response.code() == 401)
                        emit(MyResult.Logout<CardInfoResponse>())
                    else {
                        response.errorBody()?.let {
                            val message = Gson().fromJson(it.string(), ResponseData::class.java)
                            emit(MyResult.Message<CardInfoResponse>(message.message!!))
                        }
                    }
                }
            } catch (e: IOException) {
                emit(MyResult.Error<CardInfoResponse>(e))
            }
        }

    override fun editCard(editCardRequest: EditCardRequest): LiveData<MyResult<Unit>> = liveData {
        try {
            val response = cardApi.editCard(editCardRequest)
            if (response.isSuccessful) {
                emit(MyResult.Success<Unit>(Unit))
            } else {
                if (response.code() == 401)
                    emit(MyResult.Logout<Unit>())
                else {
                    response.errorBody()?.let {
                        val message = Gson().fromJson(it.string(), ResponseData::class.java)
                        emit(MyResult.Message<Unit>(message.message!!))
                    }
                }
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }

    override fun deleteCard(cardNumberRequest: CardNumberRequest): LiveData<MyResult<Unit>> =
        liveData {
            try {
                val response = cardApi.deleteCard(cardNumberRequest)
                if (response.isSuccessful) {
                    emit(MyResult.Success<Unit>(Unit))
                } else {
                    if (response.code() == 401)
                        emit(MyResult.Logout<Unit>())
                    else {
                        response.errorBody()?.let {
                            val message = Gson().fromJson(it.string(), ResponseData::class.java)
                            emit(MyResult.Message<Unit>(message.message!!))
                        }
                    }
                }
            } catch (e: IOException) {
                emit(MyResult.Error<Unit>(e))
            }
        }

    override fun getAllCard(): LiveData<MyResult<List<CardInfoResponse>>> = liveData {
        try {
            val response = cardApi.getAllCard()
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    emit(MyResult.Success<List<CardInfoResponse>>(it))
                    return@liveData
                }
                emit(MyResult.Message<List<CardInfoResponse>>("Response data null"))
            } else {
                if (response.code() == 401)
                    emit(MyResult.Logout<List<CardInfoResponse>>())
                else {
                    response.errorBody()?.let {
                        val message = Gson().fromJson(it.string(), ResponseData::class.java)
                        emit(MyResult.Message<List<CardInfoResponse>>(message.message!!))
                    }
                }
            }
        } catch (e: IOException) {
            emit(MyResult.Error<List<CardInfoResponse>>(e))
        }
    }

    override fun getTotalSum(): LiveData<MyResult<Double>> = liveData {
        try {
            val response = cardApi.getTotalSum()
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    emit(MyResult.Success<Double>(it))
                    return@liveData
                }
                emit(MyResult.Message<Double>("Response data null"))
            } else {
                if (response.code() == 401)
                    emit(MyResult.Logout<Double>())
                else {
                    response.errorBody()?.let {
                        val message = Gson().fromJson(it.string(), ResponseData::class.java)
                        emit(MyResult.Message<Double>(message.message!!))
                    }
                }
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Double>(e))
        }
    }

    override fun ownerByPan(ownerByPanRequest: OwnerByPanRequest): LiveData<MyResult<OwnerCardResponse>> =
        liveData {
            try {
                val response = cardApi.ownerByPan(ownerByPanRequest.pan)
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        emit(MyResult.Success<OwnerCardResponse>(it))
                        return@liveData
                    }
                    emit(MyResult.Message<OwnerCardResponse>("Response data null"))

                } else {
                    when {
                        response.code() == 401 -> emit(MyResult.Logout<OwnerCardResponse>())
                        response.code()==400 -> {
                            emit(MyResult.Message<OwnerCardResponse>("401"))
                        }
                        else -> {
                            response.errorBody()?.let {
                                val message = Gson().fromJson(it.string(), ResponseData::class.java)
                                emit(MyResult.Message<OwnerCardResponse>(message.message!!))
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                emit(MyResult.Error<OwnerCardResponse>(e))
            }
        }

    override fun ownerById(ownerByIdRequest: OwnerByIdRequest): LiveData<MyResult<OwnerCardResponse>> =
        liveData {
            try {
                val response = cardApi.ownerById(ownerByIdRequest.id)
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        emit(MyResult.Success<OwnerCardResponse>(it))
                        return@liveData
                    }
                    emit(MyResult.Message<OwnerCardResponse>("Response data null"))

                } else {
                    if (response.code() == 401)
                        emit(MyResult.Logout<OwnerCardResponse>())
                    else {
                        response.errorBody()?.let {
                            val message = Gson().fromJson(it.string(), ResponseData::class.java)
                            emit(MyResult.Message<OwnerCardResponse>(message.message!!))
                        }
                    }
                }
            } catch (e: IOException) {
                emit(MyResult.Error<OwnerCardResponse>(e))
            }
        }

    override fun colorCard(colorRequest: ColorRequest): LiveData<MyResult<ColorResponse>> =
        liveData {
            try {
                val response = cardApi.colorCard(colorRequest)
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        emit(MyResult.Success<ColorResponse>(it))
                        return@liveData
                    }
                    emit(MyResult.Message<ColorResponse>("Response data null"))

                } else {
                    if (response.code() == 401)
                        emit(MyResult.Logout<ColorResponse>())
                    else {
                        response.errorBody()?.let {
                            val message = Gson().fromJson(it.string(), ResponseData::class.java)
                            emit(MyResult.Message<ColorResponse>(message.message!!))
                        }
                    }
                }
            } catch (e: IOException) {
                emit(MyResult.Error<ColorResponse>(e))
            }
        }

    override fun ignoreBalance(ignoreBalanceRequest: IgnoreBalanceRequest): LiveData<MyResult<IgnoreBalanceResponse>> =
        liveData {
            try {
                val response = cardApi.ignoreBalance(ignoreBalanceRequest)
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        emit(MyResult.Success<IgnoreBalanceResponse>(it))
                        return@liveData
                    }
                    emit(MyResult.Message<IgnoreBalanceResponse>("Response data null"))
                } else {
                    if (response.code() == 401)
                        emit(MyResult.Logout<IgnoreBalanceResponse>())
                    else {
                        response.errorBody()?.let {
                            val message = Gson().fromJson(it.string(), ResponseData::class.java)
                            emit(MyResult.Message<IgnoreBalanceResponse>(message.message!!))
                        }
                    }
                }
            } catch (e: IOException) {
                emit(MyResult.Error<IgnoreBalanceResponse>(e))
            }
        }
}