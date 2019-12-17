package com.example.asus.finalsubmissionr2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import com.example.asus.finalsubmissionr2.alarm.AlarmReceiver
import com.example.asus.finalsubmissionr2.preference.AppPreference
import com.example.asus.finalsubmissionr2.retro.ApiMain
import com.example.asus.finalsubmissionr2.retro.ApiResponse
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var alarmManager: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        val dailySwitch = findViewById<SwitchCompat>(R.id.switch_daily)
        val upcomingSwitch = findViewById<SwitchCompat>(R.id.switch_upcoming)

//       val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
//            ApiMain().services.getToday(date, date).enqueue(
//                object :  retrofit2.Callback<ApiResponse> {
//                    override fun onResponse(call: retrofit2.Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
//
//                        if(response.code() == 200) {
//                            val list = response.body()?.result
//                            if (list != null) {
//                                for (i in 0 until list.size) {
//
//                                    Log.d("COBA", "hm ${list[i]}")
//                                }
//                                Log.d("coba", list.toString())
//                            }
//
//                        }
//                    }
//                    override fun onFailure(call: retrofit2.Call<ApiResponse>, t: Throwable){
//                        Log.d("GAGAL", "KESALAHAN PADA DEVELOPER")
//                    }
//                })

        val appPreference = AppPreference(this)
        alarmManager = AlarmReceiver()

        dailySwitch.setOnClickListener {
            val isDaily = dailySwitch.isChecked
            if (isDaily) {
                dailySwitch.isEnabled = true
                appPreference.isDaily = isDaily
                alarmManager.setDailyReminder(this)
                Toast.makeText(
                    this@SettingsActivity,
                    R.string.daily_on,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                dailySwitch.isChecked = false
                appPreference.isDaily = isDaily
                alarmManager.cancelAlarm(
                    this,
                    AlarmReceiver.TYPE_DAILY_REMINDER
                )
//                Toast.makeText(
//                    this@SettingsActivity,
//                    R.string.daily_off,
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }

        upcomingSwitch.setOnClickListener {
            val isUpcoming = upcomingSwitch.isChecked
            if (isUpcoming) {
                upcomingSwitch.isEnabled = true
                appPreference.isUpcoming = isUpcoming
                alarmManager.setDailyMovie(
                    this
                )
                Toast.makeText(
                    this@SettingsActivity,
                    R.string.upcoming_on,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                upcomingSwitch.isChecked = false
                appPreference.isUpcoming = isUpcoming
                alarmManager.cancelAlarm(
                    this@SettingsActivity,
                        AlarmReceiver.TYPE_DAILY_MOVIE
                    )
//                Toast.makeText(
//                    this@SettingsActivity,
//                        R.string.upcoming_off,
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }

        if (appPreference.isDaily) {
            dailySwitch.isChecked = true
        } else {
            dailySwitch.isChecked = false
        }

        if (appPreference.isUpcoming) {
            upcomingSwitch.isChecked = true
        } else {
            upcomingSwitch.isChecked = false
        }
    }
}