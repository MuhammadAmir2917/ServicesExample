package com.service.example.services

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import com.service.example.log
import com.service.example.toast
import java.lang.Exception
import kotlin.math.max
/*
*
* Created By Muhammad Amir
* */
class CounterService : IntentService(CounterService::class.java.canonicalName){
    private var handler = Handler()
    companion object{
        private  val TAG = CounterService::class.java.canonicalName
        const val EXTRA_MAX_COUNT = "maxCountValue"
    }

    override fun onHandleIntent(intent: Intent?) {
        val maxCount = intent?.getIntExtra(EXTRA_MAX_COUNT , -1)

        for(i in 0 until maxCount!!){
            log(TAG , "onHandleWork : The number is : $i")
            try{
                Thread.sleep(1000)
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        handler.post {
            toast("Job Execution Started")
        }
    }

}