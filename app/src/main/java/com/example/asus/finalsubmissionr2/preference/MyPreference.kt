package com.example.asus.finalsubmissionr2.preference

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.asus.finalsubmissionr2.R
import com.example.asus.finalsubmissionr2.alarm.AlarmReceiver
import com.example.asus.finalsubmissionr2.model.Movie
import com.example.asus.finalsubmissionr2.viewModel.UpToDateViewModel
import java.util.*

class MyPreference : PreferenceFragmentCompat(),

    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var mainViewModel: UpToDateViewModel

    private lateinit var alarmManager: AlarmReceiver

    private lateinit var DAILY: String
    private lateinit var MOVIE: String
    private lateinit var dailyPreference: SwitchPreference
    private lateinit var moviePreference: SwitchPreference


    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.root_preferences)
        init()
        setSummaries()
    }
    private fun init() {
        DAILY = resources.getString(R.string.key_notif_daily)
        MOVIE = resources.getString(R.string.key_notif_movie)
        dailyPreference = findPreference<SwitchPreference> (DAILY) as SwitchPreference
        moviePreference = findPreference<SwitchPreference>(MOVIE) as SwitchPreference
    }
    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        dailyPreference.isChecked = sh.getBoolean(DAILY, false)
        moviePreference.isChecked = sh.getBoolean(MOVIE, false)
    }
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences, p1: String) {
        when(p1){
            DAILY ->{
                dailyPreference.isChecked = p0.getBoolean(DAILY, false)
                setReminder(DAILY)
            }
            MOVIE -> {
                dailyPreference.isChecked= p0.getBoolean(MOVIE, false)
                setReminder(MOVIE)
            }
        }


    }
    private fun setReminder(key: String) {
        val isActive = preferenceManager.sharedPreferences.getBoolean(key, false)
        alarmManager = AlarmReceiver()
        var language = Locale.getDefault().getDisplayLanguage().toString()
        if(language == "Bahasa Indonesia"){
            language = "id"
        }else{
            language = "en-US"
        }
        if (isActive) {
            if (key == DAILY) {
                context?.let { alarmManager.setDailyReminder(it) }
            } else if (key == MOVIE) {
                mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                    UpToDateViewModel::class.java)
                mainViewModel.setMovie("movie",language)
                mainViewModel.getMovies().observe(this, androidx.lifecycle.Observer { movieItem ->
                    if(movieItem != null){
                        context?.let { alarmManager.setDailyMovie(it) }
                    }
                })


            }
        } else {
            if (key == DAILY) {
                context?.let { alarmManager.cancelAlarm(it, "type_daily_reminder") }
            } else if (key == MOVIE) {
                context?.let { alarmManager.cancelAlarm(it, "TYPE_Movie_REMINDER")}
            }
        }
    }
}