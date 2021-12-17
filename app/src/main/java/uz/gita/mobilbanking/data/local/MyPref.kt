package uz.gita.mobilbanking.data.local

import android.annotation.SuppressLint
import android.content.Context

class MyPref private constructor(private val context: Context) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: MyPref? = null

        fun init(context: Context) {
            instance = MyPref(context)
        }

        fun getInstance(): MyPref = instance!!
    }

    private val pref = context.getSharedPreferences("MyMobileBanking", Context.MODE_PRIVATE)

    var accessToken: String
        set(value) = pref.edit().putString("accessToken", value).apply()
        get() = pref.getString("accessToken", "")!!

    var refreshToken: String
        set(value) = pref.edit().putString("refreshToken", value).apply()
        get() = pref.getString("refreshToken", "")!!

    var numberUser: String
        set(value) = pref.edit().putString("numberUser", value).apply()
        get() = pref.getString("numberUser", "")!!

    var fingerprint: Boolean
        set(value) = pref.edit().putBoolean("fingerprint", value).apply()
        get() = pref.getBoolean("fingerprint", false)

    var pinCode: String
        set(value) = pref.edit().putString("pinCode", value).apply()
        get() = pref.getString("pinCode", "")!!


}