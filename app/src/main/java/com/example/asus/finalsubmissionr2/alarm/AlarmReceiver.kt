package com.example.asus.finalsubmissionr2.alarm


import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import com.example.asus.finalsubmissionr2.R
import com.example.asus.finalsubmissionr2.model.MovieAlarm
import com.example.asus.finalsubmissionr2.retro.ApiMain
import com.example.asus.finalsubmissionr2.retro.ApiResponse
import com.example.asus.finalsubmissionr2.viewModel.UpToDateViewModel
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var mainViewModel: UpToDateViewModel
    private val movieList: ArrayList<MovieAlarm> = ArrayList<MovieAlarm>()

    companion object {
        const val TYPE_DAILY_REMINDER = "type_daily_reminder"
        const val TYPE_DAILY_MOVIE = "type_daily_movie"
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"

        private const val REQ_DAILY_REMINDER = 1000
        private const val REQ_DAILY_MOVIE = 1001
    }

    override fun onReceive(context: Context, intent: Intent) {
        var message: String = context.getString(R.string.new_movie)
        var title: String = context.getString(R.string.release_movie)

        val type = intent.getStringExtra(EXTRA_TYPE)
        val notifId = intent.getIntExtra(EXTRA_ID, 0)
        if (type.equals(TYPE_DAILY_REMINDER, ignoreCase = true)) {
            title = context.getString(R.string.notification_title)
            message = context.getString(R.string.notification_message)

        } else {
            val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            ApiMain().services.getToday(date, date).enqueue(
                object :  retrofit2.Callback<ApiResponse> {
                    override fun onResponse(call: retrofit2.Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                        if(response.code() == 200) {
                            val list = response.body()?.result
                            if (list != null) {
                                for (i in list.indices) {
//                                    showAlarmNotification(context, list[i].title!!, list[i].title!! + context.getString(R.string.new_movie), notifId)
                                    title = list[i].title!!
                                    message = list[i].title!! + context.getString(R.string.new_movie)
                                    showAlarmNotification(context, title, message, list[i].id)
                                }
                            }

                        }
                    }
                    override fun onFailure(call: retrofit2.Call<ApiResponse>, t: Throwable){
                        Log.d("GAGAL", "KESALAHAN PADA DEVELOPER")
                    }
                })

        }
        showAlarmNotification(context, title, message, notifId)




    }

    private fun showAlarmNotification(
        context: Context,
        title: String,
        message: String,
        notifId: Int
    ) {
        val channelId = "Channel_1"
        val channelName = "AlarmManager_channel"

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_movie_black_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelId)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)

    }

    fun setDailyReminder(
        context: Context
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, TYPE_DAILY_REMINDER)
        intent.putExtra(EXTRA_ID, 100)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, REQ_DAILY_REMINDER, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )




    }

    fun setDailyMovie(
        context: Context

    ) {

        var notifId = 101

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)

        intent.putExtra(EXTRA_TYPE, TYPE_DAILY_MOVIE)

        intent.putExtra(EXTRA_ID, notifId)

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 8)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQ_DAILY_MOVIE,
                intent,
                0
            )

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )





    }

    fun cancelAlarm(
        context: Context,
        type: String
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        val requestCode = if (type.equals(TYPE_DAILY_REMINDER, ignoreCase = false))
            REQ_DAILY_REMINDER else REQ_DAILY_MOVIE

        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        if (requestCode == REQ_DAILY_REMINDER) {
            Toast.makeText(context, "Daily Reminder OFF", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Daily Movie OFF", Toast.LENGTH_SHORT).show()
        }

    }

}

