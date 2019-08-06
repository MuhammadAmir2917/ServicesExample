package com.service.example

import android.app.Application
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.service.example.services.CounterService
import com.service.example.services.ForegroundService
/*
*
* Created By Muhammad Amir
* */
class App : Application(){

    private lateinit var counterSericeIntent : Intent
    private lateinit var foregroundServiceIntent : Intent

    fun getCounterServiceIntent() = counterSericeIntent
    fun getForegroundServiceIntent() = foregroundServiceIntent

    override fun onCreate() {
        super.onCreate()
        instance = this
        counterSericeIntent= Intent(instance , CounterService::class.java)
        foregroundServiceIntent = Intent(instance , ForegroundService::class.java)
    }

    companion object{
         lateinit var instance : App

        /*operator fun invoke() = instance ?: synchronized(this){
            instance ?: App().also { instance = it }
        }*/
    }

    fun startForgroundService(intent : Intent){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(intent)
        }else{
            instance.startService(intent)
        }
    }
}