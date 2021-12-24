package uz.gita.mobilbanking.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Sherzodbek Muhammadiev on 31.01.2020
 */

abstract class SharedPreference(context: Context, preferences: SharedPreferences? = null) {
    private val pref = preferences ?: context.getSharedPreferences(javaClass.canonicalName, Context.MODE_PRIVATE)
    private val gson = Gson()

    inner class BooleanPreference(private val defValue: Boolean = false) : ReadWriteProperty<SharedPreference, Boolean> {
        override fun getValue(thisRef: SharedPreference, property: KProperty<*>) = pref.getBoolean(property.name, defValue)
        override fun setValue(thisRef: SharedPreference, property: KProperty<*>, value: Boolean) = pref.edit { putBoolean(property.name, value).apply() }
    }

    inner class IntPreference(private val defValue: Int = -1) : ReadWriteProperty<Any, Int> {
        override fun getValue(thisRef: Any, property: KProperty<*>) = pref.getInt(property.name, defValue)
        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) = pref.edit { putInt(property.name, value).apply() }
    }

    inner class LongPreference(private val defValue: Long = 0L) : ReadWriteProperty<Any, Long> {
        override fun getValue(thisRef: Any, property: KProperty<*>) = pref.getLong(property.name, defValue)
        override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) = pref.edit { putLong(property.name, value).apply() }
    }

    inner class StringPreference(private val defValue: String = "") : ReadWriteProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String = pref.getString(property.name, defValue) ?: ""

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) = pref.edit { putString(property.name, value).apply() }
    }

    inline fun <reified T> checkType() = when (T::class) {
        String::class -> println("TypeA")
        else -> println("Type not recognized")
    }

    /*inner class Preference<T>(private val defValue: T? = null) : ReadWriteProperty<SharedPreference, T?> {
        override fun getValue(thisRef: SharedPreference, property: KProperty<*>): T? {
            val key = property.name
            property.returnType
            String::class
           *//* return when (property) {
                is String -> pref.getString(key, defValue as? String)
                is Int -> pref.getInt(key, defValue as? Int ?: 0)
                else -> pref.getString(key, defValue as? String)
            } as? T*//*
        }

        override fun setValue(thisRef: SharedPreference, property: KProperty<*>, value: T?) = pref.edit {
            val key = property.name
            *//*when (thisRef) {
                is String -> putString(key, value as? String)
                is Int -> putInt(key, value as? Int ?: 0)
                else -> putString(key, value as? String)
            }*//*
        }
    }*/

    inner class ObjectPreference<T>(private val defValue: T? = null) : ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            val text = pref.getString(property.name, null)
            val type = object : TypeToken<T>() {}.type
            return gson.fromJson<T?>(text, type)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) = pref.edit { putString(property.name, gson.toJson(value)).apply() }
    }
}


