package com.example.kolibreath.shconnection.extensions

import android.content.Context
import android.content.SharedPreferences
import com.example.kolibreath.shconnection.base.App
import kotlinx.coroutines.experimental.withTimeout
import kotlin.reflect.KProperty

class Preference<T> (private val name:String,
    private val default:T) {

  private val context:Context = App.getContext()
  private val prefs: SharedPreferences  by lazy {
    context.getSharedPreferences("default",Context.MODE_PRIVATE)
  }

  operator fun getValue(thisRef:Any?,property:KProperty<*>):T = getPreference(name,default)

  operator fun setValue(thisRef: Any?,property: KProperty<*>,value:T) = putPreference(name,default)

  private fun getPreference(name:String,default: T) = with(prefs){
    val res:Any = when(default){
      is Long   -> getLong(name,default)
      is String -> getString(name,default)
      is Int    -> getInt(name,default)
      is Boolean-> getBoolean(name,default)
      is Float  -> getFloat(name,default)
      else -> throw IllegalArgumentException(" wrong type")
    }
    res as T
  }

  private fun putPreference(name:String,value:T) = with(prefs.edit()){
    when(value){
      is Long   -> putLong(name,value)
      is Int    -> putInt(name,value)
      is String -> putString(name,value)
      is Boolean-> putBoolean(name,value)
      is Float  -> putFloat(name,value)
      else -> throw  IllegalArgumentException("wrong type")
    }.apply()
  }
}