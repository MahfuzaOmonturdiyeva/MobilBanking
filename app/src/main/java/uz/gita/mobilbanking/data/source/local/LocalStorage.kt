package uz.gita.mobilbanking.data.source.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.mobilbanking.utils.SharedPreference
import javax.inject.Inject
import javax.inject.Singleton

class LocalStorage @Inject constructor(@ApplicationContext context: Context) :SharedPreference(context){

    var accessToken: String by StringPreference("")

    var refreshToken: String by StringPreference("")

    var phoneNumberUser: String by StringPreference("")

    var fingerprint: Boolean by BooleanPreference(false)

    var pinCode: String by StringPreference("")

    var favoriteCardId:Int by IntPreference(-1)

    var ignoreTotalSum:Boolean by BooleanPreference(false)

}