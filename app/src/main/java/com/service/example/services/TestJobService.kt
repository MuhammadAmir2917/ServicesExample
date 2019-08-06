package com.service.example.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.service.example.R
import com.service.example.log
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class TestJobService : JobService(){

    companion object{
        private val TAG = TestJobService::class.java.canonicalName
    }

    private  var params : JobParameters? = null
    private lateinit var manager : NotificationManager


    override fun onStopJob(params: JobParameters?): Boolean {
        log(TAG , "System calling to stop the job here")
       manager.cancel(3)
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
         this.params = params
        log(TAG , "Work to be called from here")
        val task = "Test Job Service"
        val desc = "Test Job service is running"
        showNotification(task , desc)
        doAsync {
            try{
                Thread.sleep(3000)
            }catch (e : Exception){
                e.printStackTrace()
            }

            uiThread {
                log(TAG , "Test Job Service work finished")
                stopForeground(true)
                stopSelf()
                jobFinished(params , false)
            }
        }
        return false

    }

    private fun showNotification(task: String, desc: String?) {
        manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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

        manager.notify(3 , builder.build())
    }

}