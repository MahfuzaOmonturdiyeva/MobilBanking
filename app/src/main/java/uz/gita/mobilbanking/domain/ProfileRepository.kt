package uz.gita.mobilbanking.domain

import com.google.gson.Gson
import uz.gita.mobilbanking.data.source.remote.api.ProfileApi
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.ProfileInfoResponse
import uz.gita.mobilbanking.data.source.local.LocalStorage
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.source.remote.ApiClient

class ProfileRepository {
//    private val pref = LocalStorage.getInstance()
//    private val api = ApiClient.retrofit.create(ProfileApi::class.java)
//    private val authRepository = AuthRepository()
//
//    suspend fun setUserAvatar(formData: String): MyResult<Unit> {
//        try {
//            val response = api.setUserAvatar(pref.accessToken, formData)
//            if (response.isSuccessful) {
//                response.body()?.message?.let {
//                    return MyResult.Success(Unit)
//                }
//            }
//            if (!response.isSuccessful) {
////                if(response.code()==401){
////                    authRepository.userRefreshToken()
////
////                }
//
//                response.errorBody()?.let {
//                    val message = Gson().fromJson(it.string(), String()::class.java)
//                    return MyResult.Message(message)
//                }
//                return MyResult.Message("Server bilan bog'lanishda xatolik")
//            }
//
//            val body =
//                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
//            return body.message?.let { MyResult.Message(it) }!!
//        } catch (e: Throwable) {
//            return MyResult.Error(e)
//        }
//    }
//
//    suspend fun getUserAvatar(): MyResult<Unit> {
//        try {
//            val response = api.getUserAvatar(pref.accessToken)
//            if (response.isSuccessful) {
//                response.body()?.message?.let {
//                    return MyResult.Success(Unit)
//                }
//            }
//            if (!response.isSuccessful) {
////                if(response.code()==401){
////                    authRepository.userRefreshToken()
////
////                }
//                response.errorBody()?.let {
//                    val message = Gson().fromJson(it.string(), String()::class.java)
//                    return MyResult.Message(message)
//                }
//                return MyResult.Message("Server bilan bog'lanishda xatolik")
//            }
//
//            val body =
//                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
//            return body.message?.let { MyResult.Message(it) }!!
//        } catch (e: Throwable) {
//            return MyResult.Error(e)
//        }
//    }
//
//    suspend fun setUserProfileInfo(profileRequest: ProfileRequest): MyResult<Unit> {
//        try {
//            val response = api.setUserProfileInfo(pref.accessToken, profileRequest)
//            if (response.isSuccessful) {
//                response.body()?.data?.let {
//                    return MyResult.Success(Unit)
//                }
//            }
//            if (!response.isSuccessful) {
////                if(response.code()==401){
////                    authRepository.userRefreshToken()
////
////                }
//                response.errorBody()?.let {
//                    val message = Gson().fromJson(it.string(), String()::class.java)
//                    return MyResult.Message(message)
//                }
//                return MyResult.Message("Server bilan bog'lanishda xatolik")
//            }
//            val body =
//                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
//            return body.message?.let { MyResult.Message(it) }!!
//        } catch (e: Throwable) {
//            return MyResult.Error(e)
//        }
//    }
//
//    suspend fun getUserProfileInfo(): MyResult<ProfileInfoResponse> {
//        try {
//            val response = api.getUserProfileInfo(pref.accessToken)
//            if (response.isSuccessful) {
//                response.body()?.data?.let {
//                    return MyResult.Success(it)
//                }
//            }
//            if (!response.isSuccessful) {
////                if(response.code()==401){
////                    authRepository.userRefreshToken()
////
////                }
//                response.errorBody()?.let {
//                    val message = Gson().fromJson(it.string(), String()::class.java)
//                    return MyResult.Message(message)
//                }
//                return MyResult.Message("Server bilan bog'lanishda xatolik")
//            }
//            val body =
//                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
//            return body.message?.let { MyResult.Message(it) }!!
//        } catch (e: Throwable) {
//            return MyResult.Error(e)
//        }
//    }
}

