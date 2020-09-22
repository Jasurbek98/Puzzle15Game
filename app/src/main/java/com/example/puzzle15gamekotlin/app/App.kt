package com.example.puzzle15gamekotlin.app

import android.app.Application
import com.example.puzzle15gamekotlin.SharedPreference.AppSharedPreference

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppSharedPreference.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}