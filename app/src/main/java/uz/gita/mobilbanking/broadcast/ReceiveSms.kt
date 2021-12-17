package uz.gita.mobilbanking.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

class ReceiveSms : BroadcastReceiver() {
    val codeLiveData = MutableLiveData<String>()
    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.let {
            if (p1.action.equals("android.provider.Telephony.SMS_RECEIVED")) {
                val myBundle = it.extras;
                if (myBundle != null) {
                    try {
                        val pdus = myBundle.get("pdus") as Array<*>
                        val messages = ArrayList<SmsMessage>()
                        var numberFrom = ""
                        for (i in pdus.indices) {
                            messages.add(SmsMessage.createFromPdu(pdus[i] as ByteArray))
                            numberFrom = messages[i].displayOriginatingAddress
                            val body = messages[i].messageBody.split(":")
                            if (body.size >= 2) {
                                val text=body[1].substring(1, body[1].length)
                                var code1=""
                                var isCode=true
                                for (char in text){
                                    if (!char.isDigit()) {
                                        isCode = false
                                    }
                                    else code1+=char.toString()
                                }
                                if (numberFrom == "+998976879798" || numberFrom == "+998909793629" ||
                                    numberFrom == "+998975351717" || numberFrom == "+998933182442" &&
                                            isCode && code1.length==6
                                )
                                    codeLiveData.postValue(code1)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Timber.d(e)
                    }
                }
            }
        }
    }
}