package com.example.monterapp.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import com.example.monterapp.utils.KEYS

class Pref(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(KEYS.KEY_PREFS,Context.MODE_PRIVATE)

    fun setUserLoggedInStatus(isLoggedIn: Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEYS.KEY_USER_LOGGED_IN,isLoggedIn)
        editor.apply()
    }

    fun getUserLoggedInStatus(): Boolean{
        return sharedPreferences.getBoolean(KEYS.KEY_USER_LOGGED_IN,false)
    }

    fun setUserLoggedInFullName(fullName:String){
        val editor = sharedPreferences.edit()
        editor.putString(KEYS.KEY_USER_LOGGED_IN_FULL_NAME,fullName)
        editor.apply()
    }

    fun getUserLoggedInFullName():String?{
        return sharedPreferences.getString(KEYS.KEY_USER_LOGGED_IN_FULL_NAME,"")
    }

    fun setUserLoggedInRegionName(regionName:String){
        val editor = sharedPreferences.edit()
        editor.putString(KEYS.KEY_USER_LOGGED_IN_REGION_NAME,regionName)
        editor.apply()
    }

    fun getUserLoggedInRegionName():String?{
        return sharedPreferences.getString(KEYS.KEY_USER_LOGGED_IN_REGION_NAME,"")
    }
}