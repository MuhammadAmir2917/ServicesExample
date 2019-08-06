package com.service.example.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.service.example.MainActivity
import com.service.example.R
import com.service.example.toast
import android.media.session.PlaybackState.ACTION_PLAY
import com.service.example.eventbus.Events
import com.service.example.eventbus.GlobalBus


/*
*
* Created By Muhammad Amir
* */



class ForegroundService : Service(){

    companion object{
        const val CHANNEL_ID = "ForgroundServiceChannel"
        const val EXTRA_INPUT = "inputExtra"
        val ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE"

        val ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE"
        val ACTION_PLAY = "ACTION_PLAY"

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when(it.action){
                ACTION_START_FOREGROUND_SERVICE->{
                    startServiceAsForground(it)
                }

                ACTION_STOP_FOREGROUND_SERVICE->{
                    toast("Stop Foreground Service")
                    stopForegroundService()
                }

                ACTION_PLAY->{
                    val event = Events.ServiceToActivity("Message from service to activity")
                    GlobalBus.invoke().post(event)
                    toast("Play Clicked")
                }
            }

        }
        return START_NOT_STICKY
    }

    private fun startServiceAsForground(it: Intent) {
        val input = it.getStringExtra(EXTRA_INPUT)
        createNotificationChannel()
        val notificationIntent = Intent(this , MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this , 0 , notificationIntent , 0)
        val notification  = NotificationCompat.Builder(this , CHANNEL_ID)
            .setContentTitle("ForegroundService")
            .setContentText(input)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
        val playIntent = Intent(this, ForegroundService::class.java)
        playIntent.action = ACTION_PLAY
        val pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0)
        val playAction =
            NotificationCompat.Action(android.R.drawable.ic_media_play, "Play", pendingPlayIntent)
        notification.addAction(playAction)

        startForeground(1 , notification.build())
        toast("Start Foreground Service")
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(CHANNEL_ID,
                "Forground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }


    fun stopForegroundService(){
        stopForeground(true)
        stopSelf()
    }

}