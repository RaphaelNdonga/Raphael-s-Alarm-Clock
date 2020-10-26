package com.example.android.mykotlinalarmclock.screens.ring

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.mykotlinalarmclock.data.Alarm
import com.example.android.mykotlinalarmclock.receiver.AlarmBroadcastReceiver
import com.example.android.mykotlinalarmclock.service.AlarmService
import com.example.android.mykotlinalarmclock.utils.*
import java.util.*

class RingViewModel(private val app: Application) : AndroidViewModel(app){


    private val _finishActivity = MutableLiveData<Event<Unit>>()
    val finishActivity:LiveData<Event<Unit>>
        get() = _finishActivity


    fun dismissAlarm(){
        val intentService = Intent(app,AlarmService::class.java)
        app.stopService(intentService)
        initiateEvent(_finishActivity)
    }

    fun snoozeAlarm(){
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE,10)
        val alarm = Alarm(
            hour = Calendar.HOUR_OF_DAY,
            minute = Calendar.MINUTE,
            title = "Snooze",
            started = true,
            recurring = false,
            monday = false,
            tuesday = false,
            wednesday = false,
            thursday = false,
            friday = false,
            saturday = false,
            sunday = false
        )
        scheduleAlarm(alarm,app)
        val intentService = Intent(app,AlarmService::class.java)
        app.stopService(intentService)
        initiateEvent(_finishActivity)
    }
    private fun initiateEvent(event: MutableLiveData<Event<Unit>>){
        event.value = Event(Unit)
    }
}