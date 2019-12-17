package com.example.asus.finalsubmissionr2.preference

import android.content.Context
import android.content.SharedPreferences

class AppPreference(context: Context) {
    private val KEY_UPCOMING = "upcoming"
    private val KEY_DAILY = "daily"
    private val PREF_NAME = "UserPref"
    private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    var isUpcoming: Boolean
        get() = preferences.getBoolean(KEY_UPCOMING, false)
        set(status) {
            editor.putBoolean(KEY_UPCOMING, status)
            editor.apply()
        }

    var isDaily: Boolean
        get() = preferences.getBoolean(KEY_DAILY, false)
        set(status) {
            editor.putBoolean(KEY_DAILY, status)
            editor.apply()
        }

    init {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }
}