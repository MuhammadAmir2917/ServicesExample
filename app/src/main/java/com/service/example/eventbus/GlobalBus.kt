package com.service.example.eventbus

import org.greenrobot.eventbus.EventBus

class GlobalBus {
    companion object{
        @Volatile private var instance : EventBus?= null

        operator fun invoke() = instance ?: synchronized(this){
            instance ?: EventBus.getDefault().also { instance = it  }
        }
    }
}