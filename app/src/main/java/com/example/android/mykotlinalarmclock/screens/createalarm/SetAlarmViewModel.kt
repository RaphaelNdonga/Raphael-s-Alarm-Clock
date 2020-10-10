package com.example.android.mykotlinalarmclock.screens.createalarm

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.android.mykotlinalarmclock.data.Alarm
import com.example.android.mykotlinalarmclock.receiver.AlarmBroadcastReceiver
import com.example.android.mykotlinalarmclock.utils.*
import java.util.*

class SetAlarmViewModel(private val app: Application) : AndroidViewModel(app) {

    fun scheduleAlarm(alarm: Alarm) {

        val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(app, AlarmBroadcastReceiver::class.java)
        intent.putExtra(RECURRING, alarm.recurring)
        intent.putExtra(MONDAY, alarm.monday)
        intent.putExtra(TUESDAY, alarm.tuesday)
        intent.putExtra(WEDNESDAY, alarm.wednesday)
        intent.putExtra(THURSDAY, alarm.thursday)
        intent.putExtra(FRIDAY, alarm.friday)
        intent.putExtra(SATURDAY, alarm.saturday)
        intent.putExtra(SUNDAY, alarm.sunday)
        intent.putExtra(TITLE, alarm.title)

        val pendingIntent = PendingIntent.getBroadcast(getApplication(), alarm.alarmId, intent, 0)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, alarm.hour)
        calendar.set(Calendar.MINUTE, alarm.minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        /**
         * Note the brilliance here. Using Calendar.DAY_OF_MONTH records days in this way:
         * 1st monday of January, 2nd monday of January, 3rd monday of January 4th monday of january
         * When The time set on monday is too late, it sends the alarm to the next monday
         */
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
        }
        if (!alarm.recurring) {
            var toastText: String? = null
            try {
                toastText = String.format(
                    "One Time Alarm %s scheduled for %s at %02d:%02d",
                    alarm.title,
                    DayUtil.toDay(calendar.get(Calendar.DAY_OF_WEEK)),
                    alarm.hour,
                    alarm.minute,
                    alarm.alarmId
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(app, toastText, Toast.LENGTH_SHORT).show()
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            var toastText: String? = null
            try {
                toastText = String.format(
                    "Recurring Alarm %s scheduled for %s at %02d:%02d",
                    alarm.title,
                    getRecurringDaysText(alarm),
                    alarm.hour,
                    alarm.minute,
                    alarm.alarmId
                )
            }catch (e:Exception){
                e.printStackTrace()
            }
            Toast.makeText(app,toastText,Toast.LENGTH_SHORT).show()
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis, RUN_DAILY,pendingIntent)

        }
        alarm.started = true
    }

    private fun getRecurringDaysText(alarm: Alarm) {
        if (!alarm.recurring) {
            return
        }
        var days = ""
        if (alarm.monday) {
            days += "Mo "
        }
        if (alarm.tuesday) {
            days += "Tu "
        }
        if (alarm.wednesday) {
            days += "We "
        }
        if (alarm.thursday) {
            days += "Th "
        }
        if (alarm.friday) {
            days += "Fr "
        }
        if (alarm.saturday) {
            days += "Sa"
        }
        if (alarm.sunday) {
            days += "Su"
        }
    }
}