package com.im.dairyinventorymanagement.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHandler(val context: Context) {

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(INVENTORY_SHARED_PREFS, Context.MODE_PRIVATE)

    fun getString(key: String, defaultValue: String? = null): String? {
        return sharedPrefs.getString(key, defaultValue)
    }

    fun putString(key: String, value: String) {
        sharedPrefs.edit().putString(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPrefs.getInt(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        sharedPrefs.edit().putInt(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPrefs.getBoolean(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPrefs.edit().putBoolean(key, value).apply()
    }

    fun clearSharedPreferences() {
        sharedPrefs.edit().clear().apply()
    }

    companion object {
        private const val INVENTORY_SHARED_PREFS = "INVENTORY_SHARED_PREFS"
        const val LOGIN_STATUS = "LOGIN_STATUS"
    }
}