package com.service.example.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.service.example.services.ForegroundService

/*
*
* Created By Muhammad Amir
* */

class BootCompleteReceiver  : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if(it.action == Intent.ACTION_BOOT_COMPLETED){
                /*val counterService = App.instance.getCounterServiceIntent()
                counterService.putExtra(CounterService.EXTRA_MAX_COUNT , 1000)
                App.instance.startService(counterService)
*/
                val foregroundIntent = Intent(context , ForegroundService::class.java)
                foregroundIntent.putExtra(ForegroundService.EXTRA_INPUT , "Foreground Service example in kotlin")
                foregroundIntent.action = ForegroundService.ACTION_START_FOREGROUND_SERVICE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context?.startForegroundService(foregroundIntent)
                } else {
                    context?.startService(foregroundIntent)
                }
            }
        }
    }
}