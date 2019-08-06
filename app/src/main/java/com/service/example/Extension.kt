package com.service.example

import android.content.Context
import android.util.Log
import android.widget.Toast
/*
*
* Created By Muhammad Amir
* */
fun Context.toast(message : String) {
    Toast.makeText(this , message , Toast.LENGTH_SHORT).show()
}

fun Context.log(TAG : String , message : String){
    Log.d(TAG , message)
}