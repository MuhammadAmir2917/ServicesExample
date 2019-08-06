package com.service.example.workmanagers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.service.example.MainActivity
import com.service.example.R
import java.lang.Exception

class NotificationWorker( context : Context ,  workderParams : WorkerParameters)
    : Worker(context , workderParams){
    companion object{
        const val WORK_RESULT = "work_result"
    }

    override fun doWork(): Result {
        val taskDataString = inputData.getString(MainActivity.MESSAGE_STATUS)
        showNotification("WorkManager" , taskDataString)
        val outputData = Data.Builder().putString(WORK_RESULT , "Job finished").build()
        try{
            Thread.sleep(2000)
        }catch (e : Exception){
            e.printStackTrace()
        }
        return Result.success(outputData)
    }

    private fun showNotification(task: String, desc: String?) {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "task_channel"
        val channelName = "task_name"

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId , channelName , NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext , channelId)
            .setContentTitle(task)
            .setContentText(desc)
            .setSmallIcon(R.mipmap.ic_launcher)

        manager.notify(2 , builder.build())
    }

}