package com.service.example

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.service.example.eventbus.Events
import com.service.example.eventbus.GlobalBus
import com.service.example.services.CounterService
import com.service.example.services.ForegroundService

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.greenrobot.eventbus.Subscribe

/*
*
* Created By Muhammad Amir
* */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        btnIntentService.setOnClickListener {
            val intent = App.instance.getCounterServiceIntent()
            intent.putExtra(CounterService.EXTRA_MAX_COUNT , 100)
            App.instance.startService(intent)
        }


        btnStartForegroundService.setOnClickListener {
            val intent = App.instance.getForegroundServiceIntent()
            intent.putExtra(ForegroundService.EXTRA_INPUT , "Foreground Service example in kotlin")
            intent.action = ForegroundService.ACTION_START_FOREGROUND_SERVICE
             App.instance.startService(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
          menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
          return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    @Subscribe
    fun events(event : Events.ServiceToActivity){
        tv_message.text = event.message
    }

    override fun onStart() {
        super.onStart()
        GlobalBus.invoke().register(this)
    }

    override fun onDestroy() {
        GlobalBus.invoke().unregister(this)
        super.onDestroy()
    }
}
